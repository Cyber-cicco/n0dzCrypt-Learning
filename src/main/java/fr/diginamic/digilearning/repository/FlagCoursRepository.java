package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlagCoursRepository extends JpaRepository<FlagCours,Long>{
    Optional<FlagCours> findByCoursAndStagiaire(Cours cours, Utilisateur stagiaire);
    Optional<FlagCours> findByCoursAndStagiaire_Id(Cours cours, Long id);
    List<FlagCours> findByDatePrevueBetweenAndStagiaire_Id(LocalDateTime ldt1, LocalDateTime ldt2, Long id);
}
