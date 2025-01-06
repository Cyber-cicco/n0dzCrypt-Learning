package fr.diginamic.digilearning.DTO;

import fr.diginamic.digilearning.entities.enums.StatusChapitre;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChapitreDto {
    private String titre;
    private StatusChapitre statusChapitre;
}
