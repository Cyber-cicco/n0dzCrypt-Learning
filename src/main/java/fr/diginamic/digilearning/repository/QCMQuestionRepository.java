package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.QCMQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QCMQuestionRepository extends JpaRepository<QCMQuestion,Long>{
    @Query(nativeQuery = true, value = """
select qq.* 
from dl_qcm_question qq
join dl_chapitre ch on ch.id = qq.qcm_id
join dl_cours c on ch.cours_id = c.id 
join dl_utilisateur_cours duc on c.id = duc.id_cours
where duc.id_utilisateur = ?2
and qq.id = ?1
and qq.archived = 0;
    """)
    Optional<QCMQuestion> findByIdAndAdminId(Long idQuestion, Long idFormateur);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = """
update dl_qcm_question qq
set ordre = ordre - 1
where qq.qcm_id = ?3
and qq.ordre between ?1 and ?2
and qq.archived = 0
    """)
    void updateOrdreAscendant(int oldOrdre, int ordre, long idQuestion);
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = """
update dl_qcm_question qq
set ordre = ordre + 1
where qq.qcm_id = ?3
and qq.ordre <= ?1
and qq.ordre >= ?2
and qq.archived = 0
    """)
    void updateOrdreDescendant(int oldOrdre, int ordre, long idChapitre);

    @Query(nativeQuery = true, value = """
update dl_qcm_question qq
set ordre = ordre - 1
where qq.qcm_id = ?2
and qq.ordre > ?1
and qq.archived = 0
    """)
    void updateOrdreAfterSuppression(int ordre, Long idChapitre);
}
