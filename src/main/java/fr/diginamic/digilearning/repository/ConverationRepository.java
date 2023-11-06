package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.Conversation;

public interface ConverationRepository extends JpaRepository<Conversation, Long>  {

}
