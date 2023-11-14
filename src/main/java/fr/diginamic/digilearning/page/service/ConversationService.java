package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.entities.Message;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.StatusResponsableSession;
import fr.diginamic.digilearning.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    public List<Message> getConversation() {
        return null;
    }

    public record ContactInfos (
            Long id,
            String nom,
            String prenom,
            String email,
            String status
    ){}
    public List<ContactInfos> createContactList(Utilisateur utilisateur) {
        System.out.println(utilisateur.getSessionStagiaire().get().getNom());
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
                                    administrationSession.getUtilisateur().getId(),
                                    administrationSession.getUtilisateur().getNom(),
                                    administrationSession.getUtilisateur().getPrenom(),
                                    administrationSession.getUtilisateur().getEmail(),
                                    role
                            );
                        }
                )
                .toList()).orElseGet(ArrayList::new);
    }
}
