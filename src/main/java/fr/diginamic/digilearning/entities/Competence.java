package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="COMPETENCE")
public class Competence   {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LIBELLE")
    private String libelle;

    @OneToMany(mappedBy = "competence")
    private List<CompetenceBloc> comptenceBlocList;

    @ManyToMany
    @JoinTable(name="COMPETENCE_COURS",
            joinColumns = @JoinColumn(name = "ID_COMPETENCE", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_COURS", referencedColumnName = "ID")
    )
    private List<CoursRef> coursList = new ArrayList<>();

}

