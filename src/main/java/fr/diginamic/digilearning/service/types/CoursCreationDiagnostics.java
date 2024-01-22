package fr.diginamic.digilearning.service.types;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoursCreationDiagnostics implements Diagnostics {
    private String titre;
    private String difficulte;
    private String ordre;
    private String duree;

    @Override
    public boolean isValid() {
        return titre == null && difficulte == null && ordre == null && duree == null;
    }
}
