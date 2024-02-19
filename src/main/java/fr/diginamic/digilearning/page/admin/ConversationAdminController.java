package fr.diginamic.digilearning.page.admin;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.admin.irrigator.ConversationAdminIrrigator;
import fr.diginamic.digilearning.page.irrigator.ConversationIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/conversation")
public class ConversationAdminController {

    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final ConversationAdminIrrigator conversationAdminIrrigator;
    private final ConversationIrrigator conversationIrrigator;

    @GetMapping
    public String getConversation(Model model, HttpServletResponse response, @RequestParam(required = false, name = "id") Long idInterlocuteur) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), response);
        if(idInterlocuteur == null){
            conversationAdminIrrigator.irrigateConversationAdmin(model, userInfos, Routes.ADR_ADMIN_CONVERSATIONS_PRESENTATION);
            layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_CONVERSATIONS);
            return Routes.ADR_BASE_LAYOUT;
        }
        conversationIrrigator.irrgateConversationInterlocuteur(model, userInfos, idInterlocuteur);
        conversationAdminIrrigator.irrigateConversationAdmin(model, userInfos, Routes.ADR_OUTER_CHAT);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_CONVERSATIONS);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/api")
    public String getConversationApi(Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), response);
        conversationAdminIrrigator.irrigateConversationAdmin(model, userInfos, Routes.ADR_ADMIN_CONVERSATIONS_PRESENTATION);
        return Routes.ADR_ADMIN_CONVERSATIONS;
    }
}
