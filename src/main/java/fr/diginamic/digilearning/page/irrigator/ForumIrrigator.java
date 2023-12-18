package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.entities.FilDiscussion;
import fr.diginamic.digilearning.entities.Salon;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.service.ForumService;
import fr.diginamic.digilearning.repository.SujetRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Irrigateur du modèle donnée par le controlleur
 * HyperMédia du forum
 */
@Service
@RequiredArgsConstructor
public class ForumIrrigator {

    private final UtilisateurRepository utilisateurRepository;
    private final SujetRepository sujetRepository;
    private final LayoutIrrigator layoutIrrigator;
    private final ForumService forumService;
    public void irrigateBaseTemplate(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        model.addAttribute("utilisateur", utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("sujets", sujetRepository.findAll());
    }

    public void irrigateCard(Model model, AuthenticationInfos userInfos, String cardInsert) {
        model.addAttribute("cardInsert",cardInsert);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_FORUM);
    }

    public void irrigateSalonAttribute(AuthenticationInfos userInfos, Model model, HttpServletResponse response, Long idSalon) {
        Salon salon = forumService.getSalonByIdAndCheckIfUserAuthorized(userInfos.getId(), idSalon);
        FilDiscussion regles = forumService.getRegles();
        List<FilDiscussion> discussions = forumService.getFilOrderedFilDiscussion(salon.getId());
        model.addAttribute("salon", salon);
        model.addAttribute("regles", regles);
        model.addAttribute("discussions", discussions);
    }

    public void irrigateFilAttribute(AuthenticationInfos userInfos, Model model, HttpServletResponse response, Long idFil, Long page) {
        forumService.verifyIfUserIsAllowed(userInfos, idFil, response);
        FilDiscussion fil = forumService.getFilDiscussion(idFil);
        model.addAttribute("page", page);
        model.addAttribute("nbPages", forumService.getNbPages(fil));
        model.addAttribute("id", fil.getSalon().getId());
        model.addAttribute("fil", fil);
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil, page));
    }

    public void irrigateRegles(Model model, Long id){
        Long idFil = forumService.getRegles().getId();
        model.addAttribute("page", 1);
        model.addAttribute("nbPages", 1);
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil, 1L));
        model.addAttribute("fil", forumService.getFilDiscussion(idFil));
        model.addAttribute("id", id);
    }
}
