package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Représente une ressource téléchargeable
 *
 * @author Abel Ciccoli
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "dl_ressource_cours")
public class RessourceCours {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**nom de la ressource affichée dans le front*/
    private String libelleAffiche;
    /**nom de la ressource dans le système de fichiers du serveur*/
    private String libelle;
    private String typeRessource;

}
