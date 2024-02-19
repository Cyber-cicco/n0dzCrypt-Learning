package fr.diginamic.digilearning.dto;

import fr.diginamic.digilearning.entities.Cours;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représentation d'un cours avec les flags
 * d'un utilisateur donné et le nom du sous module dans
 * lequel il se trouve.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CoursDto implements Comparable<CoursDto> {
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
    @Override
    public int compareTo(CoursDto o) {
        int comp = ordre.compareTo(o.getOrdre());
        if(comp == 0){
            return titre.compareTo(o.getTitre());
        }
        return comp;
    }
}
