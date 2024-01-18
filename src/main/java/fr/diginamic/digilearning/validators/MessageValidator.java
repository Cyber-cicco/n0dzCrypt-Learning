package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageValidator {

    public void validateMessage(String message){
        if (message.length() > 255) {
            throw new BrokenRuleException("La taille maximum d'un message est de 255 caractères");
        }
        if (message.isBlank()) {
            throw new BrokenRuleException("Les messages vides ne sont pas autorisés");
        }
    }
}
