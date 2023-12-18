package fr.diginamic.digilearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Représentation d'un nouveau fil envoyé du front
 * par un utilisateur souhaitant créer un nouveau fil.
 */
@Getter
@AllArgsConstructor
public class PostFilDto {
    private String titre;
    private String description;
}
