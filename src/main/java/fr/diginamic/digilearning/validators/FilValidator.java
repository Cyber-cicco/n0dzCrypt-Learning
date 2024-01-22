package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.dto.PostFilDto;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import org.springframework.stereotype.Service;

@Service
public class FilValidator {

    public void validateFil(PostFilDto postFilDto) {
        if(postFilDto.getTitre().isBlank()){
            throw new BrokenRuleException("Le titre ne peut pas être vide");
        }
        if(postFilDto.getDescription().isBlank()){
            throw new BrokenRuleException("La description ne peut pas être vide");
        }
        if(postFilDto.getTitre().length() > 255){
            throw new BrokenRuleException("La longueur du titre d'un fil ne peut excéder les 255 caractères");
        }
        if(postFilDto.getDescription().length() > 2048){
            throw new BrokenRuleException("La longueur de la description ne peut excéder les 2048 caractères");
        }
    }
}
