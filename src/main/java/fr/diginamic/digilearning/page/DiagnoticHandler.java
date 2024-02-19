package fr.diginamic.digilearning.page;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public interface DiagnoticHandler {

    default String handleSingleDiagnostic(Model model, HttpServletResponse response, String diagnostic, String target, String route){
        model.addAttribute("error", diagnostic);
        response.setHeader("HX-Retarget", target);
        return route;
    }
}
