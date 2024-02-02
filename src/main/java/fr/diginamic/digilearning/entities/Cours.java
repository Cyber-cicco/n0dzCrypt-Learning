package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusPublication;
import fr.diginamic.digilearning.entities.enums.TypeCoursElement;
import jakarta.persistence.*;
import lombok.*;

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
public class Cours implements Comparable<Cours>, CoursElement {
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
    private List<CoursSession> coursSessions = new ArrayList<>();
    public List<Chapitre> getChapitres() {
        return chapitres.stream()
                .filter(chapitre -> !chapitre.getStatusPublication().equals(StatusPublication.NON_PUBLIE))
                .sorted(Comparator.comparing(Chapitre::getOrdre))
                .toList();
    }

    public Chapitre getChapitrePrecedent(Integer ordre) {
        Optional<Chapitre> chapitre = chapitres.stream().filter(c -> c.getOrdre().equals(ordre - 1)).findFirst();
        while (chapitre.isPresent() && chapitre.get().getStatusPublication().equals(StatusPublication.NON_PUBLIE)){
            Optional<Chapitre> finalChapitre = chapitre;
            chapitre = chapitres.stream()
                    .filter(c -> c.getOrdre().equals(finalChapitre.get().getOrdre() - 1))
                    .findFirst();
        }
        return chapitre.orElse(null);
    }
    public Chapitre getChapitreSuivant(Integer ordre) {
        Optional<Chapitre> chapitre = chapitres.stream().filter(c -> c.getOrdre().equals(ordre + 1)).findFirst();
        while (chapitre.isPresent() && chapitre.get().getStatusPublication().equals(StatusPublication.NON_PUBLIE)){
            Optional<Chapitre> finalChapitre = chapitre;
            chapitre = chapitres.stream()
                    .filter(c -> c.getOrdre().equals(finalChapitre.get().getOrdre() + 1))
                    .findFirst();
        }
        return chapitre.orElse(null);
    }

    public List<Chapitre> getAllChapitres(){
        return chapitres.stream()
                .sorted(Comparator.comparing(Chapitre::getOrdre))
                .toList();
    }

    @Override
    public int compareTo(Cours o) {
        int comp = ordre.compareTo(o.ordre);
        if(comp == 0){
            return titre.compareTo(o.titre);
        }
        return comp;
    }

    @Override
    public String getNom() {
        return titre;
    }

    @Override
    public TypeCoursElement getTypeElement() {
        return TypeCoursElement.COURS;
    }
}
