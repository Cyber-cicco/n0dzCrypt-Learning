package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.SousModule;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("cours")
@RequiredArgsConstructor
public class CoursController {

    private final AuthenticationService authenticationService;
    private final ModuleRepository moduleRepository;
    private final CoursService coursService;
    private final SousModuleRepository sousModuleRepository;

    @GetMapping("/api")
    public String getCoursApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, response);
        return "pages/cours";
    }
    @GetMapping
    public String getCours(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, response);
        model.addAttribute("insert", "pages/cours");
        return "base";
    }

    private void irrigateBaseModel(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        model.addAttribute("subinsert", "pages/fragments/cours/cours.main.html");
        model.addAttribute("modules", moduleRepository.findModulesByUtilisateur(userInfos.getEmail()));
    }

    @GetMapping("/module/api")
    public String getModuleApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateModule(userInfos, idModule, model, response);
        return "pages/module";
    }
    @GetMapping("/module")
    public String getModule(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateModule(userInfos, idModule, model, response);
        model.addAttribute("insert", "pages/module");
        return "base";
    }

    private void irrigateModule(AuthenticationInfos userInfos, Long idModule, Model model, HttpServletResponse response){
        List<SousModule> sousModuleInfosDtos = coursService.findModulesByUtilisateur(userInfos.getEmail(), idModule);
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("titre", module.getLibelle());
        model.addAttribute("smodules", sousModuleInfosDtos);
    }

    @GetMapping("/liste/api")
    public String getListCoursApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, model, response);
        return "pages/liste-cours";
    }


    @GetMapping("/liste")
    public String getListeCours(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, model, response);
        model.addAttribute("insert", "pages/liste-cours");
        return "base";
    }

    private void irrigateListeCours(AuthenticationInfos userInfos, Long idSModule, Model model, HttpServletResponse response) {
     //   model.addAttribute("cours", coursService.getCours(userInfos, idSModule));
    }

}
