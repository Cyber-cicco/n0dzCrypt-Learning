package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.CoursSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CoursSessionRepository extends JpaRepository<CoursSession,Long>{

    Optional<CoursSession> findByCours_IdAndSession_Id(Long idCours, Long idSession);

    List<CoursSession> findByDatePrevueBetweenAndSession_Id(LocalDateTime dateDebut, LocalDateTime dateFin, Long idSession);
}
