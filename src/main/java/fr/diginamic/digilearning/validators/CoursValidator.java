package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.dto.CreationCoursDto;
import fr.diginamic.digilearning.service.types.CoursCreationDiagnostics;
import fr.diginamic.digilearning.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursValidator {

    private final CoursRepository coursRepository;

    public Optional<String> validateTitreChapitre(String titreChapitre) {
        if(titreChapitre == null || titreChapitre.isBlank()){
            return Optional.of("Le titre du chapitre ne peut pas être vide");
        }
        if(titreChapitre.length() > 255) {
            return Optional.of("Le titre d'un chapitre ne peut excéder 255 caractères");
        }
        return Optional.empty();
    }

    public CoursCreationDiagnostics validateCoursCreation(CreationCoursDto cours, Long idSousModule) {
        CoursCreationDiagnostics diagnostics = new CoursCreationDiagnostics();
        if(cours.getTitre() == null || cours.getTitre().isBlank()) {
            diagnostics.setTitre("le titre ne peut pas être vide");
        }
        if(cours.getTitre() != null && cours.getTitre().length() > 255) {
            diagnostics.setTitre("La taille du titre ne peut excéder 255 caractères");
        }
        if(cours.getTitre() != null && coursRepository.existsByTitre(cours.getTitre())) {
            diagnostics.setTitre("Le titre du cours que vous avez donné existe déjà. Veuillez en choisir un autre");
        }
        if(cours.getDifficulte() == null){
            diagnostics.setDifficulte("La difficulté doit obligatoirement être renseignée");
        }
        if(cours.getDifficulte() != null && (cours.getDifficulte() > 5 || cours.getDifficulte() < 1)) {
            diagnostics.setDifficulte("La difficulté du cours doit être comprise entre 1 et 5");
        }
        Optional<Integer> maxOrdre = coursRepository.maxByOrdre(idSousModule);
        if (cours.getOrdre() == null){
            diagnostics.setOrdre("L'ordre doit obligatoirement être renseigné");
        }
        if(cours.getOrdre() != null && cours.getOrdre() < 1) {
            diagnostics.setOrdre("L'ordre d'un cours dans un sous module ne peut être inférieur à 1");
        }
        maxOrdre.ifPresentOrElse((max) -> {
           if(cours.getOrdre() != null && cours.getOrdre() > max + 1){
               diagnostics.setOrdre("L'ordre du cours ne peut être supérieur à l'ordre maximum plus 1 des cours du sous module");
           }
        }, () -> {
            if(cours.getOrdre() != null && cours.getOrdre() != 1){
                diagnostics.setOrdre("l'ordre du cours ne peut être supérieur à l'ordre maximum plus 1 des cours du sous module");
            }
        });
        if(cours.getDuree() == null){
            diagnostics.setDuree("La durée estimée doit obligatoirement être renseignée");
        } else {
            if(cours.getDuree() < 1) {
                diagnostics.setOrdre("La durée estimée ne peut être inférieure à une heure");
            }
            if(cours.getDuree() > 6) {
                diagnostics.setOrdre("La durée maximum d'un cours est de 6 heure. Au delà de cela, Découpez votre cours en plusieurs morceaux");
            }

        }
        return diagnostics;
    }
}
