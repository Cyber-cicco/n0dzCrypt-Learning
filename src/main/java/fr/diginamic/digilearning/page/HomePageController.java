package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.page.service.HomeService;
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
@RequestMapping
@RequiredArgsConstructor
public class HomePageController {
    private final HomeService homeService;
    private final AuthenticationService authenticationService;

    @GetMapping({"api", "home/api"})
    public String getHomePageApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        homeService.irrigateModel(model);
        return "pages/home";
    }
    @GetMapping({"/", "", "home"})
    public String getHomePage(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("insert", "pages/home");
        homeService.irrigateModel(model);
        return "base";
    }
}
