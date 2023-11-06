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
@Table(name = "MODULE")
public class Module {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @ManyToOne
    @JoinColumn(name = "ID_FORMATION")
    private Formation formation;
    @OneToMany(mappedBy = "module")
    private List<Cours> coursList;
    @Column(name = "LIBELLE_COURT")
    private String libelleCourt;

}
