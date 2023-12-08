package fr.diginamic.digilearning.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateUtil {
    public LocalDateTime getLdt(LocalDate date, LocalTime time) {
        return LocalDateTime.of(date, time);
    }

    public String getId(LocalDate date, LocalTime time) {
        return "T" + LocalDateTime.of(date, time).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"));
    }

    public String getHeure(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
