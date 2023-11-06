package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import fr.diginamic.digilearning.entities.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>  {

    Optional<Utilisateur> findByEmail(String email);
}
