package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "dl_fil_discussion")
public class FilDiscussion {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String titre;
    @OneToMany(mappedBy = "filDiscussion")
    private List<PostForum> postForumList;

    private Boolean supprime;

    private Boolean ferme;
    @ManyToOne
    @JoinColumn(name = "salon_id")
    private Salon salon;

}
