package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.AdministrationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdministrationSessionRepository extends JpaRepository<AdministrationSession,Long>{
    Optional<AdministrationSession> findByUtilisateur_IdAndSession_Id(Long idUtilisateur, Long idSession);
    List<AdministrationSession> findBySession_Id(Long id);

    @Query(nativeQuery = true, value = """
insert into ADMINISTRATION_SESSION(ID_SESSION, ID_UTILISATEUR, STATUS_RESPONSABLE_SESSION)
 values (?2, ?1, 0);
""")
    void addResponsable(Long idResponsable, Long idSession);
}
