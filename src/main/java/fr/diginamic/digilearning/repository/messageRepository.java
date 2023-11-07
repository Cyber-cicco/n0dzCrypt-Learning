package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Message;

public interface messageRepository extends JpaRepository<Message, Long>  {

}
