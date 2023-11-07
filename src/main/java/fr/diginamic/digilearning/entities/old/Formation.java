package fr.diginamic.digilearning.entities.old;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "FORMATION")
public class Formation {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "NOM_COURT")
    private String nomCourt;
    @Column(name = "REFERENCE")
    private String reference;
    @Column(name = "INFOS_CERTIF")
    private String infosCertif;
    @OneToMany(mappedBy = "formation")
    private List<Module> moduleList;


}
