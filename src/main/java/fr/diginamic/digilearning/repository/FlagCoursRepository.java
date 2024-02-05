package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlagCoursRepository extends JpaRepository<FlagCours,Long>{
    Optional<FlagCours> findByCoursAndStagiaire(Cours cours, Utilisateur stagiaire);
    Optional<FlagCours> findByCoursAndStagiaire_Id(Cours cours, Long id);
    List<FlagCours> findByDatePrevueBetweenAndStagiaire_Id(LocalDateTime ldt1, LocalDateTime ldt2, Long id);

    @Query(nativeQuery = true, value = """
update dl_flag_cours fc
join dl_cours dc on fc.cours_id = dc.id
set fc.datePrevue = null
where fc.stagiaire_id in (
    select U.ID from UTILISATEUR U
    join SESSION_STAGIAIRE SS on U.ID = SS.ID_STAG
    where SS.ID_SES = ?3
)
-- date1 debut = fc.datePrevue
-- date1 fin = fc.datePrevue, INTERVAL dc.dureeEstimee HOUR
-- date2 debut = ?1
-- date2 fin = ?1, INTERVAL (select dc.dureeEstimee from dl_cours where id = ?2) HOUR
and (
    ?1 -- date2 debut
        between 
            fc.datePrevue  -- date1 debut
            and 
            date_add(fc.datePrevue, INTERVAL dc.dureeEstimee HOUR) -- date1 fin
    OR date_add(?1, INTERVAL (select dc.dureeEstimee from dl_cours where id = ?2) HOUR)  -- date2 fin
        between  
            fc.datePrevue -- date 1 debut
            and
            date_add(fc.datePrevue, INTERVAL dc.dureeEstimee HOUR) -- date 1 fin
    OR fc.datePrevue -- date 1 debut
        between 
            ?1 -- date 2 debut
            and 
            date_add(?1, INTERVAL (select dc.dureeEstimee from dl_cours where id = ?2) HOUR)  -- date 2 fin
    OR date_add(fc.datePrevue, INTERVAL dc.dureeEstimee HOUR) -- date 1 fin
        between 
            ?1 -- date 2 debut
            and 
            date_add(?1, INTERVAL (select dc.dureeEstimee from dl_cours where id = ?2) HOUR)  -- date 2 fin
)
""")
    void setToNullForDateAndSession(LocalDateTime temps, Long idCours, Long idSession);
}
