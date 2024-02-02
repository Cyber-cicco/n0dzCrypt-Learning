package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeCoursElement;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Réprésente le sous-module répertoriant une liste de cours donnée
 * Plusieurs modules peuvent partager un meme sous module
 *
 * @author Abel Ciccoli
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dl_sous_module")
public class SousModule implements Comparable<SousModule>, CoursElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String photo;
    private Integer ordre;
    @OneToMany(mappedBy = "sousModule")
    private List<Cours> cours = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "dl_module_smodule",
            inverseJoinColumns = @JoinColumn(name = "id_module", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "id_smodule", referencedColumnName = "id")
    )
    @Builder.Default
    private List<Module> modules = new ArrayList<>();

    @Override
    public int compareTo(SousModule o) {
        if(o.ordre == null){
            return titre.compareTo(o.titre);
        }
        if(o.ordre > ordre) return -1;
        if(o.ordre.equals(ordre)) {
            return titre.compareTo(o.titre);
        }
        return 1;
    }

    public List<Cours> getCours(){
        return cours.stream().sorted().toList();
    }

    @Override
    public String getNom() {
        return titre;
    }

    @Override
    public TypeCoursElement getTypeElement() {
        return TypeCoursElement.SOUS_MODULE;
    }
}
