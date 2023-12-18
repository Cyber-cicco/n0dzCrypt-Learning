package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class HomePageIrrigator {
    private final UtilisateurRepository utilisateurRepository;
    private final CoursRepository coursRepository;
    private final CoursService coursService;
    private final DateUtil dateUtil;

    public void irrigateModel(Model model, AuthenticationInfos userInfos) {
        model.addAttribute("utilisateur", utilisateurRepository.findById(userInfos.getId())
                .orElseThrow(EntityNotFoundException::new));
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
        model.addAttribute("dateUtil", dateUtil);
    }
}
