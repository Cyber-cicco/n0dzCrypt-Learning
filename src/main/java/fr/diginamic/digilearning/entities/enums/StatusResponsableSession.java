package fr.diginamic.digilearning.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Permet de déterminer le role d'une personne dans une session
 */
@Getter
@AllArgsConstructor
public enum StatusResponsableSession {
    RESPONSABLE_PEDAGOGIQUE("Reponsable Pédagogique"),
    RESPONSABLE_ADMINISTRATIF("Responsable Administratif"),
    ROOM_MASTER("Room Master");

    private String libelle;
}
