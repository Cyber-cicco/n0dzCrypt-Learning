package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/error")
public class ErrorController {

    private final AuthenticationService authenticationService;
    @GetMapping("/utilisateur")
    public String getCours(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        return Routes.ADR_UTILISATEUR_ERROR;
    }
}
