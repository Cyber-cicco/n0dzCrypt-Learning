package fr.diginamic.digilearning.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeCoursElement {
    FORMATION("Formation"),  MODULE("Module"), SOUS_MODULE("Sous module"), COURS("Cours");
    private String libelle;
}
