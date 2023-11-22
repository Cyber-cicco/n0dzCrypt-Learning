package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.Salon;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SalonRepository extends JpaRepository<Salon, Long>  {

    @Query(nativeQuery = true, value =
            """
select s.id, s.statusForum, s.sujet_id, s.titre
from dl_salon s 
join whitelist w on s.id = w.salon_id
where w.utilisateur_id = ?2 
and s.id = ?1
union select s.id, s.statusForum, s.sujet_id, s.titre 
from dl_salon s where s.statusForum = 0 
and s.id = ?1
and ?2 not in (select bu.utilisateur_id from blacklist bu where bu.salon_id = ?1);
"""
    )
    Optional<Salon> getSalonByIdAndCheckAuthorized(Long idSalon, Long idUtilisateur);

    @Query(nativeQuery = true, value =
            """
select case when exists(
    select f.id from dl_fil_discussion f
    join dl_salon ds on f.salon_id = ds.id
    where ?2 not in (select b.utilisateur_id from blacklist b where b.utilisateur_id = ?2 and b.salon_id = f.salon_id)
    and f.id = ?1
    and ds.statusForum = 0
)
then cast(1 as INT)
else ( 
    select case when exists(
        select f.id from dl_fil_discussion f 
        join whitelist w on f.salon_id = w.salon_id
        where f.id = ?1
        and w.utilisateur_id = ?2
    )
    then cast(1 as INT)
    else cast(0 as INT)
    end
    )
end
""")
    int getForumByIdAndCheckIfAuthorized(Long idFil, Long idUtilisateur);
}
