package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Long>  {

    @Query(nativeQuery = true, value = """
select ID,  NOM, NOM_COURT, REFERENCE, TITRE from FORMATION where ID_NEXT IS NULL
""" )
    List<Formation> findAllActive();

    @Query(nativeQuery = true, value = """
select ID, NOM, NOM_COURT, REFERENCE, TITRE 
from FORMATION 
where ID_NEXT 
IS NULL and NOM = ?
order by DATE_MAJ DESC 
limit 1
""")
    Optional<Formation> findByNom(String nom);
}
