package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Formation;

public interface FormationRepository extends JpaRepository<Formation, Long>  {

}
