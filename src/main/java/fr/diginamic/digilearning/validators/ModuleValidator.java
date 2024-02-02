package fr.diginamic.digilearning.validators;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleValidator {

    public Optional<String> validateModuleTitre(String titre){
        if(titre == null || titre.isBlank()){
            return Optional.of("Le titre ne peut pas être vide");
        }
        if(titre.length() > 255){
            return Optional.of("La taille du titre ne peut pas être supérieure à 255 caractères");
        }
        return Optional.empty();
    }
}
