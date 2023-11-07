package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "DIPLOME")
public class Diplome   {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LIBELLE")
    private String libelle;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "NIVEAU")
    private Integer niveau;

    @Column(name = "CERTIFICATEUR")
    private String certificateur;

    @Column(name = "LIEN_DOCUMENTATION")
    private String lienDocumentation;

    @Column(name = "DATE_FIN_VALIDITE")
    private LocalDate dateFinValidite;

    @ManyToMany
    @JoinTable(name="DIPLOME_BLOC",
            joinColumns = @JoinColumn(name = "ID_DIPLOME", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_BLOC", referencedColumnName = "ID")
    )
    @Fetch(value = FetchMode.JOIN)
    private List<Bloc> blocs;
}
