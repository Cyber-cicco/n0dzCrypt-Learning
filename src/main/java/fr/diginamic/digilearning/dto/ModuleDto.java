package fr.diginamic.digilearning.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
    private Long id;
    private String libelle;
    private String photo;
    private Integer nbCoursTermines;
    private Integer nbCoursTotal;

    public Integer getPourcentageCompletion(){
        if(nbCoursTotal != 0) return (int) Math.ceil((Double.valueOf(nbCoursTermines) / Double.valueOf(nbCoursTotal)) * 100d);
        return 100;
    }
}
