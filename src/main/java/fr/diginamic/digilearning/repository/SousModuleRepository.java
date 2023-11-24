package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.SousModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SousModuleRepository extends JpaRepository<SousModule,Long>{

    @Query(nativeQuery = true, value = """
select sm.*
from dl_sous_module sm
join dl_module_smodule dms on sm.id = dms.id_smodule
join dl_module dm on dms.id_module = dm.id
join dl_module_formation dmf on dm.id = dmf.id_module
join FORMATION F on dmf.id_formation = F.ID
join SESSION S on F.ID = S.ID_FOR
join SESSION_STAGIAIRE SS on S.ID = SS.ID_SES
join UTILISATEUR U on SS.ID_STAG = U.ID
where U.EMAIL = ?1
and dm.id = ?2
""")
    List<SousModule> findModulesByUtilisateur(String email, Long idModule);
}
