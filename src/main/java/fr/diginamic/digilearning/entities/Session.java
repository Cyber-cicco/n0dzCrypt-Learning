package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;    
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "SESSION")
public class Session {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @ManyToOne
    @JoinColumn(name = "ID_FOR")
    private Formation formation;
    @Column(name = "DATE_DEB")
    private LocalDate dateDebut;
    @Column(name = "DATE_FIN")
    private LocalDate dateFin;
    @Column(name = "RESPONSABLE_PEDAGOGIQUE")
    private String responsablePedagogique;
    @ManyToMany
    @JoinTable(name = "dl_post_session",
            joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
    )
    private List<Post> postList;


}
