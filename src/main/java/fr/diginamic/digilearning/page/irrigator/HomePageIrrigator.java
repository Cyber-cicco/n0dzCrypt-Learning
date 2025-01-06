package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Irrigateur du modèle donnée par le controlleur
 * HyperMédia de la page d'accueil
 */
@Service
@RequiredArgsConstructor
public class HomePageIrrigator {
    private final UtilisateurRepository utilisateurRepository;
    private final CoursRepository coursRepository;
    private final CoursService coursService;
    private final DateUtil dateUtil;
    public record TestR(String pipi, String caca){}

    public void irrigateModel(Model model, AuthenticationInfos userInfos) {
        model.addAttribute("utilisateur", utilisateurRepository.findById(userInfos.getId())
                .orElseThrow(EntityNotFoundException::new));
        Supplier<String> test = () -> "bite";
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
        model.addAttribute("func", test);
        model.addAttribute("_prout69", new TestR("caca", "pipi"));
        model.addAttribute("dateUtil", dateUtil);
    }
}
