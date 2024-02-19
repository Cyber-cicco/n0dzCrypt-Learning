package fr.diginamic.digilearning.service.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DateOption {
    SEMAINE("Semaine"), SEMAINE_OUVREE("Semaine de travail"), JOURNEE("Journée");
    final String libelle;
}
