package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusForum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "dl_salon")
public class Salon {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String titre;
    private StatusForum statusForum;
    @ManyToMany
    @JoinTable(name = "whitelist",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID")
    )
    private List<Utilisateur> whiteList;
    @ManyToMany
    @JoinTable(name = "blacklist",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID")
    )
    private List<Utilisateur> blackList;
    @ManyToMany
    @JoinTable(name = "salon_moderateurs",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "moderateur_id", referencedColumnName = "id")
    )
    private List<Utilisateur> moderateurs;
    @ManyToOne
    @JoinColumn(name = "sujet_id")
    private Sujet sujetForum;
    @OneToMany(mappedBy = "salon")
    private List<FilDiscussion> filDiscussionList;


}
