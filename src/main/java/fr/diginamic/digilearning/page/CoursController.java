package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.FlagCoursRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
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
    private final FlagCoursRepository flagCoursRepository;

    @GetMapping("/api")
    public String getCoursApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model);
        return "pages/cours";
    }
    @GetMapping
    public String getCours(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model);
        model.addAttribute("insert", "pages/cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateBaseModel(AuthenticationInfos userInfos, Model model){
        model.addAttribute("subinsert", "pages/fragments/cours/cours.main.html");
        model.addAttribute("modules", coursService.findModulesByUtilisateur(userInfos.getId()));
    }

    @GetMapping("/module/api")
    public String getModuleApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateModule(userInfos, idModule, model);
        return "pages/module";
    }
    @GetMapping("/module")
    public String getModule(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateModule(userInfos, idModule, model);
        model.addAttribute("insert", "pages/module");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateModule(AuthenticationInfos userInfos, Long idModule, Model model){
        List<SousModule> sousModuleInfosDtos = coursService.findSModulesByUtilisateur(userInfos.getEmail(), idModule);
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("titre", module.getLibelle());
        model.addAttribute("id", module.getId());
        model.addAttribute("smodules", sousModuleInfosDtos);
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
    }

    @GetMapping("/liste/api")
    public String getListCoursApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, idModule, model);
        return "pages/liste-cours";
    }


    @GetMapping("/liste")
    public String getListeCours(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateListeCours(userInfos, idSModule, idModule, model);
        model.addAttribute("insert", "pages/liste-cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateListeCours(AuthenticationInfos userInfos, Long idSModule, Long idModule, Model model) {
        List<CoursDto> coursDtos = coursService.getCours(userInfos, idSModule);
        model.addAttribute("cours", coursDtos);
        SousModule sousModule = sousModuleRepository.findById(idSModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("smodule", sousModule);
        model.addAttribute("idModuleOrigine", idModule);
        model.addAttribute("bookmarked", coursDtos.stream().filter(CoursDto::getBoomarked).toList());
    }

    @GetMapping("/sommaire/api")
    public String getSommaireApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSommaire(userInfos, id, model);
        return "pages/visualiser.cours";
    }


    @GetMapping("/sommaire")
    public String getSommaire(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSommaire(userInfos, id, model);
        model.addAttribute("insert", "pages/visualiser.cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateSommaire(AuthenticationInfos userInfos, Long idCours, Model model) {
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        model.addAttribute("cours", cours);
        model.addAttribute("flags", flagCours);
        model.addAttribute("slide", "pages/sommaire.cours.main");
    }

    @GetMapping("/chapitre/api")
    public String getChapitreApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateChapitre(userInfos, id, idCours, model);
        return "pages/visualiser.cours";
    }

    @GetMapping("/chapitre")
    public String getChapitre(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateChapitre(userInfos, id, idCours, model);
        model.addAttribute("insert", "pages/visualiser.cours");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "base";
    }

    private void irrigateChapitre(AuthenticationInfos userInfos, Integer idChapitre, Long idCours, Model model) {
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        Chapitre chapitre = coursService.getChapitreIfExistsElseThrow(cours, idChapitre);
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("contenu", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenu()));
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestionsNonSuppr());
        model.addAttribute("cours", cours);
        model.addAttribute("flags", flagCours);
        model.addAttribute("idCours", idCours);
        model.addAttribute("slide", "pages/chapitre.main.cours");
    }

    @GetMapping("/bookmarked")
    public String getBookMarks(@CookieValue("AUTH-TOKEN") String token, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
        model.addAttribute("schedueled", coursService.getCoursCeJour(userInfos.getId()));
        return "pages/fragments/cours/modal.cours.bookmarked";
    }
    @PatchMapping("/bookmark")
    public String patchBookmark(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model) {
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
    public String patchFinished(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model) {
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
