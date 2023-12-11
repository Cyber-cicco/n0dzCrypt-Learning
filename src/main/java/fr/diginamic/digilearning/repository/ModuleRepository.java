package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long>  {

    @Query(nativeQuery = true, value = """
select m.id, m.libelle, m.photo, count(dfc.id), count(dc.id)
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
group by m.id, libelle, photo
""")
    List<String[]> findModulesByUtilisateur(Long id);
}
