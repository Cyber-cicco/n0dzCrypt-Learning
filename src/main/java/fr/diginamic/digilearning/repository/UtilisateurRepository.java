package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>  {

    Optional<Utilisateur> findByEmail(String email);

    @Query(nativeQuery = true, value = """
select * from UTILISATEUR
join SESSION_STAGIAIRE SS on UTILISATEUR.ID = SS.ID_STAG
where SS.ID_SES = ?1
""")
    List<Utilisateur> findBySession(Long id);

}
