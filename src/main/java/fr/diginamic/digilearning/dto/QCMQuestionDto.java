package fr.diginamic.digilearning.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class QCMQuestionDto {

    private Long id;
    private String libelle;
    private String illustration;
    private String commentaire;
    private Integer ordre;
    private List<QCMChoixDto> choix;
}
