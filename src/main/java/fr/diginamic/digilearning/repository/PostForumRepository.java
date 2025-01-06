package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.PostForum;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostForumRepository extends JpaRepository<PostForum, Long>  {

    @Query(nativeQuery = true, value =
"""
select p.id, p.contenu, p.dateEmission, p.reponseA_id, U.PRENOM, U.NOM from dl_post_forum p
join UTILISATEUR U on p.auteur_id = U.ID
where p.fil_discussion_id = ?1
order by dateEmission asc
limit ?2, ?3
""")
    List<String[]> getPostInfosFromFil(Long idFil, Long offset, Long length);
}
