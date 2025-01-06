package fr.diginamic.digilearning.validators;

import fr.diginamic.digilearning.DTO.MessageDto;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostForumValidator {

    public static String[] BAN_WORDS = {
            "chocolatine",
    };
    public void validatePostForum(MessageDto post) {
        if(post.getMessage() == null || post.getMessage().isBlank()) {
            throw new BrokenRuleException("Le message ne peut pas être vide");
        }
        if(post.getMessage().length() > 2048) {
            throw new BrokenRuleException("La taille maximale d'un post est de 2048 caractères");
        }
    }
}
