package fr.diginamic.digilearning.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeCoursElement {
    FORMATION("Formation"), COURS("Cours"), MODULE("Module"), SOUS_MODULE("Sous module");
    private String libelle;
}
