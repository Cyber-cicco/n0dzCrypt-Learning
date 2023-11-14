package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.RoleEnum;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.ConversationService;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("conversation")
public class ConversationController {
    private final ConversationService conversationService;
    private final AuthenticationService authenticationService;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/stagiaire/api")
    public String getConversationApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseAttributesStagiaires(userInfos, model, response);
        return "pages/conversation";
    }
    @GetMapping("/stagiaire")
    public String getConversation(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseAttributesStagiaires(userInfos, model, response);
        model.addAttribute("insert", "pages/conversation");
        return "base";
    }

    private void irrigateBaseAttributesStagiaires(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        authenticationService.mustBeOfRole(userInfos.getRoles(), RoleEnum.ROLE_STAGIAIRE, response);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("title", "Mon suivi");
        List<ConversationService.ContactInfos> contactInfos = conversationService.createContactList(utilisateur);
        if(contactInfos.get(0) != null) {
            model.addAttribute("idConversateur", contactInfos.get(0).id());
            model.addAttribute("conversation", conversationService.getConversation());
        } else {
            model.addAttribute("idConversateur", -1L);
        }
        model.addAttribute("contacts", contactInfos);
    }
}
