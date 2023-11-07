package fr.diginamic.digilearning.entities;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class ChampBDC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected Long id;
    @Column(name = "NOM_CHAMP")
    private String nomChamp;

    @Column(name = "CONTENU_CHAMP")
    private String contenuChamp;

}