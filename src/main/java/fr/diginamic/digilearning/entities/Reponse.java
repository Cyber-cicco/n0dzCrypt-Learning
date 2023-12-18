package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Réponse à une question posée à propos d'un chapitre
 *
 * @author Abel Ciccoli
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_reponse")
public class Reponse implements Rated<RelationReponse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Utilisateur auteur;
    private Boolean supprimee;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @OneToMany(mappedBy = "reponse")
    @Builder.Default
    private List<RelationReponse> relationReponses = new ArrayList<>();

    @Override
    public Boolean isLiked(Long idUtilisateur) {
        return relationReponses.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationReponse::getLiked)
                .findFirst()
                .orElse(false);
    }

    @Override
    public Boolean isDisliked(Long idUtilisateur) {
        return relationReponses.stream()
                .filter(relationQuestion -> relationQuestion.getUtilisateur().getId().equals(idUtilisateur))
                .map(RelationReponse::getDisliked)
                .findFirst()
                .orElse(false);
    }

    @Override
    public int getLikes() {
        return relationReponses.stream().filter(RelationReponse::getLiked).toList().size();
    }

    @Override
    public int getDislikes() {
        return relationReponses.stream().filter(RelationReponse::getDisliked).toList().size();
    }

    @Override
    public List<RelationReponse> getRelations() {
        return relationReponses;
    }
}
