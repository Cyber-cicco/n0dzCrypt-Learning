package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.QCMChoix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QCMChoixRepository extends JpaRepository<QCMChoix, Long> {
    @Query(nativeQuery = true, value = """
select qc.* 
from dl_qcm_choix qc
join dl_qcm_question qq on qq.id = qc.question_id
join dl_chapitre ch on ch.id = qq.qcm_id
join dl_cours c on ch.cours_id = c.id 
join dl_utilisateur_cours duc on c.id = duc.id_cours
where duc.id_utilisateur = ?2
and qc.id = ?1
""" )
    Optional<QCMChoix> findByIdAndAdminId(Long idChoix, Long id);
}
