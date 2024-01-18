package fr.diginamic.digilearning.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class QCMDto {

    private Long id;
    private String libelle;
    private List<QCMQuestionDto> questions;
}
