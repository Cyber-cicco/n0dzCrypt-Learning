package fr.diginamic.digilearning.components;

import fr.diginamic.digilearning.components.elements.NavLink;
import fr.diginamic.digilearning.components.elements.NavLinks;
import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("components/navbar")
public class NavBarController {

    private final NavBarService navBarService;

    @GetMapping
    public String getNavBar(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = new AuthenticationInfos();
        NavLinks links[] =  navBarService.getLinks(userInfos);
        model.addAttribute("links", links);
        model.addAttribute("foo", "foo");
        model.addAttribute("bar", "bar");
        return "components/navbar";
    }
}
