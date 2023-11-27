package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.SousModule;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("cours")
@RequiredArgsConstructor
public class CoursController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final ModuleRepository moduleRepository;
    private final CoursService coursService;
    private final CoursRepository coursRepository;
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
        model.addAttribute("links", navBarService.getLinks(userInfos));
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
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateModule(AuthenticationInfos userInfos, Long idModule, Model model, HttpServletResponse response){
        List<SousModule> sousModuleInfosDtos = coursService.findModulesByUtilisateur(userInfos.getEmail(), idModule);
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("titre", module.getLibelle());
        model.addAttribute("id", module.getId());
        model.addAttribute("smodules", sousModuleInfosDtos);
    }

    @GetMapping("/liste/api")
    public String getListCoursApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, idModule, model, response);
        return "pages/liste-cours";
    }


    @GetMapping("/liste")
    public String getListeCours(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, idModule, model, response);
        model.addAttribute("insert", "pages/liste-cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateListeCours(AuthenticationInfos userInfos, Long idSModule, Long idModule, Model model, HttpServletResponse response) {
        model.addAttribute("cours", coursService.getCours(userInfos, idSModule));
        SousModule sousModule = sousModuleRepository.findById(idSModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("smodule", sousModule);
        model.addAttribute("idModuleOrigine", idModule);
    }

    @GetMapping("/sommaire/api")
    public String getSommaireApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSommaire(userInfos, id, idSModule, idModule, model, response);
        return "pages/sommaire.cours";
    }


    @GetMapping("/sommaire")
    public String getSommaire(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSommaire(userInfos, id, idSModule, idModule, model, response);
        model.addAttribute("insert", "pages/sommaire.cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateSommaire(AuthenticationInfos userInfos, Long idCours, Long idSModule, Long idModule, Model model, HttpServletResponse response) {
        model.addAttribute("cours", coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("idSModule", idSModule);
        model.addAttribute("idModuleOrigine", idModule);
    }

    @GetMapping("/chapitre/api")
    public String getChapitreApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, @RequestParam("id") Long idSModule, @RequestParam("cours") Long idCours, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateChapitre(userInfos, id, idCours, idSModule, idModule, model, response);
        return "pages/chapitre.cours";
    }

    @GetMapping("/chapitre")
    public String getChapitre(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, @RequestParam("id") Long idSModule, @RequestParam("cours") Long idCours, @RequestParam("module") Long idModule, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateChapitre(userInfos, id, idCours, idSModule, idModule, model, response);
        model.addAttribute("insert", "pages/chapitre.cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateChapitre(AuthenticationInfos userInfos, Long idChapitre, Long idCours, Long idSModule, Long idModule, Model model, HttpServletResponse response) {
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        Chapitre chapitre = cours.getChapitres().stream().peek(System.out::println).filter(chapitre1 -> chapitre1.getId().equals(idChapitre)).findFirst().orElseThrow(EntityNotFoundException::new);
        model.addAttribute("contenu", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenu()));
        model.addAttribute("idCours", idCours);
        model.addAttribute("idSModule", idSModule);
        model.addAttribute("idModuleOrigine", idModule);
    }
    @PatchMapping("/bookmark")
    public String patchBookmark(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        boolean bookmarked = coursService.patchBookmark(userInfos, idCours);
        model.addAttribute("style", (bookmarked)
                ? "filter: invert(73%) sepia(83%) saturate(1960%) hue-rotate(3deg) brightness(99%) contrast(108%);"
                : ""
        );
        model.addAttribute("id", idCours);
        return "pages/fragments/cours/cours.bookmark";
    }

    @PatchMapping("/finished")
    public String patchFinished(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        boolean finished = coursService.patchFinished(userInfos, idCours);
        model.addAttribute("style", (finished)
                ? "filter: invert(42%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);"
                : ""
        );
        model.addAttribute("id", idCours);
        return "pages/fragments/cours/cours.finished";
    }
}
