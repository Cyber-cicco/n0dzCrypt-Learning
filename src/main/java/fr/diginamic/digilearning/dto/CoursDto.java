package fr.diginamic.digilearning.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CoursDto {
    private Long id;
    private String titre;
    private Integer difficulte;
    private Integer ordre;
    private Integer dureeEstimee;
    private Boolean boomarked;
    private Boolean finished;
    private Boolean liked;
    private LocalDateTime datePrevue;
    private String titreSousModule;

    public boolean isAtDatePrevue(HourInfos hourInfos, DayInfos dayInfos) {
        return datePrevue.equals(LocalDateTime.of(dayInfos.dateJour(), hourInfos.time()));
    }
}
