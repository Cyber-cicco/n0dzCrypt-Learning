package fr.diginamic.digilearning.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MessageForumDto {

    private Long id;
    private String contenu;
    private LocalDateTime dateEmission;
    private Long reponseA_id;
    private String prenom;
    private String nom;

    public String getFullName(){
        return nom.toUpperCase() + " " + prenom;
    }

    public String getDateEmissionString(){
        return dateEmission.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
