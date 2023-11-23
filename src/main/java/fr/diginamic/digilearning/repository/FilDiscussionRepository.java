package fr.diginamic.digilearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.digilearning.entities.FilDiscussion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilDiscussionRepository extends JpaRepository<FilDiscussion, Long>  {

    @Query("select f from FilDiscussion f where f.salon = null")
    FilDiscussion findRegles();

    @Query(nativeQuery = true, value =
            """
select * from dl_fil_discussion 
where salon_id = ?1 
and supprime != 1
order by epingle desc, dateCreation desc 
"""
    )
    List<FilDiscussion> findDiscussion(Long id);

    @Query(nativeQuery = true, value =
            """
select count(id) from dl_post_forum
where fil_discussion_id = ?1 
""")
    Integer countMessagesOfFil(Long id);
}
