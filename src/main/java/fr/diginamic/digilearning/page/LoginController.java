package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.HomeService;
import fr.diginamic.digilearning.page.service.LoginService;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.dto.LoginDto;
import fr.diginamic.digilearning.security.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {
    private final LoginService loginService;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String getloginPage(Model model, HttpServletResponse response){
        model.addAttribute("title", "Me connecter");
        return "pages/login";
    }

    @PostMapping
    public String login(@ModelAttribute LoginDto loginDto, Model model, HttpServletResponse response){
        utilisateurRepository.findByEmail(loginDto.getEmail())
                .filter(utilisateur -> passwordEncoder.matches(loginDto.getPassword(), utilisateur.getMotDePasse()))
                .ifPresentOrElse((utilisateur) ->{
                    response.setHeader(HttpHeaders.SET_COOKIE, jwtService.buildJWTCookie(utilisateur));
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    loginDto.getEmail(),
                                    null,
                                    utilisateurRepository.findByEmail(loginDto.getEmail())
                                    .orElseThrow(EntityNotFoundException::new)
                                    .getRoleList().stream()
                                    .map(role -> new SimpleGrantedAuthority(role.getTypeRole().getLibelle()))
                                    .collect(Collectors.toList())
                            )
                    );
                    response.setHeader("HX-Redirect", "home");
                }, () -> {
                    model.addAttribute("error", "Votre adresse e-mail ou mot de passe est invalide");
                });
        return "reponses/form.error";
    }
}
