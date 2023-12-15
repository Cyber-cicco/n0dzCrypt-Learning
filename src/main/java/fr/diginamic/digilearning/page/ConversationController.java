package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.page.irrigator.ConversationIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.page.service.ConversationService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conversation")
public class ConversationController {
    private final AuthenticationService authenticationService;
    private final ConversationIrrigator conversationIrrigator;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping("/stagiaire/api")
    public String getConversationApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        conversationIrrigator.irrigateBaseAttributesStagiaires(userInfos, model, response);
        return Routes.ADR_CONVERSATION_BODY;
    }
    @GetMapping("/stagiaire")
    public String getConversation(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        conversationIrrigator.irrigateBaseAttributesStagiaires(userInfos, model, response);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_CONVERSATION_BODY);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/interlocuteur")
    public String getConversationWithInterlocuteur(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idInterlocuteur, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        conversationIrrigator.irrgateConversationInterlocuteur(model, userInfos, idInterlocuteur);
        return Routes.ADR_OUTER_CHAT;
    }

    @PostMapping("/message")
    public String postNewMessage(@CookieValue("AUTH-TOKEN") String token, @ModelAttribute ConversationService.MessageModel message, @RequestParam("id") Long id, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        conversationIrrigator.irrigateConvWithNewMessage(model, userInfos, message, id);
        return Routes.ADR_INNER_CHAT ;
    }

    @GetMapping("messages/refresh")
    public String getOldMessages(@CookieValue("AUTH-TOKEN") String token,  @RequestParam("page") int page, @RequestParam("conv") Long idConversation, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        conversationIrrigator.irrigateRefresh(model, userInfos, page, idConversation);
        return Routes.ADR_INNER_CHAT;
    }
}
