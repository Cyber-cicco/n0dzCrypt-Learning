package fr.diginamic.digilearning.entities.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * Status d'un ASE
 * Artefact de digiCAP
 */
@AllArgsConstructor
public enum StatusAse {
    A_PLACE("A placer"), PLACEMENT_EN_COURS("Placement en cours"), PLACE("Placé!");

    @JsonValue
    public String getPlacement() {
        return placement;
    }

    private String placement;

}
