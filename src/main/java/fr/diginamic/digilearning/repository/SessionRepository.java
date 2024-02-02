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
and ID_NEXT IS NULL
""")
    List<Session> findByDateFinAfter(LocalDate date);

    @Query(nativeQuery = true, value = """
select * from SESSION
where ID_NEXT IS NULL
""")
    List<Session> findAll();
}
