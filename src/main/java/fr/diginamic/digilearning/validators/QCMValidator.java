package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.QCMChoix;
import fr.diginamic.digilearning.entities.QCMQuestion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<String> validateQCM(Chapitre chapitre) {
        List<String> diagnostics = new ArrayList<>();
        for (QCMQuestion qcmQuestion : chapitre.getQcmQuestions()) {
            if(qcmQuestion.getChoix().size() < 2) {
                diagnostics.add("La question : " + qcmQuestion.getLibelle() + " doit avoir au moins deux choix");
            }
            qcmQuestion.getChoix().stream().filter(QCMChoix::getValid).findFirst().ifPresentOrElse((n) -> {}, () -> {
                diagnostics.add("La question : " + qcmQuestion.getLibelle() + " doit avoir au moins un choix valide");
            });
        }
        return diagnostics;
    }

    public Optional<String> validateQCMCommentaire(String comentaire) {
        if(comentaire.length() > 1024) {
            return Optional.of("La taille du commentaire ne peut excéder 255 caractères");
        }
        return Optional.empty();
    }
}
