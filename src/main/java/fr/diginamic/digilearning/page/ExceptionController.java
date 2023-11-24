package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BrokenRuleException.class)
    public String sendUnAuthorizedException(Model model, HttpServletResponse response, BrokenRuleException e) {
        model.addAttribute("error", e.getMessage());
        response.setHeader("HX-Retarget", "#error");
        return "reponses/form.error";
    }
}
