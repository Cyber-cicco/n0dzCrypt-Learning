package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une question posée sur un chapitre
 * Implémente une interface signifiant que l'entité peut etre like ou dislike par un
 * utilisateur
 *
 * @author Abel Ciccoli
 */
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

    /**
     * Permet de si une question a été like par un utilisateur donné
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @return true si l'utilisateur a liké la question
     */
    @Override
    public Boolean isLiked(Long idUtilisateur){
        return relationQuestions.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationQuestion::getLiked)
                .findFirst()
                .orElse(false);
    }
    /**
     * Permet de si une question a été dislike par un utilisateur donné
     * @param idUtilisateur l'identifiant de l'utilisateur
     * @return true si l'utilisateur a disliké la question
     */
    @Override
    public Boolean isDisliked(Long idUtilisateur){
        return relationQuestions.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationQuestion::getDisliked)
                .findFirst()
                .orElse(false);
    }


    /**
     * Permet de récupérer le nombre de likes de la question
     * @return le nombre de likes de la question
     */
    @Override
    public int getLikes() {
        return relationQuestions.stream().filter(RelationQuestion::getLiked).toList().size();
    }

    /**
     * Permet de récupérer le nombre de dislikes de la question
     * @return le nombre de likes de la question
     */
    @Override
    public int getDislikes() {
        return relationQuestions.stream().filter(RelationQuestion::getDisliked).toList().size();
    }

    /**
     * Getter de relationsQuestions
     * @return les relations à la question
     */
    @Override
    public List<RelationQuestion> getRelations() {
        return relationQuestions;
    }

    public void addReponse(Reponse reponse) {
        reponses.add(reponse);
    }
}
