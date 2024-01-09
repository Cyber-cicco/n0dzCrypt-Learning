package fr.diginamic.digilearning.dto;

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
