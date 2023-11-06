package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;    

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
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
