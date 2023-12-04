package fr.diginamic.digilearning.page.validators;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import org.springframework.stereotype.Service;

@Service
public class QuestionValidator {

    public void validateQuestion(String message) {
        if (message.length() > 255) {
            throw new BrokenRuleException("La taille maximum d'une question de 255 caractères");
        }
        if (message.isBlank()) {
            throw new BrokenRuleException("Les questions vides ne sont pas autorisées");
        }
    }
}
