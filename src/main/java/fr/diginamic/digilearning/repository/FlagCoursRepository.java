package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlagCoursRepository extends JpaRepository<FlagCours,Long>{
    Optional<FlagCours> findByCoursAndStagiaire(Cours cours, Utilisateur stagiaire);
}
