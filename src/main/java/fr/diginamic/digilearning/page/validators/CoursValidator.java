package fr.diginamic.digilearning.page.validators;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import org.springframework.stereotype.Service;

@Service
public class CoursValidator {

    public void validateTitreChapitre(String titreChapitre) {
        if(titreChapitre.length() > 255) {
            throw new BrokenRuleException("Le titre d'un chapitre ne peut excéder 255 caractères");
        }
    }
}
