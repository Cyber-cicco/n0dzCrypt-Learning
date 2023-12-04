package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final CoursRepository coursRepository;

    @GetMapping("/api")
    public String getAgendaApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model);
        return "pages/agenda";
    }
    @GetMapping
    public String getAgenda(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model);
        model.addAttribute("insert", "pages/agenda");
        return "base";
    }

    private void irrigateBaseModel(AuthenticationInfos userInfos, Model model) {
        model.addAttribute("navbar", navBarService.getLinks(userInfos));
        model.addAttribute("cours", coursRepository.getAllCoursForUser(userInfos.getId()));
    }
}
