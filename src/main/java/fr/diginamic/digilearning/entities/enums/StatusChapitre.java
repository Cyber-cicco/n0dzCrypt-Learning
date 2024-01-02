package fr.diginamic.digilearning.entities.enums;

import lombok.Getter;

/**
 * Status du chapitre, déterminant la façon dont il sera représenté dans le front
 */
@Getter
public enum StatusChapitre {
    COURS("Cours"), QCM("QCM"), EXERCICE("Exercice");

    private String libelle;

    StatusChapitre(String libelle){
        this.libelle = libelle;
    }
}
