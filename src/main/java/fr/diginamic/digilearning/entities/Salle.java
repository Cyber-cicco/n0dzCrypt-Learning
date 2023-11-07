package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.TypeRessource;
import fr.diginamic.digilearning.entities.enums.TypeSalle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "SALLE")
public class Salle implements Ressource {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    /** Nom de la salle */
    @Column(name = "NOM")
    private String nom;

    /** Type (informatique ou communication) */
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeSalle type;

    /** capacite d'accueil en nombre de stagiaires */
    @Column(name = "capacite")
    private Integer capacite;

    /** centre où se trouve la salle */
    @ManyToOne
    @JoinColumn(name = "ID_CENT")
    private Centre centre;

    /** sessions qui occupent cette salle */
    @OneToMany(mappedBy = "salle")
    private List<Session> sessions;

    /** dateFinValidite : LocalDate */
    @Column(name = "DATE_FIN_VALIDITE")
    private LocalDate dateFinValidite;

    /** indisponibilites : List de IndisponibiliteSalle */
    @OneToMany(mappedBy = "salle")
    private List<IndisponibiliteSalle> indisponibilites = new ArrayList<>();

    @Override
    public TypeRessource getTypeRessource() {
        return TypeRessource.SALLE;
    }

    @Override
    public String getAttribute() {
        return "salle.nom";
    }

    @Override
    public String getValue() {
        return this.nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }

    @Override
    public List<IndisponibiliteSalle> getIndisponibilites() {
        return indisponibilites;
    }
}