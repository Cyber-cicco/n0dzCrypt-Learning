package fr.diginamic.digilearning.page;


import fr.diginamic.digilearning.repository.SujetRepository;
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
@RequiredArgsConstructor
@RequestMapping("forum")
public class ForumController {

    private final AuthenticationService authenticationService;
    private final SujetRepository sujetRepository;
    @GetMapping("/api")
    public String getForumApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("sujets", sujetRepository.findAll());
        return "pages/forum";
    }
    @GetMapping
    public String getForum(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("insert", "pages/forum");
        irrigateBaseTemplate(token, model, response);
        return "base";
    }

    private void irrigateBaseTemplate(String token, Model model, HttpServletResponse response){
        model.addAttribute("sujets", sujetRepository.findAll());
    }
}
