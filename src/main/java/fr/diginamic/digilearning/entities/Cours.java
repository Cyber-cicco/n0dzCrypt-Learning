package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dl_cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private Integer difficulte;
    private Integer ordre;
    @ManyToMany
    @JoinTable(name = "dl_utilisateur_cours",
            joinColumns = @JoinColumn(name = "id_cours", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur", referencedColumnName = "ID")
    )
    private List<Utilisateur> auteurs;
    @ManyToOne
    @JoinColumn(name = "sous_module_id")
    private SousModule sousModule;
    @OneToMany(mappedBy = "cours")
    private List<Chapitre> chapitres = new ArrayList<>();

    @OneToMany(mappedBy = "cours")
    private List<FlagCours> flagCours = new ArrayList<>();
}
