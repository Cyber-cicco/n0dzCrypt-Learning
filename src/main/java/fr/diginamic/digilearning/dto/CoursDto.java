package fr.diginamic.digilearning.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
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

    public boolean isAtDatePrevue(HourInfos hourInfos, DayInfos dayInfos) {
        return datePrevue.equals(LocalDateTime.of(dayInfos.dateJour(), hourInfos.time()));
    }
}
