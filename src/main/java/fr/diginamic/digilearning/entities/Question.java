package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.repository.RelationQuestionRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dl_question")
public class Question implements Rated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
    private Boolean supprimee;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Reponse> reponses;
    @ManyToOne
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;
    @OneToMany(mappedBy = "question")
    private List<RelationQuestion> relationQuestions;

    public List<Reponse> getReponses() {
        return reponses.stream().filter(reponse -> !reponse.getSupprimee()).toList();
    }
    @Override
    public Boolean isLiked(Long idUtilisateur){
        return relationQuestions.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationQuestion::getLiked)
                .findFirst()
                .orElse(false);
    }
    @Override
    public Boolean isDisliked(Long idUtilisateur){
        return relationQuestions.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationQuestion::getDisliked)
                .findFirst()
                .orElse(false);
    }


    @Override
    public int getLikes() {
        return relationQuestions.stream().filter(RelationQuestion::getLiked).toList().size();
    }

    @Override
    public int getDislikes() {
        return relationQuestions.stream().filter(RelationQuestion::getDisliked).toList().size();
    }
}
