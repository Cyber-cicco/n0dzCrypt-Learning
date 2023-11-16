package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Conversation;
import fr.diginamic.digilearning.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>  {

    Page<Message> findByConversationOrderByDateEmissionDesc(Pageable pageable, Conversation conversation);

    @Query("select m from Message m where m.conversation = :conversation order by m.dateEmission desc, m.id desc limit 1")
    Optional<Message> findFirstByConversationOrderByDateEmissionDesc(Conversation conversation);

}
