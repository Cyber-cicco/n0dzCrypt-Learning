package fr.diginamic.digilearning.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CoursAdminDto implements Comparable<CoursAdminDto> {

    private Long id;
    private String titre;
    private Integer difficulte;
    private Integer ordre;
    private Integer dureeEstimee;
    private LocalDateTime datePrevue;
    private String titreSousModule;
    @Override
    public int compareTo(CoursAdminDto o) {
        int comp = ordre.compareTo(o.getOrdre());
        if(comp == 0){
            return titre.compareTo(o.getTitre());
        }
        return comp;
    }
}
