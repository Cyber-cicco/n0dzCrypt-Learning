package fr.diginamic.digilearning.entities.old;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "CHAPITRE")
public class Chapitre {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "LIBELLE")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "ID_COURS")
    private Cours cours;

}
