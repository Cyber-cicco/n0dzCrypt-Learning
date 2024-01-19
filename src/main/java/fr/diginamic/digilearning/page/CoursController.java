package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.dto.ReponseQCMDto;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.QCMPasse;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.repository.QCMPasseRepository;
import fr.diginamic.digilearning.service.ChapitreService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.QCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("cours")
@RequiredArgsConstructor
public class CoursController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final CoursIrrigator coursIrrigator;
    private final ChapitreService chapitreService;
    private final LayoutIrrigator layoutIrrigator;
    private final QCMService qcmService;
    private final QCMPasseRepository qcmPasseRepository;

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
    public String getChapitreApi(@RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var chapitreInfos = chapitreService.getChapitreInfos(userInfos, id, idCours);
        switch (chapitreInfos.chapitre().getStatusChapitre()){
            case COURS -> {
                coursIrrigator.irrigateChapitre(userInfos, chapitreInfos.chapitre(), chapitreInfos.cours(), chapitreInfos.flagCours(), model);
                return Routes.ADR_VISIONNEUSE_COURS;
            }
            case QCM -> {
                handleQCM(model, userInfos, chapitreInfos.chapitre(), chapitreInfos.cours(), chapitreInfos.flagCours());
                return Routes.ADR_VISIONNEUSE_COURS;
            }
            case EXERCICE -> {
                throw new RuntimeException("Partie non implémentée");
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }

    private void handleQCM(Model model, AuthenticationInfos userInfos, Chapitre qcm, Cours cours, FlagCours flagCours){
        Optional<QCMPasse> qcmPasseOpt = qcmPasseRepository.findByUtilisateurAndQCM(userInfos.getId(), qcm.getId());
        if(qcmPasseOpt.isPresent()){
            QCMPasse qcmPasse = qcmPasseOpt.get();
            model.addAttribute("qcm", qcmPasse);
            if(qcmPasse.isQCMFinished()) {
                model.addAttribute("slide", Routes.ADR_QCM_REFAIRE);
                return;
            }
            model.addAttribute("slide", Routes.ADR_QCM_EN_COURS);
            return;
        }
        coursIrrigator.irrigateQCM(model, userInfos, qcm, cours, 0);
    }

    @GetMapping("/chapitre")
    public String getChapitre(@RequestParam("id") Integer id, @RequestParam("cours") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var chapitreInfos = chapitreService.getChapitreInfos(userInfos, id, idCours);
        coursIrrigator.irrigateChapitre(userInfos, id, idCours, model);
        switch (chapitreInfos.chapitre().getStatusChapitre()){
            case COURS -> coursIrrigator.irrigateChapitre(userInfos, chapitreInfos.chapitre(), chapitreInfos.cours(), chapitreInfos.flagCours(), model);
            case QCM -> coursIrrigator.irrigateQCM(model, userInfos, chapitreInfos.chapitre(), chapitreInfos.cours(), 0);
            case EXERCICE -> throw new RuntimeException("Partie non implémentée");
            default ->  throw new RuntimeException();
        }
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @PostMapping("/qcm/response")
    public String postNewResponseToQCM(Model model, @RequestParam("id") Long idQCM, @RequestParam("idQuestion") Long idQuestion, @RequestBody List<ReponseQCMDto> reponseQCM){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var nextPage = qcmService.postNewResponse(userInfos.getId(), idQCM, idQuestion, reponseQCM);
        if(nextPage.index().isPresent()){
            coursIrrigator.irrigateQCM(model, userInfos, nextPage.qcm(), nextPage.qcm().getCours(), nextPage.index().get());
            return Routes.ADR_QCM;
        }
        coursIrrigator.irrigateQCMFinished(model, userInfos, nextPage.qcm(), nextPage.qcmPasse());
        return Routes.ADR_QCM_TERMINE;
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
