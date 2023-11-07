package fr.diginamic.digilearning.entities;

import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "BLOC")
public class Bloc   {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "REFERENCE")
    private String reference;

    @ManyToMany
    @JoinTable(name="DIPLOME_BLOC",
            joinColumns = @JoinColumn(name = "ID_BLOC", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_DIPLOME", referencedColumnName = "ID")
    )
    private List<Diplome> diplomeList;

    @OneToMany(mappedBy = "bloc")
    private List<CompetenceBloc> competenceList = new ArrayList<>();

}
