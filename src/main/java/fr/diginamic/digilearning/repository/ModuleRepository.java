package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Module;

public interface ModuleRepository extends JpaRepository<Module, Long>  {

}
