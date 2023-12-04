package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_relation_reponse")
public class RelationReponse implements RelationLiked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean liked;
    private Boolean disliked;
    @ManyToOne
    @JoinColumn(name = "reponse_id")
    private Reponse reponse;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}
