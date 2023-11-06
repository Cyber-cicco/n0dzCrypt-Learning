package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>  {

}
