package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BrokenRuleException.class)
    public String sendUnAuthorizedException(Model model, BrokenRuleException e) {
        model.addAttribute("error", e.getMessage());
        return "reponses/form.error";
    }
}
