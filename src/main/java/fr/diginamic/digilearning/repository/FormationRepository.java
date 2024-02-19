package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Long>  {

    @Query(nativeQuery = true, value = """
select ID, NOM, NOM_COURT, REFERENCE from FORMATION 
""" )
    List<Formation> findAllActive();

    @Query(nativeQuery = true, value = """
select ID, NOM, NOM_COURT, REFERENCE
from FORMATION 
where NOM = ?
limit 1
""")
    Optional<Formation> findByNom(String nom);
}
