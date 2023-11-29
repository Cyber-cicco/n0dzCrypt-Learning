package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long>{

    @Query(nativeQuery = true, value = """
    select q.* 
    from dl_question q 
    join dl_chapitre ch on ch.id = q.chapitre_id
    join dl_cours co on ch.cours_id = co.id
    join dl_sous_module sm on co.sous_module_id = sm.id
    join dl_module_smodule dms on sm.id = dms.id_smodule
    join dl_module dm on dms.id_module = dm.id
    join dl_module_formation dmf on dm.id = dmf.id_module
    join FORMATION F on dmf.id_formation = F.ID
    join SESSION S on F.ID = S.ID_FOR
    join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
    join UTILISATEUR U on SS.ID_STAG = U.ID
    where q.id = ?1
    and U.ID = ?2
    """)
    Optional<Question> findByIdAndUtilisateurId(Long idQuestion, Long idUtilisateur);
}
