package fr.diginamic.digilearning.page.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageValidator {

    public Optional<String> validateMessage(String message){
        if (message.length() > 255) {
            return Optional.of("La taille maximum d'un message est de 255 caractères");
        }
        if (message.isBlank()) {
           return Optional.of("Les messages vides ne sont pas autorisés");
        }
        return Optional.empty();
    }
}
