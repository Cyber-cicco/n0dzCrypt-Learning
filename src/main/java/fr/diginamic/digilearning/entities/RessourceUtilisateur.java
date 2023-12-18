package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Représente une ressource de l'utilisateur
 *
 * @author digiCAP
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ds_ressource_utilisateur")
public class RessourceUtilisateur {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private LocalDate dateEnvoi;
    private String nomAffiche;
    private String nomRessource;
    private String typeDocument;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;


}
