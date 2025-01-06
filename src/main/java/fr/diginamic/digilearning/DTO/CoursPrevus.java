package fr.diginamic.digilearning.DTO;

import java.time.LocalDateTime;

public interface CoursPrevus {
    String getTitre();
    LocalDateTime getDatePrevue();
    Integer getOrdre();
    String getDureeEstimee();
}
