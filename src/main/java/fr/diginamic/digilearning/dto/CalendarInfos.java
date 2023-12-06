package fr.diginamic.digilearning.dto;

import fr.diginamic.digilearning.page.service.enums.DateOption;

public record CalendarInfos(
        String month,
        DateOption dateOption
) {}
