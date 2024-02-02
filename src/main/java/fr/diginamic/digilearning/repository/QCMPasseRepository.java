package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.QCMPasse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QCMPasseRepository extends JpaRepository<QCMPasse,Long>{

    @Query(nativeQuery = true, value = """
select qp.*
from dl_qcm_passe qp
join dl_chapitre dc on dc.id = qp.qcm_id
where qp.utilisateur_id = ?1
and qp.qcm_id = ?2
and qp.archived = 0
and dc.statusPublication != 0
""")
    Optional<QCMPasse> findByUtilisateurAndQCM(Long idUtilisateur, Long idQcm);
    @Query(nativeQuery = true, value = """
select qp.*
from dl_qcm_passe qp
join dl_chapitre dc on qp.qcm_id = dc.id
where qp.utilisateur_id = ?1
and qp.qcm_id = ?2
and dc.statusPublication != 0
""")
    List<QCMPasse> findByUtilisateurAndQCMWithArchived(Long idUtilisateur, Long idQcm);
}
