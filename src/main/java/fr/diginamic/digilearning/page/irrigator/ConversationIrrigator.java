package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.Conversation;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.ConversationService;
import fr.diginamic.digilearning.page.validators.MessageValidator;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Irrigateur du modèle donnée par le controlleur
 * HyperMédia de la conversation
 */
@Service
@RequiredArgsConstructor
public class ConversationIrrigator {

    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationService authenticationService;
    private final ConversationService conversationService;
    private final MessageValidator messageValidator;

    /**
     * Irrigue les informations essentielles pour le modèle de la conversation
     * pour un stagiaire
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param response la réponse envoyée à l'utilisateur
     */
    public void irrigateBaseAttributesStagiaires(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_STAGIAIRE, response);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        irrigateBaseAttributesConversationStagiaire(utilisateur, model);
    }

    /**
     * Permet de récupérer les informations nécessaires pour afficher la conversation
     * @param utilisateur l'utilisateur connecté
     * @param model un objet permettant d'irriguer le template thymeleaf
     */
    public void irrigateBaseAttributesConversationStagiaire(Utilisateur utilisateur, Model model){
        model.addAttribute("idUtilisateur", utilisateur.getId());
        List<ConversationService.ContactInfos> contactInfos = conversationService.createContactList(utilisateur);
        if(contactInfos.get(0) != null) {
            model.addAttribute("page", 0);
            model.addAttribute("interlocuteur", contactInfos.get(0));
            model.addAttribute("conversation", conversationService.getConversation(utilisateur, contactInfos.get(0).utilisateur(), 0));
        }
        model.addAttribute("contacts", contactInfos);
    }

    /**
     * Permet d'irriguer le modèle du chat pour un interlocuteur donné
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idInterlocuteur l'identifiant de l'interlocuteur.
     */
    public void  irrgateConversationInterlocuteur(Model model, AuthenticationInfos userInfos, Long idInterlocuteur){
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        Utilisateur interlocuteur = utilisateurRepository.findById(idInterlocuteur).orElseThrow(EntityNotFoundException::new);
        ConversationService.ContactInfos interlocuteurInfos = new ConversationService.ContactInfos(interlocuteur, null);
        ConversationService.PartialConv conversation = conversationService.getConversation(utilisateur, interlocuteur, 0);
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("conversation", conversation);
        model.addAttribute("error", "");
        model.addAttribute("interlocuteur", interlocuteurInfos);
        model.addAttribute("page", 0);
    }

    /**
     * Irrigue la conversation avec un interlocteur donné
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param message le nouveau message
     * @param id l'identifiant de la conversation
     */
    public void irrigateConvWithNewMessage(Model model, AuthenticationInfos userInfos, MessageDto message, Long id){
        Utilisateur emetteur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        Conversation conversation;
        messageValidator.validateMessage(message.getMessage());
        conversation = conversationService.postNewMessage(emetteur, message, id);
        model.addAttribute("error", "");
        model.addAttribute("idUtilisateur", emetteur.getId());
        model.addAttribute("conversation", conversationService.getMessagesFromConversation(conversation, 0));
        model.addAttribute("page", 0);
    }

    /**
     * Permet d'irriguer le modèle de la conversation lors du rafraichissement de la
     * conversation
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param page le numéro de la page
     * @param idConversation l'identifiant de la conversation
     */
    public void irrigateRefresh(Model model, AuthenticationInfos userInfos, int page, Long idConversation) {
        ConversationService.PartialConv conversation = conversationService.getConversation(userInfos, idConversation, page);
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("error", "");
        model.addAttribute("conversation", conversation);
        model.addAttribute("page", page);
    }
}
