package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.page.service.ConversationService;
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
@RequestMapping("conversation")
public class ConversationController {
    private final ConversationService conversationService;
    private final AuthenticationService authenticationService;

    @GetMapping("/api")
    public String getConversationApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(model);
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        return "pages/conversation";
    }
    @GetMapping
    public String getConversation(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        irrigateBaseAttributes(model);
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("insert", "pages/conversation");
        return "base";
    }

    private void irrigateBaseAttributes(Model model){
        model.addAttribute("title", "Conversations");
    }
}
