package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>  {

}
