package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.ChapitreDto;
import fr.diginamic.digilearning.dto.ContenuChapitreDto;
import fr.diginamic.digilearning.dto.CreationCoursDto;
import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.page.irrigator.ChapitreIrrigator;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.service.PhotoService;
import fr.diginamic.digilearning.page.service.types.CoursCreationResult;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cours/admin")
public class CoursAdminController {

    private final CoursService coursService;
    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final ChapitreIrrigator chapitreIrrigator;
    private final PhotoService photoService;
    private final CoursIrrigator coursIrrigator;
    @GetMapping
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

    @GetMapping("/api")
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

    @GetMapping("/chapitre/editer/api")
    public String getAdminPanelChapitreApi(Model model, @RequestParam("id") Long idChapitre) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateAdminChapitre(model, userInfos, idChapitre);
        return Routes.ADR_ADMIN_CHAPITRE;
    }

    @GetMapping("/chapitre/editer")
    public String getAdminPanelChapitre(Model model, @RequestParam("id") Long idChapitre) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateAdminChapitre(model, userInfos, idChapitre);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_CHAPITRE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/editer/api")
    public String getAdminCoursEditerApi(@RequestParam("id") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        return Routes.ADR_COURS_ADMIN_EDITER;
    }

    @GetMapping("/editer")
    public String getAdminCoursEditer(@RequestParam("id") Long idCours, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_ADMIN_EDITER);
        return Routes.ADR_BASE_LAYOUT;
    }

    @PostMapping("/description")
    public String editerDescription(@RequestParam("id") Long idCours, @ModelAttribute MessageDto descriptionCours) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursService.editerDescription(idCours, descriptionCours, userInfos);
        return Routes.ADR_FORM_ERROR;
    }

    @PostMapping
    public String creerCours(Model model, @RequestParam("id") Long idSousModule, @ModelAttribute CreationCoursDto creationCoursDto, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(
                userInfos.getRoles(),
                List.of(TypeRole.ROLE_ADMINISTRATEUR, TypeRole.ROLE_FORMATEUR),
                response
        );
        CoursCreationResult resultat = coursService.creerCours(userInfos, idSousModule, creationCoursDto);
        if(resultat.diagnostics().isValid()){
            coursIrrigator.irrigateEditionCours(model, resultat.cours(), userInfos);
            response.setHeader("HX-Push-Url", "cours/admin/editer?id=" + resultat.cours().getId());
            return Routes.ADR_COURS_ADMIN_EDITER;
        }
        coursIrrigator.irragateFormCreationCoursError(model, resultat.diagnostics(), creationCoursDto, idSousModule);
        response.setHeader("HX-Retarget", "#modal-content");
        return Routes.ADR_MODAL_AJOUT_COURS;
    }

    @PostMapping("/chapitre")
    public String creerChapitre(Model model, @RequestParam("id") Long idCours, @ModelAttribute ChapitreDto chapitreDto, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        CoursService.TypesChapitres result = coursService.createNewChapitre(userInfos, idCours, chapitreDto);
        switch (chapitreDto.getStatusChapitre()) {
            case COURS -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, result.chapitre().getId());
                response.setHeader("HX-Push-Url", "/cours/admin/chapitre/editer?id=" + result.chapitre().getId());
                return Routes.ADR_ADMIN_CHAPITRE;
            }
            case QCM -> {
                chapitreIrrigator.irrigateAdminQCM(model, result.qcm());
                return Routes.ADR_ADMIN_QCM;
            }
            case EXERCICE -> {
                throw new RuntimeException();
            }
            default -> throw new BrokenRuleException();
        }
    }
    @PostMapping("/photo")
    public ResponseEntity<?> ajouterPhoto(Model model, @ModelAttribute("file") MultipartFile file, HttpServletResponse response) throws IOException {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), response);
        String fileName = photoService.uploadPhoto(file, userInfos);
        return ResponseEntity.ok(Map.of("name", fileName));
    }

    @PostMapping("/chapitre/contenu")
    public String editerChapitre(Model model, @RequestParam("id") Long idChapitre, @ModelAttribute ContenuChapitreDto contenuChapitreDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre chapitre = coursService.updateContenu(userInfos, idChapitre, contenuChapitreDto);
        model.addAttribute("content", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenuNonPublie()));
        String aJour = (chapitre.getAJour())
                ? "La version de votre cours est publiée"
                : "La version de votre cours est en avance par rapport à la version publiée";
        String classAJour = chapitre.getAJour()
                ? "text-validation"
                : "text-error";
        model.addAttribute("aJour", aJour);
        model.addAttribute("classAJour", classAJour);
        return Routes.ADR_COURS_CONTENT;
    }


    @PostMapping("/chapitre/publier")
    public String publierChapitre(Model model, @RequestParam("id") Long idChapitre, @ModelAttribute ContenuChapitreDto contenuChapitreDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre chapitre = coursService.publierContenu(userInfos, idChapitre, contenuChapitreDto);
        model.addAttribute("content", "La version de votre cours est publiée");
        return Routes.ADR_MESSAGE;
    }
    @DeleteMapping("/chapitre")
    public String deleteChapitre(Model model, @RequestParam("id") Long idChapitre, HttpServletResponse reponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Long idCours = coursService.supprimerChapitre(userInfos, idChapitre);
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        reponse.setHeader("HX-Push-Url", "/cours/admin/chapitre?id=" + idCours);
        return Routes.ADR_COURS_ADMIN_EDITER;
    }
}
