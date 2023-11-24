package fr.diginamic.digilearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SousModuleInfosDto {
    private Long id;
    private String titre;
    private String photo;
    private String libelle;
}
