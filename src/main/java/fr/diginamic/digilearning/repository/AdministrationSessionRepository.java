package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.AdministrationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdministrationSessionRepository extends JpaRepository<AdministrationSession,Long>{
    Optional<AdministrationSession> findByUtilisateur_IdAndSession_Id(Long idUtilisateur, Long idSession);
    List<AdministrationSession> findBySession_Id(Long id);
}
