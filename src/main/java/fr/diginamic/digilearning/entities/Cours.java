package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Représente un cours e-learning
 *
 * @author Abel Ciccoli
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dl_cours")
public class Cours implements Comparable<Cours> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private Integer difficulte;
    private Integer ordre;
    private Integer dureeEstimee;
    @ManyToMany
    @JoinTable(name = "dl_utilisateur_cours",
            joinColumns = @JoinColumn(name = "id_cours", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_utilisateur", referencedColumnName = "ID")
    )
    private List<Utilisateur> auteurs = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "sous_module_id")
    private SousModule sousModule;
    @OneToMany(mappedBy = "cours")
    @Builder.Default
    private List<Chapitre> chapitres = new ArrayList<>();

    @OneToMany(mappedBy = "cours")
    @Builder.Default
    private List<FlagCours> flagCours = new ArrayList<>();
    @OneToMany(mappedBy = "cours")
    @Builder.Default
    private List<QCM> exercicesQCM = new ArrayList<>();

    public List<Chapitre> getChapitres() {
        return chapitres.stream().sorted(Comparator.comparing(Chapitre::getOrdre)).toList();
    }

    public Chapitre getChapitrePrecedent(Integer ordre) {
        Optional<Chapitre> chapitre = chapitres.stream().filter(c -> c.getOrdre().equals(ordre - 1)).findFirst();
        return chapitre.orElse(null);
    }
    public Chapitre getChapitreSuivant(Integer ordre) {
        Optional<Chapitre> chapitre = chapitres.stream().filter(c -> c.getOrdre().equals(ordre + 1)).findFirst();
        return chapitre.orElse(null);
    }

    @Override
    public int compareTo(Cours o) {
        int comp = ordre.compareTo(o.ordre);
        if(comp == 0){
            return titre.compareTo(o.titre);
        }
        return comp;
    }
}
