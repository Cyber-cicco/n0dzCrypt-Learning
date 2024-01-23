package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.page.irrigator.HomePageIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
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
@RequestMapping
@RequiredArgsConstructor
public class HomePageController {
    private final AuthenticationService authenticationService;
    private final HomePageIrrigator homePageIrrigator;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping({"api", "home/api"})
    public String getHomePageApi( Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        homePageIrrigator.irrigateModel(model, userInfos);
        return Routes.ADR_HOME;
    }
    @GetMapping({"/", "", "home"})
    public String getHomePage(Model model, HttpServletResponse response) throws IOException {
        try {
            AuthenticationInfos userInfos = authenticationService.getAuthInfos();
            layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_HOME);
            homePageIrrigator.irrigateModel(model, userInfos);
            return Routes.ADR_BASE_LAYOUT;
        } catch (Exception e){
            response.sendRedirect("login");
            return Routes.ADR_LOGIN;
        }
    }
}
