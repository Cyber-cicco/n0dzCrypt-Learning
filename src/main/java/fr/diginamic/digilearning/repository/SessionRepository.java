package fr.diginamic.digilearning.repository;


import fr.diginamic.digilearning.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

}
