package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.SousModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SousModuleRepository extends JpaRepository<SousModule,Long>{

    @Query(nativeQuery = true, value = """
select sm.id, sm.titre, sm.photo, count(dfc.id), count(dc.id)
from dl_sous_module sm
join dl_module_smodule dms on sm.id = dms.id_smodule
join dl_module dm on dms.id_module = dm.id
join dl_module_formation dmf on dm.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
left outer join dl_cours dc on sm.id = dc.sous_module_id
left outer join dl_flag_cours dfc on dc.id = dfc.cours_id 
and dfc.stagiaire_id = ?1 
and dfc.finished = true
where U.ID = ?1
and dm.id = ?2
group by sm.id, sm.titre, sm.photo
""")
    List<String[]> findModulesByUtilisateur(Long idUtilisateur, Long idModule);
}
