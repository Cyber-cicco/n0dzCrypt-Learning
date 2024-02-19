package fr.diginamic.digilearning.dto;

import java.time.LocalDateTime;

public interface CoursPrevus {
    String getTitre();
    LocalDateTime getDatePrevue();
    Integer getOrdre();
    String getDureeEstimee();
}
