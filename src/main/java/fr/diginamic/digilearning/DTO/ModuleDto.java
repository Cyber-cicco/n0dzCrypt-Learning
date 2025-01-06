package fr.diginamic.digilearning.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Représentation d'un module avec en plus
 * le nombre de cours qu'il possède et le nombre de cours
 * que l'utilisateur connecté a terminés
 */
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

    /**
     * Divise le nombre de cours terminés dans un module par son nombre de cours total multiplie le résultat par cent
     * pour obtenir le pourcentage de progression
     * @return la progression. 100% par défaut s'il y a 0 cours.
     */
    public Integer getPourcentageCompletion(){
        if(nbCoursTotal != 0) return (int) Math.ceil((Double.valueOf(nbCoursTermines) / Double.valueOf(nbCoursTotal)) * 100d);
        return 100;
    }
}
