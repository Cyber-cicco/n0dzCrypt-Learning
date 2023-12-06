package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.CalendarInfos;
import fr.diginamic.digilearning.dto.DayInfos;
import fr.diginamic.digilearning.page.service.enums.DateOption;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AgendaService {

    private final Map<String, String> englishWeekDayToFr = new HashMap<>();
    private final Map<String, String> englishMonthToFr = new HashMap<>();

    public AgendaService() {
        englishWeekDayToFr.put("MONDAY", "Lundi");
        englishWeekDayToFr.put("TUESDAY", "Mardi");
        englishWeekDayToFr.put("WEDNESDAY", "Mercredi");
        englishWeekDayToFr.put("THURSDAY", "Jeudi");
        englishWeekDayToFr.put("FRIDAY", "Vendredi");
        englishWeekDayToFr.put("SATURDAY", "Samedi");
        englishWeekDayToFr.put("SUNDAY", "Dimanche");

        englishMonthToFr.put("JANUARY", "Janvier");
        englishMonthToFr.put("FEBRUARY", "Févirier");
        englishMonthToFr.put("MARCH", "Mars");
        englishMonthToFr.put("APRIL", "Avril");
        englishMonthToFr.put("MAY", "Mai");
        englishMonthToFr.put("JUNE", "Juin");
        englishMonthToFr.put("JULY", "Juillet");
        englishMonthToFr.put("AUGUST", "Aôut");
        englishMonthToFr.put("SEPTEMBER", "Septembre");
        englishMonthToFr.put("OCTOBER", "Octobre");
        englishMonthToFr.put("NOVEMBER", "Novembre");
        englishMonthToFr.put("DECEMBER", "Décembre");
    }

    public List<String> getHeuresJournee() {
        List<String> heures = new ArrayList<>(24);
        for (int i = 0; i < 24; i++) {
            heures.add(String.valueOf((i < 10) ? "0" + i : i));
        }
        return heures;
    }

    public CalendarInfos getCalendarInfos(LocalDate date) {
        return new CalendarInfos(
                englishMonthToFr.get(date.getMonth().toString()) + " " + date.getYear(),
                DateOption.SEMAINE_OUVREE
        );
    }

    public List<DayInfos> getDayInfosForWeek(LocalDate date) {
        List<DayInfos> dayInfos = new ArrayList<>();
        date = date.minusDays(date.getDayOfWeek().getValue() - 1);
        while (date.getDayOfWeek().getValue() != 6){
            dayInfos.add(new DayInfos(
                    date.getDayOfMonth(),
                    englishWeekDayToFr.get(date.getDayOfWeek().toString())
            ));
            date = date.plusDays(1);
        }
        return dayInfos;
    }
}
