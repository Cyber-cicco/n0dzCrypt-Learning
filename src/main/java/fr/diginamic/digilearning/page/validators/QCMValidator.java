package fr.diginamic.digilearning.page.validators;

import fr.diginamic.digilearning.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QCMValidator {

    public Optional<String> validateQCMQuestion(String question) {
        if(question == null){
            return Optional.of("La question ne peut pas être nulle");
        }
        if(question.isBlank()) {
            return Optional.of("La question ne peut pas être vide");
        }
        if(question.length() > 255) {
            return Optional.of("La taille de la question ne peut excéder 255 caractères");
        }
        return Optional.empty();
    }

    public Optional<String> validateQCMChoix(String libelle) {
        if(libelle == null){
            return Optional.of("Le choix ne peut pas être nul");
        }
        if(libelle.isBlank()) {
            return Optional.of("Le choix ne peut pas être vide");
        }
        if(libelle.length() > 255) {
            return Optional.of("La taille du choix ne peut excéder 255 caractères");
        }
        return Optional.empty();
    }
}
