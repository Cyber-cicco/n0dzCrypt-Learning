package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ConversationRepository extends JpaRepository<Conversation, Long>  {

    @Query(nativeQuery = true,
    value =
            "select c.* from " +
            "(select c.* from dl_conversation c " +
            "join dl_utilisateur_conversation duc on c.id = duc.conversation_id" +
            " where duc.utilisateur_id = ?1) as c" +
            " join dl_utilisateur_conversation duc2 on c.id = duc2.conversation_id" +
            " where duc2.utilisateur_id = ?2 limit 1"
    )
    Optional<Conversation> getConversationForUserWithInterlocutor(Long idUtilisateur, Long idInterlocuteur);

    @Query(nativeQuery = true,
            value =
                    "select c.* from " +
                            "(select c.* from dl_conversation c " +
                            "join dl_utilisateur_conversation duc on c.id = duc.conversation_id" +
                            " where duc.utilisateur_id = ?1) as c" +
                            " where c.id = ?2 limit 1"
    )
    Optional<Conversation> getConversationByIdAndUtilisateurConcerne(Long idUtilisateur, Long idConversation);
}
