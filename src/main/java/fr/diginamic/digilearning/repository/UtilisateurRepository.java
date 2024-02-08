package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long>  {

    Optional<Utilisateur> findByEmail(String email);

    @Query(nativeQuery = true, value = """
select * from UTILISATEUR
join SESSION_STAGIAIRE SS on UTILISATEUR.ID = SS.ID_STAG
where SS.ID_SES = ?1
""")
    List<Utilisateur> findBySession(Long id);

    @Query(nativeQuery = true, value = """
update UTILISATEUR set dl_banned = true where ID = ?1
""")
    void ban(Long idUtilisateur);

    @Query(nativeQuery = true, value = """
select count(id) from UTILISATEUR where ID = ?1 and dl_banned = 1
""")
    int isBanned(Long idUtilisateur);

    @Query(nativeQuery = true, value = """
update UTILISATEUR set dl_banned = 0 where ID = ?1
""")
    void unban(Long id);

    @Query(nativeQuery = true, value = """
select DISTINCT U.* from UTILISATEUR U
join ROLE_UTILISATEUR RU on U.ID = RU.ID_UTILISATEUR and (RU.ID_ROLE = 1 OR RU.ID_ROLE = 3)
""")
    List<Utilisateur> findAllResponsables();
}
