package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long>  {

    @Query(nativeQuery = true, value = """

select c.id, c.titre, c.difficulte, c.ordre, c.dureeEstimee, fc.boomarked, fc.finished, fc.liked, fc.datePrevue
from dl_cours c
join dl_sous_module sm on c.sous_module_id = sm.id
join dl_module_smodule dms on sm.id = dms.id_smodule
join dl_module dm on dms.id_module = dm.id
join dl_module_formation dmf on dm.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
left outer join dl_flag_cours fc on c.id = fc.cours_id and fc.stagiaire_id = ?1
where U.ID = ?1
and sm.id = ?2
""")
    List<String[]> findByUserAndSousModule(Long idUtilisateur, Long idSModule);

    @Query(nativeQuery = true, value = """

select c.*
from dl_cours c
join dl_sous_module sm on c.sous_module_id = sm.id
join dl_module_smodule dms on sm.id = dms.id_smodule
join dl_module dm on dms.id_module = dm.id
join dl_module_formation dmf on dm.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
where U.ID = ?1
and c.id = ?2
""")
    Optional<Cours> findByUserAndId(Long idUtilisateur, Long idCours);

    @Query(nativeQuery = true, value = """

select c.*
from dl_cours c
join dl_flag_cours dfc on c.id = dfc.cours_id
where dfc.stagiaire_id = ?1
and dfc.boomarked = true
""")
    List<Cours> getBookMarked(Long idUtilisateur);
    @Query(nativeQuery = true, value = """
select c.id, c.titre, c.difficulte, c.ordre, c.dureeEstimee, fc.boomarked, fc.finished, fc.liked, fc.datePrevue
from dl_cours c
left outer join dl_flag_cours fc on c.id = fc.cours_id and fc.stagiaire_id = ?1
join dl_sous_module dsm on c.sous_module_id = dsm.id
join dl_module_smodule dms on dsm.id = dms.id_smodule
join dl_module dm on dms.id_module = dm.id
join dl_module_formation dmf on dm.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
where SS.ID_STAG = ?1
order by dm.libelle, dsm.titre, c.ordre
""")
    List<String[]> getAllCoursForUser(Long idUtilisateur);

    @Query(nativeQuery = true, value = """
select c.id, c.titre, c.difficulte, c.ordre, c.dureeEstimee, fc.boomarked, fc.finished, fc.liked, fc.datePrevue
from dl_cours c
join dl_flag_cours fc on c.id = fc.cours_id
where fc.stagiaire_id = ?1
and fc.datePrevue IS NOT NULL
""")
    List<String[]> getCoursPrevus(Long idUtilisateur);

    @Query(nativeQuery = true, value = """
select c.id, c.titre, c.difficulte, c.ordre, c.dureeEstimee, fc.boomarked, fc.finished, fc.liked, fc.datePrevue, dsm.titre
from dl_cours c
join dl_flag_cours fc on c.id = fc.cours_id
join dl_sous_module dsm on c.sous_module_id = dsm.id
where fc.stagiaire_id = ?1
and DATE(fc.datePrevue) = CURDATE()
order by fc.datePrevue
""")
    List<String[]> getPrevusCeJour(Long id);

    @Query(nativeQuery = true, value = """
select count(dc.id), count(dfc.id)
from dl_module m  
join dl_module_formation dmf on m.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
join dl_module_smodule on m.id = dl_module_smodule.id_module
left outer join dl_sous_module on dl_module_smodule.id_smodule = dl_sous_module.id
left outer join dl_cours dc on dl_sous_module.id = dc.sous_module_id
left outer join dl_flag_cours dfc on dc.id = dfc.cours_id 
and dfc.stagiaire_id = ?1 
and dfc.finished = true
where U.ID = ?1
""")
    List<String[]> getProgression(Long id);

    @Query(nativeQuery = true, value = """
select count(dc.id)
from dl_module m  
join dl_module_formation dmf on m.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
join dl_module_smodule on m.id = dl_module_smodule.id_module
left outer join dl_sous_module on dl_module_smodule.id_smodule = dl_sous_module.id
left outer join dl_cours dc on dl_sous_module.id = dc.sous_module_id
where U.ID = ?1
""")
    Integer getNbCours(Utilisateur utilisateur);

    @Query(nativeQuery = true, value = """
select c.*
from dl_cours c 
join dl_utilisateur_cours duc on c.id = duc.id_cours
where duc.id_utilisateur = ?1
""")
    List<Cours> getCoursCrees(Long id);

    @Query(nativeQuery = true, value = """
select c.* 
from dl_cours c
join dl_utilisateur_cours duc on c.id = duc.id_cours
where duc.id_utilisateur = ?2
and c.id = ?1
""")
    Optional<Cours> getCoursByIdAndFormateur(Long idCours, Long id);

    @Query("select count(c)  from Chapitre c where c.cours.id = :id")
    Integer findNombreChapitre(Long id);
}
