package fr.diginamic.digilearning.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CoursDto {
    private Long id;
    private String titre;
    private Integer difficulte;
    private Integer ordre;
    private Boolean boomarked;
    private Boolean finished;
    private Boolean liked;
}
