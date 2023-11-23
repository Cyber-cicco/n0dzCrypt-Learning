package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "dl_fil_discussion")
public class FilDiscussion {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String titre;
    private String description;
    @OneToMany(mappedBy = "filDiscussion")
    private List<PostForum> postForumList;
    private Boolean supprime;
    private Boolean ferme;
    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
    private LocalDateTime dateCreation;
    private Boolean epingle;

    public String getDateCreationString() {
        return dateCreation.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
