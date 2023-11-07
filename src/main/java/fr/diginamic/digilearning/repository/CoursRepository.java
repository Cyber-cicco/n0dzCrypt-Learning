package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Cours;

public interface CoursRepository extends JpaRepository<Cours, Long>  {

}
