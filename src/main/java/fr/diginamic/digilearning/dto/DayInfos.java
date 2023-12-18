package fr.diginamic.digilearning.dto;

import java.time.LocalDate;

/**
 * Structure contenant des informations sur la journée
 * @param dateJour la date stockée en tant que date
 * @param dateNumber le jour dans le mois
 * @param weekDay le nom du jour (lundi, mardi, etc.)
 */
public record DayInfos(
        LocalDate dateJour,
        Integer dateNumber,
        String weekDay
) {}
