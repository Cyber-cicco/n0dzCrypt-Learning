package fr.diginamic.digilearning.repository;

import fr.diginamic.digilearning.entities.RelationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RelationQuestionRepository extends JpaRepository<RelationQuestion,Long>{
    @Query(nativeQuery = true, value = """
insert into dl_relation_question(disliked, liked, question_id, utilisateur_id) 
values (?1, ?2, ?3, ?4)
""")
    void insertRelationQuestion(Boolean disliked, Boolean liked, Long idQuestion, Long idUtilisateur);
}
