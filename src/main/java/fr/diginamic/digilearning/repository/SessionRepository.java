package fr.diginamic.digilearning.repository;


import fr.diginamic.digilearning.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query(nativeQuery = true, value = """
select * from SESSION
where DATE_FIN > ?1 
""")
    List<Session> findByDateFinAfter(LocalDate date);

    @Query(nativeQuery = true, value = """
select * from SESSION
""")
    List<Session> findAll();

    @Query(nativeQuery = true, value = """
select count(session_id) from dl_salon_session where session_id = ?1 and salon_id = ?2
""")
    int hasAuthorization(Long idSession, Long idSalon);
    @Query(nativeQuery = true, value = """
delete from dl_salon_session where session_id = ?1 and salon_id = ?2
""")
    void removeAuthorization(Long idSession, Long idSalon);

    @Query(nativeQuery = true, value = """
insert into dl_salon_session(session_id, salon_id) values (?1, ?2)
""")
    void grandAuthorization(Long idSession, Long idSalon);

    @Query(nativeQuery = true, value = """
select S.* from SESSION S
join ADMINISTRATION_SESSION ASS on S.ID = ASS.ID_SESSION and ASS.ID_UTILISATEUR = ?1
where DATE_FIN > ?2
""")
    List<Session> findByResponsabiliteAndDatefin(Long id, LocalDate now);
}
