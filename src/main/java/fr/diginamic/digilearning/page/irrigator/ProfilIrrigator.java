package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.service.CoursService;
import fr.diginamic.digilearning.service.UtilisateurService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.DateUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Irrigateur du model donné par le controlleur
 * HyperMédia du profil
 */
@Service
@RequiredArgsConstructor
public class ProfilIrrigator {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurService utilisateurService;
    private final CoursService coursService;
    private final CoursRepository coursRepository;
    private final DateUtil dateUtil;
    public void irrigateBaseAttributes(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        List<Cours> bookmarked = coursRepository.getBookMarked(userInfos.getId());
        irrigateLeftCard(model, utilisateur);
        model.addAttribute("bookmarked", bookmarked);
        model.addAttribute("_user" ,userInfos);
        model.addAttribute("dateNaissance", utilisateur.getDateNaissance());
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        model.addAttribute("progresCours", utilisateurService.getProgression(utilisateur.getId()));
        model.addAttribute("progresJour", utilisateurService.getProgressionJournee(utilisateur.getId()));
        model.addAttribute("dateUtil", dateUtil);
        model.addAttribute("title", "Mon Compte");
    }

    public void irrigateLeftCard(Model model, Utilisateur utilisateur){
        model.addAttribute("_session", utilisateur.getSessionCourante());
        model.addAttribute("presentation", utilisateur.getNom().toUpperCase() + " " + utilisateur.getPrenom());
        model.addAttribute("email", utilisateur.getEmail());
        model.addAttribute("telephone", utilisateur.getTelephone());
    }

    public void irrigateApprenantAdmin(Model model, Long idUtilisateur, AuthenticationInfos userInfos){
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("_user" ,userInfos);
        model.addAttribute("id", utilisateur.getId());
        model.addAttribute("banned", utilisateur.getBanned());
        model.addAttribute("progresCours", utilisateurService.getProgression(utilisateur.getId()));
        irrigateLeftCard(model, utilisateur);
    }

    public void irrigatePatchProgress(Model model, AuthenticationInfos userInfos, Long idCours){
        boolean finished = coursService.patchFinished(userInfos, idCours);
        model.addAttribute("style", (finished)
                ? "filter: invert(42%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);"
                : ""
        );
        model.addAttribute("progresCours", utilisateurService.getProgression(userInfos.getId()));
        model.addAttribute("progresJour", utilisateurService.getProgressionJournee(userInfos.getId()));
        model.addAttribute("id", idCours);
    }
}
