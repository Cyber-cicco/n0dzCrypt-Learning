package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.old.Role;

public interface RoleRepository extends JpaRepository<Role, Long>  {

}
