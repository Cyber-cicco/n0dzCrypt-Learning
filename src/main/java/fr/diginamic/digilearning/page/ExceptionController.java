package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.utils.hx.HX;
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
        response.setHeader(HX.RETARGET, "#error");
        return Routes.ADR_FORM_ERROR;
    }
    @ExceptionHandler(UnauthorizedException.class)
    public String sendUnauthorizedException(HttpServletRequest request, Model model, HttpServletResponse response, UnauthorizedException e){
        model.addAttribute("error", e.getMessage());
        response.setHeader(HX.RETARGET, "#error");
        return Routes.ADR_FORM_ERROR;
    }
}
