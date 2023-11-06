package fr.diginamic.digilearning.entities;

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
@Table(name = "COURS")
public class Cours {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "LIBELLE_COURT")
    private String libelleCourt;
    @ManyToOne
    @JoinColumn(name = "ID_COURS_REF")
    private CoursRef coursRef;
    @Column(name = "TYPE_COURS")
    private String typeCours;

    @ManyToOne
    @JoinColumn(name = "ID_MODULE")
    private Module module;
    @OneToMany(mappedBy = "cours")
    private List<Chapitre> chapitreList;


}
