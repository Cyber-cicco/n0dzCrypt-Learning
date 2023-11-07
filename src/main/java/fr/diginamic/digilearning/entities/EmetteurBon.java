package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "EMETTEUR_BON")
public class EmetteurBon {

    /** identifiant */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /** Société émittrice */
	@ManyToOne
    @JoinColumn(name = "ID_SOCIETE", referencedColumnName = "ID")
    private Societe societeEmittrice;

    /** template */
    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
    private DocumentDiginamic template;

    /** isDefault */
    @Column(name="IS_DEFAULT")
    private Boolean isDefault;

}
