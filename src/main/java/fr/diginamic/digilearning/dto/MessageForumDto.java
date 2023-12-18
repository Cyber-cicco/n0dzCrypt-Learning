package fr.diginamic.digilearning.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représentation d'un message du forum avec en
 * plus l'id du message auquel il répond et le nom et
 * prénom de son auteur
 */
@Getter
public class MessageForumDto {

    private Long id;
    private String contenu;
    private LocalDateTime dateEmission;
    private Long reponseA_id;
    private String prenom;
    private String nom;

    /**
     * Permet de récupérer le nom complet bien formatté de l'auteur du poste
     * @return le nom complet de l'auteur du post
     */
    public String getFullName(){
        return nom.toUpperCase() + " " + prenom;
    }

    /**
     * Permet de récupérer la date d'émission formattée pour l'affichage dans le front
     * @return la date d'émission formattée.
     */
    public String getDateEmissionString(){
        return dateEmission.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
