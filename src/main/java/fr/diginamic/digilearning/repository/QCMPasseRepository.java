package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.QCMPasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QCMPasseRepository extends JpaRepository<QCMPasse,Long>{

    @Query(nativeQuery = true, value = """
select qp.*
from dl_qcm_passe qp
where qp.utilisateur_id = ?1
and qp.qcm_id = ?2
and qp.archived = 0;
""")
    Optional<QCMPasse> findByUtilisateurAndQCM(Long idUtilisateur, Long idQcm);
}
