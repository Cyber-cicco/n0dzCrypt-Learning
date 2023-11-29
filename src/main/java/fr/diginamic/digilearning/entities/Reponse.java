package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dl_reponse")
public class Reponse implements Rated {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Boolean supprimee;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @OneToMany(mappedBy = "reponse")
    private List<RelationReponse> relationReponses;

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
}
