package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.UtilisateurService;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("profil")
public class ProfilController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;

    @GetMapping("/api")
    public String getProfilApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(token, model, response);
        return "pages/profil";
    }
    @GetMapping
    public String getProfil(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(token, model, response);
        model.addAttribute("insert", "pages/profil");
        return "base";
    }

    private void irrigateBaseAttributes(String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("links", navBarService.getLinks(userInfos));
        model.addAttribute("presentation", utilisateur.getNom().toUpperCase() + " " + utilisateur.getPrenom());
        model.addAttribute("email", utilisateur.getEmail());
        model.addAttribute("telephone", utilisateur.getTelephone());
        model.addAttribute("dateNaissance", utilisateur.getDateNaissance());
        model.addAttribute("progresCours", utilisateurService.getProgression(utilisateur));
        model.addAttribute("nomSession", utilisateur.getSessionsStagiaire().stream().findFirst().orElseGet(() -> {
            try {
                response.sendRedirect("/error/utilisateur");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new Session();
        }).getNom());
        String placement = "Vous n'êtes pas actullement en recherche d'entreprise";
        if(utilisateur.getStatusAse() != null){
            switch (utilisateur.getStatusAse()){
                case A_PLACE -> placement = "Vous êtes encore en recherche d'entreprise. Courage !";
                case PLACEMENT_EN_COURS -> placement = "Vous avez trouvé une entreprise, plus que quelques détails à régler.";
            }
        }
        model.addAttribute("placement", placement);
        model.addAttribute("title", "Mon Compte");
    }
}
