package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.entities.Role;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.irrigator.HomePageIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.dto.LoginDto;
import fr.diginamic.digilearning.security.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final HomePageIrrigator homePageIrrigator;
    private final PasswordEncoder passwordEncoder;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping
    public String getloginPage(Model model, HttpServletResponse response){
        return Routes.ADR_LOGIN;
    }
    @GetMapping("/api")
    public void redirectToLogin(Model model, HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @PostMapping
    public String login(@ModelAttribute LoginDto loginDto, Model model, HttpServletResponse response){
        Optional<Utilisateur> auth = utilisateurRepository.findByEmail(loginDto.getEmail())
                .filter(utilisateur -> passwordEncoder.matches(loginDto.getPassword(), utilisateur.getPassword())) ;
        if(auth.isPresent()){
            AuthenticationInfos userInfos = AuthenticationInfos.builder()
                    .id(auth.get().getId())
                    .email(auth.get().getEmail())
                    .roles(auth.get()
                            .getRoles().stream().map(Role::getLibelle).collect(Collectors.toList()))
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, jwtService.buildJWTCookie(auth.get()));
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            null,
                                    auth.get().getRoles()
                                    .stream()
                                    .map(role -> new SimpleGrantedAuthority(role.getType().getLibelle()))
                                    .collect(Collectors.toList())
                    )
            );
            layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_HOME);
            homePageIrrigator.irrigateModel(model, userInfos);
            response.setHeader("HX-Redirect", "home");
            return Routes.ADR_BASE_LAYOUT;
        }
        model.addAttribute("error", "Votre adresse e-mail ou mot de passe est invalide");
        return Routes.ADR_FORM_ERROR;
    }
}
