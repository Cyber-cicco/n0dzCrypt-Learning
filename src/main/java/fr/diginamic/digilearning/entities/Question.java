package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.repository.RelationQuestionRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_question")
public class Question implements Rated<RelationQuestion> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
    private Boolean supprimee;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Reponse> reponses = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;
    @OneToMany(mappedBy = "question")
    @Builder.Default
    private List<RelationQuestion> relationQuestions = new ArrayList<>();

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

    @Override
    public List<RelationQuestion> getRelations() {
        return relationQuestions;
    }

    public void addReponse(Reponse reponse) {
        reponses.add(reponse);
    }
}
