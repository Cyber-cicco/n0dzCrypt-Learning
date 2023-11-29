package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CoursRepository extends JpaRepository<Cours, Long>  {

    @Query(nativeQuery = true, value = """

select c.id, c.titre, c.difficulte, c.ordre, fc.boomarked, fc.finished, fc.liked
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
}
