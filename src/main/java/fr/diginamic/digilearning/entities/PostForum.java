package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "dl_post_forum")
public class PostForum {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String contenu;
    private LocalDateTime dateEmission;
    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Utilisateur auteur;

    @Column(name = "auteur_id", updatable = false, insertable = false)
    private Long auteurId;
    @ManyToOne
    @JoinColumn(name = "fil_discussion_id")
    private FilDiscussion filDiscussion;
    @ManyToOne
    @JoinColumn(name = "reponseA_id")
    private PostForum reponseA;

    @OneToMany(mappedBy = "reponseA")
    private List<PostForum> reponses;
}
