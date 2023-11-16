package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.entities.Conversation;
import fr.diginamic.digilearning.entities.Message;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.ConversationRepository;
import fr.diginamic.digilearning.repository.MessageRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private int PAGE_SIZE = 20;


    public record MessageModel(String usermsg){}

    public record ContactInfos (
            Utilisateur utilisateur,
            String status
    ){}

    public record PartialConv(
            Long id,
            List<Message> messages,
            String nomConversation
    ){}
    public PartialConv getConversation(Utilisateur utilisateur, Utilisateur interlocuteur, int page) {
        Optional<Conversation> conversationOpt = conversationRepository.getConversationForUserWithInterlocutor(utilisateur.getId(), interlocuteur.getId());
        Conversation conversation = conversationOpt.orElseGet(() ->
            conversationRepository.save(Conversation.builder()
                    .participants(List.of(utilisateur, interlocuteur))
                    .build())
        );
        return getMessagesFromConversation(conversation, page);
    }

    public PartialConv getMessagesFromConversation(Conversation conversation, int page) {
        List<Message> messages = messageRepository.findByConversationOrderByDateEmissionDesc(PageRequest.of(page, PAGE_SIZE), conversation).toList();
        return new PartialConv(conversation.getId(), messages, conversation.getLibelleGroupe());
    }

    public List<ContactInfos> createContactList(Utilisateur utilisateur) {
        return utilisateur.getSessionStagiaire()
                .map(session -> session.getAdministrationSessions().stream()
                .map(administrationSession ->{
                            String role = "";
                            switch (administrationSession.getStatusResponsableSession()){
                                case ROOM_MASTER -> role = "Room Master";
                                case RESPONSABLE_PEDAGOGIQUE -> role = "Responsable pédagogique";
                                case RESPONSABLE_ADMINISTRATIF -> role = "Responsable administratif";
                            }

                            return new ContactInfos(
                                    administrationSession.getUtilisateur(),
                                    role
                            );
                        }
                )
                .toList()).orElseGet(ArrayList::new);
    }

    public Conversation postNewMessage(Utilisateur emetteur, MessageModel messageModel, Long id) {
        Conversation conversation = conversationRepository.getConversationByIdAndUtilisateurConcerne(emetteur.getId(), id)
                .orElseThrow(EntityNotFoundException::new);
        Message message = Message.builder()
                .contenu(messageModel.usermsg())
                .conversation(conversation)
                .emetteur(emetteur)
                .emetteurId(emetteur.getId())
                .dateEmission(LocalDateTime.now())
                .build();
        messageRepository.findFirstByConversationOrderByDateEmissionDesc(conversation).ifPresentOrElse((oldMessage) -> {
                    oldMessage.setNext(message);
                    message.setPrevious(oldMessage);
                    messageRepository.saveAll(List.of(oldMessage, message));
                },
                ()->{
                    messageRepository.save(message);
                }
        );
        return conversation;
    }
}
