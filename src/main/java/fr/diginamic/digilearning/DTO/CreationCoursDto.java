package fr.diginamic.digilearning.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreationCoursDto {
    String titre;
    Integer ordre;
    Integer difficulte;
    Integer duree;
}
