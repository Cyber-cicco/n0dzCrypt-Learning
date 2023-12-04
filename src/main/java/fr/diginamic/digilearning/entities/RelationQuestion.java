package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_relation_question")
public class RelationQuestion implements RelationLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean liked;
    private Boolean disliked;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @Override
    public String toString() {
        return "RelationQuestion{" +
                "id=" + id +
                ", liked=" + liked +
                ", disliked=" + disliked +
                '}';
    }
}
