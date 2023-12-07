package fr.diginamic.digilearning.dto;

import java.time.LocalDate;

public record DayInfos(
        LocalDate dateJour,
        Integer dateNumber,
        String weekDay
) {}
