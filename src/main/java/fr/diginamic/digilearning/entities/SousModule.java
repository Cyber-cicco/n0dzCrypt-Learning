package fr.diginamic.digilearning.entities;

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
public class SousModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String photo;
    @OneToMany(mappedBy = "sousModule")
    private List<Cours> cours = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "dl_module_smodule",
            inverseJoinColumns = @JoinColumn(name = "id_module", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "id_smodule", referencedColumnName = "id")
    )
    private List<Module> module = new ArrayList<>();

}
