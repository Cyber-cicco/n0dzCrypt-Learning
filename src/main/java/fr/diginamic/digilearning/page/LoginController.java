package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.entities.Role;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.page.irrigator.HomePageIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.dto.LoginDto;
import fr.diginamic.digilearning.security.service.JwtService;
import fr.diginamic.digilearning.utils.hx.HX;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final HomePageIrrigator homePageIrrigator;
    private final PasswordEncoder passwordEncoder;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping("/login")
    public String getloginPage(){
        return Routes.ADR_LOGIN;
    }
    @GetMapping("/login/api")
    public void redirectToLogin(HttpServletResponse response) throws IOException {
        response.setHeader(HX.REDIRECT, "/login");
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, Model model, HttpServletResponse response){
        Optional<Utilisateur> auth = utilisateurRepository.findByEmail(loginDto.getEmail())
                .filter(utilisateur -> passwordEncoder.matches(loginDto.getPassword(), utilisateur.getPassword())) ;
        if(auth.isPresent()){
            AuthenticationInfos userInfos = AuthenticationInfos.builder()
                    .id(auth.get().getId())
                    .email(auth.get().getEmail())
                    .roles(auth.get()
                            .getRoles().stream().map(Role::getLibelle).collect(Collectors.toList()))
                    .banned(auth.get().getBanned())
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, jwtService.buildJWTCookie(auth.get()));
            layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_HOME);
            homePageIrrigator.irrigateModel(model, userInfos);
            response.setHeader(HX.REDIRECT, "home");
            return Routes.ADR_BASE_LAYOUT;
        }
        model.addAttribute("error", "Votre adresse e-mail ou mot de passe est invalide");
        return Routes.ADR_FORM_ERROR;
    }
}
