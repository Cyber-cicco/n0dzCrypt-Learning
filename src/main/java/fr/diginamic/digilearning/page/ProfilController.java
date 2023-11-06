package fr.diginamic.digilearning.page;

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
@RequestMapping("profil")
public class ProfilController {

    private final AuthenticationService authenticationService;

    @GetMapping("/api")
    public String getProfilApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(model);
        return "pages/profil";
    }
    @GetMapping
    public String getProfil(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(model);
        model.addAttribute("insert", "pages/profil");
        return "base";
    }

    private void irrigateBaseAttributes(Model model){
        model.addAttribute("title", "Mon Compte");
    }
}
