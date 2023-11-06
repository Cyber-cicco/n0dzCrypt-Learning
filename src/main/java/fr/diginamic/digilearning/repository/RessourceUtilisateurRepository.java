package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import fr.diginamic.digilearning.entities.RessourceUtilisateur;

public interface RessourceUtilisateurRepository extends JpaRepository<RessourceUtilisateur, Long>  {

}
