package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import org.springframework.stereotype.Service;

@Service
public class ReponseValidator {
    public void validateReponse(String message) {
        if (message.length() > 1024) {
            throw new BrokenRuleException("La taille maximum d'une question de 255 caractères");
        }
        if (message.isBlank()) {
            throw new BrokenRuleException("Les questions vides ne sont pas autorisées");
        }
    }
}
