package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ChapitreRepository extends JpaRepository<Chapitre, Long>  {
    @Query(nativeQuery = true, value = """
    select ch.* 
    from dl_chapitre ch
    join dl_cours co on ch.cours_id = co.id
    join dl_sous_module sm on co.sous_module_id = sm.id
    join dl_module_smodule dms on sm.id = dms.id_smodule
    join dl_module dm on dms.id_module = dm.id
    join dl_module_formation dmf on dm.id = dmf.id_module
    join FORMATION F on dmf.id_formation = F.ID
    join SESSION S on F.ID = S.ID_FOR
    join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
    join UTILISATEUR U on SS.ID_STAG = U.ID
    where ch.id = ?1
    and ch.statusPublication != 0
    and U.ID = ?2
    """)
    Optional<Chapitre> findByIdAndUtilisateurId(Long idChapitre, Long idUtilisateur);


    @Query(nativeQuery = true, value = """
    select ch.* 
    from dl_chapitre ch
    join dl_cours co on ch.cours_id = co.id
    join dl_utilisateur_cours duc on co.id = duc.id_cours
    where ch.id = ?1
    and duc.id_utilisateur = ?2
    """)
    Optional<Chapitre> findByIdAndAdminId(Long idChapitre, Long idUtilisateur);

    @Query(nativeQuery = true, value = """
    select ch.* 
    from dl_chapitre ch
    join dl_cours co on ch.cours_id = co.id
    join dl_utilisateur_cours duc on co.id = duc.id_cours
    join dl_qcm_question dqq on ch.id = dqq.qcm_id
    where dqq.id = ?2
    and duc.id_utilisateur = ?1
    """)
    Optional<Chapitre> findByAdminIdAndQuestionId(Long id, Long idQuestion);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = """
update dl_chapitre ch
set ordre = ordre + 1
where ch.cours_id = ?3
and ch.ordre <= ?1
and ch.ordre >= ?2
    """)
    void updateOrdreDescendant(int oldOrdre, int ordre, long idChapitre);
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = """
update dl_chapitre ch
set ordre = ordre - 1
where ch.cours_id = ?3
and ch.ordre between ?1 and ?2
    """)
    void updateOrdreAscendant(int oldOrdre, int ordre, Long idCours);
    @Modifying
    @Query(nativeQuery = true, value = """
update dl_chapitre ch
set ordre = ordre - 1
where ch.cours_id = ?2
and ch.ordre > ?1
    """)
    void updateOrdreAfterSuppression(int ordre, Long idCours);
}
