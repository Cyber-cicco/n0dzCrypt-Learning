package fr.diginamic.digilearning.DTO;

import fr.diginamic.digilearning.service.enums.DateOption;

/**
 * Structure représentant les informations nécessaires pour irriguer le calendrier
 * @param month le mois concerné
 * @param dateOption l'option choisie pour la réprésentation des journées sur le calendrier.
 */
public record CalendarInfos(
        String month,
        DateOption dateOption
) {}
