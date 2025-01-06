package fr.diginamic.digilearning.DTO;

import java.time.LocalTime;

/**
 * Information sur une heure données pour le calendrier
 * @param hour l'heure dans sa représentation en tant que chaine de caractère (00h, 01h, etc.)
 * @param time le temps en tant qu'objet de classe LocalTime
 */
public record HourInfos(
        String hour,
        LocalTime time
)
{}
