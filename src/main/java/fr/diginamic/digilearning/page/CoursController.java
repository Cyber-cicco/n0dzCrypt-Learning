package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cours")
@RequiredArgsConstructor
public class CoursController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final CoursIrrigator coursIrrigator;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping("/api")
    public String getCoursApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateBaseModel(userInfos, model);
        return Routes.ADR_COURS_MODULE;
    }
    @GetMapping
    public String getCours(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateBaseModel(userInfos, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_MODULE);
        return Routes.ADR_BASE_LAYOUT;
    }


    @GetMapping("/module/api")
    public String getModuleApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateModule(userInfos, idModule, model);
        return Routes.ADR_SOUS_MODULE;
    }
    @GetMapping("/module")
    public String getModule(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateModule(userInfos, idModule, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_SOUS_MODULE);
        return Routes.ADR_BASE_LAYOUT;
    }


    @GetMapping("/liste/api")
    public String getListCoursApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateListeCours(userInfos, idSModule, idModule, model);
        return Routes.ADR_LISTE_COURS;
    }


    @GetMapping("/liste")
    public String getListeCours(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateListeCours(userInfos, idSModule, idModule, model);
        model.addAttribute("insert", "pages/cours/liste/cours.liste");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return Routes.ADR_BASE_LAYOUT;
    }


    @GetMapping("/sommaire/api")
    public String getSommaireApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateSommaire(userInfos, id, model);
        return Routes.ADR_VISIONNEUSE_COURS;
    }


    @GetMapping("/sommaire")
    public String getSommaire(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateSommaire(userInfos, id, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/chapitre/api")
    public String getChapitreApi(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateChapitre(userInfos, id, idCours, model);
        return Routes.ADR_VISIONNEUSE_COURS;
    }

    @GetMapping("/chapitre")
    public String getChapitre(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateChapitre(userInfos, id, idCours, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @PatchMapping("/bookmark")
    public String patchBookmark(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateBookmarked(model, userInfos, idCours);
        return Routes.ADR_BOOKMARK_ICON;
    }

    @PatchMapping("/finished")
    public String patchFinished(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        coursIrrigator.irrigateFinished(model, userInfos, idCours);
        return Routes.ADR_FINISHED_ICON;
    }
}
