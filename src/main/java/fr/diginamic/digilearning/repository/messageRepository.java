package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import fr.diginamic.digilearning.entities.Message;

public interface messageRepository extends JpaRepository<Message, Long>  {

}
