package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BrokenRuleException.class)
    public String sendBrokenRuleException(HttpServletRequest request, Model model, HttpServletResponse response, BrokenRuleException e) {
        model.addAttribute("error", e.getMessage());
        response.setHeader("HX-Retarget", "#error");
        return "components/reponses/form.error";
    }
    @ExceptionHandler(UnauthorizedException.class)
    public String sendUnauthorizedException(HttpServletRequest request, Model model, HttpServletResponse response, UnauthorizedException e){
        model.addAttribute("error", e.getMessage());
        response.setHeader("HX-Retarget", "#error");
        return "components/reponses/form.error";
    }
}
