package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursRepository extends JpaRepository<Cours, Long>  {

}
