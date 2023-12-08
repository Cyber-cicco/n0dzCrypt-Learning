package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.utils.DateUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomePageController {
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationService authenticationService;
    private final CoursRepository coursRepository;
    private final CoursService coursService;
    private final DateUtil dateUtil;
    private final NavBarService navBarService;

    @GetMapping({"api", "home/api"})
    public String getHomePageApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateModel(model, userInfos);
        return "pages/home";
    }
    @GetMapping({"/", "", "home"})
    public String getHomePage(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("insert", "pages/home");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        irrigateModel(model, userInfos);
        return "base";
    }

    private void irrigateModel(Model model, AuthenticationInfos userInfos) {
        model.addAttribute("utilisateur", utilisateurRepository.findById(userInfos.getId())
                .orElseThrow(EntityNotFoundException::new));
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
        model.addAttribute("dateUtil", dateUtil);
    }
}
