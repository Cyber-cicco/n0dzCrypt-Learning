package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
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
    private final CoursIrrigator coursIrrigator;
    private final LayoutIrrigator layoutIrrigator;

    @GetMapping("/api")
    public String getCoursApi( Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateBaseModel(userInfos, model);
        return Routes.ADR_COURS_MODULE;
    }
    @GetMapping
    public String getCours( Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateBaseModel(userInfos, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_MODULE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/admin")
    public String getCoursAdminPanel(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(
                userInfos.getRoles(),
                List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR),
                response
        );
        coursIrrigator.irrigateAdminPanel(userInfos, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_ADMIN);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/admin/api")
    public String getCoursAdminPanelApi(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(
                userInfos.getRoles(),
                List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR),
                response
        );
        coursIrrigator.irrigateAdminPanel(userInfos, model);
        return Routes.ADR_COURS_ADMIN;
    }
    @GetMapping("/module/api")
    public String getModuleApi( @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateModule(userInfos, idModule, model);
        return Routes.ADR_SOUS_MODULE;
    }
    @GetMapping("/module")
    public String getModule( @RequestParam("id") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateModule(userInfos, idModule, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_SOUS_MODULE);
        return Routes.ADR_BASE_LAYOUT;
    }


    @GetMapping("/liste/api")
    public String getListCoursApi( @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateListeCours(userInfos, idSModule, idModule, model);
        return Routes.ADR_LISTE_COURS;
    }


    @GetMapping("/liste")
    public String getListeCours( @RequestParam("id") Long idSModule, @RequestParam("module") Long idModule, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateListeCours(userInfos, idSModule, idModule, model);
        model.addAttribute("insert", "pages/cours/liste/cours.liste");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return Routes.ADR_BASE_LAYOUT;
    }


    @GetMapping("/sommaire/api")
    public String getSommaireApi( @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateSommaire(userInfos, id, model);
        return Routes.ADR_VISIONNEUSE_COURS;
    }


    @GetMapping("/sommaire")
    public String getSommaire( @RequestParam("id") Long id, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateSommaire(userInfos, id, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/chapitre/api")
    public String getChapitreApi(@RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateChapitre(userInfos, id, idCours, model);
        return Routes.ADR_VISIONNEUSE_COURS;
    }

    @GetMapping("/admin/editer/api")
    public String getAdminCoursEditerApi(@RequestParam("id") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        return Routes.ADR_COURS_ADMIN_EDITER;
    }

    @GetMapping("/admin/editer")
    public String getAdminCoursEditer(@RequestParam("id") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_ADMIN_EDITER);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/chapitre")
    public String getChapitre(@RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateChapitre(userInfos, id, idCours, model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @PatchMapping("/bookmark")
    public String patchBookmark(@RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateBookmarked(model, userInfos, idCours);
        return Routes.ADR_BOOKMARK_ICON;
    }

    @PatchMapping("/finished")
    public String patchFinished(@RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateFinished(model, userInfos, idCours);
        return Routes.ADR_FINISHED_ICON;
    }
}
