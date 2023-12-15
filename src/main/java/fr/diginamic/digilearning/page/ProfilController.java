package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.service.UtilisateurService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.utils.DateUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("profil")
public class ProfilController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final CoursRepository coursRepository;
    private final CoursService coursService;
    private final DateUtil dateUtil;

    @GetMapping("/api")
    public String getProfilApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(token, model, response);
        return "pages/profil/profil";
    }
    @GetMapping
    public String getProfil(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(token, model, response);
        model.addAttribute("insert", "pages/profil/profil");
        return "base";
    }

    private void irrigateBaseAttributes(String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        List<Cours> bookmarked = coursRepository.getBookMarked(userInfos.getId());
        model.addAttribute("links", navBarService.getLinks(userInfos));
        model.addAttribute("presentation", utilisateur.getNom().toUpperCase() + " " + utilisateur.getPrenom());
        model.addAttribute("email", utilisateur.getEmail());
        model.addAttribute("telephone", utilisateur.getTelephone());
        model.addAttribute("bookmarked", bookmarked);
        model.addAttribute("dateNaissance", utilisateur.getDateNaissance());
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        model.addAttribute("progresCours", utilisateurService.getProgression(utilisateur.getId()));
        model.addAttribute("progresJour", utilisateurService.getProgressionJournee(utilisateur.getId()));
        model.addAttribute("dateUtil", dateUtil);
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

    @PatchMapping("/finished/progress")
    public String patchFinishedWithProgress(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        boolean finished = coursService.patchFinished(userInfos, idCours);
        model.addAttribute("style", (finished)
                ? "filter: invert(42%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);"
                : ""
        );
        model.addAttribute("progresCours", utilisateurService.getProgression(userInfos.getId()));
        model.addAttribute("progresJour", utilisateurService.getProgressionJournee(userInfos.getId()));
        model.addAttribute("id", idCours);
        return "pages/profil/fragments/profil.progress";
    }
}
