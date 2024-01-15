package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.QCMQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
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
    """)
    Optional<QCMQuestion> findByIdAndAdminId(Long idQuestion, Long idFormateur);
}
