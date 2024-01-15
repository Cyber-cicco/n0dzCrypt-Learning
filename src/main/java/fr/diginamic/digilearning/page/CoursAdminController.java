package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.*;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.QCMQuestion;
import fr.diginamic.digilearning.entities.enums.StatusPublication;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.page.irrigator.ChapitreIrrigator;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.service.PhotoService;
import fr.diginamic.digilearning.page.service.types.CoursCreationResult;
import fr.diginamic.digilearning.repository.ChapitreRepository;
import fr.diginamic.digilearning.repository.QCMQuestionRepository;
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
    private final ChapitreRepository chapitreRepository;
    private final QCMQuestionRepository qcmQuestionRepository;
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
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        switch (chapitre.getStatusChapitre()) {
            case COURS -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre);
                return Routes.ADR_ADMIN_CHAPITRE;
            }
            //TODO : changer pour mettre un handler quand la vue des tps sera prête
            case EXERCICE -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre);
                return Routes.ADR_ADMIN_CHAPITRE;
            }
            case QCM -> {
                chapitreIrrigator.irrigateAdminQCM(model, chapitre);
                return Routes.ADR_ADMIN_QCM;
            }
            default -> throw new RuntimeException("Le status du chapitre est nul ou n'est pas pris en charge");
        }
    }

    @GetMapping("/chapitre/editer")
    public String getAdminPanelChapitre(Model model, @RequestParam("id") Long idChapitre) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        String route;
        switch (chapitre.getStatusChapitre()) {
            case COURS -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre);
                route = Routes.ADR_ADMIN_CHAPITRE;
            }
            //TODO : changer pour mettre un handler quand la vue des tps sera prête
            case EXERCICE -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre);
                route = Routes.ADR_ADMIN_CHAPITRE;
            }
            case QCM -> {
                chapitreIrrigator.irrigateAdminQCM(model, chapitre);
                route = Routes.ADR_ADMIN_QCM;
            }
            default -> throw new RuntimeException("Le status du chapitre est nul ou n'est pas pris en charge");
        }
        layoutIrrigator.irrigateBaseLayout(model, userInfos, route);
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

    @GetMapping("/qcm/question")
    public String getAdminQCMQuestion(@RequestParam("id") Long idQuestion, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(
                userInfos.getRoles(),
                List.of(TypeRole.ROLE_ADMINISTRATEUR, TypeRole.ROLE_FORMATEUR),
                response
        );
        model.addAttribute("question", coursService.getQuestion(idQuestion, userInfos));
        return Routes.ADR_QCM_EDITION_QUESTION;
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
        Chapitre chapitre = coursService.createNewChapitre(userInfos, idCours, chapitreDto);
        switch (chapitreDto.getStatusChapitre()) {
            case COURS -> {
                chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre);
                response.setHeader("HX-Push-Url", "/cours/admin/chapitre/editer?id=" + chapitre.getId());
                return Routes.ADR_ADMIN_CHAPITRE;
            }
            case QCM -> {
                chapitreIrrigator.irrigateAdminQCM(model, chapitre);
                response.setHeader("HX-Push-Url", "/cours/admin/chapitre/editer?id=" + chapitre.getId());
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
        chapitreIrrigator.irrigateAjour(model, chapitre);
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

    @PostMapping("/qcm/choix")
    public String creerChoix(Model model, @RequestParam("id") Long idQuestion, @ModelAttribute ChoixDto choixDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        CoursService.ReponseCreationChoix qcmChoix = coursService.creerChoix(idQuestion, choixDto);
        if(qcmChoix.diagnostic().isPresent()){
            reponse.setHeader("HX-Retarget", "#error-choix");
            reponse.setHeader("HX-Reswap", "outerHTML");
            model.addAttribute("error", qcmChoix.diagnostic().get());
            return Routes.ADR_FORM_ERROR;
        }
        model.addAttribute("question", qcmChoix.question());
        return Routes.ADR_QCM_CHOIX_LISTE;
    }

    @PostMapping("/qcm/new")
    public String creerNouvelleQuestion(Model model, @RequestParam("id") Long idQCM, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre qcm = chapitreRepository.findById(idQCM).orElseThrow(EntityNotFoundException::new);
        QCMQuestion question = coursService.creerQuestion(qcm);
        qcm.getRawQCMQuestions().add(question);
        model.addAttribute("question", question);
        model.addAttribute("qcm", qcm);
        return Routes.ADR_ADMIN_QCM;
    }

    @PostMapping("/qcm/publier")
    public String changerTitreQuestion(Model model, @RequestParam("id") Long idQCM, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        CoursService.ReponsePublicationQCM reponsePublicationQCM = coursService.publierQCM(idQCM, reponse);
        if (reponsePublicationQCM.diagnostics().isEmpty()){
            chapitreIrrigator.irrigateAjour(model, reponsePublicationQCM.chapitre());
            model.addAttribute("id", "qcm-a-jour");
            return Routes.ADR_GENERIC_MESSAGE;
        }
        reponse.setHeader("HX-Retarget", "#error");
        reponse.setHeader("HX-Reswap", "outerHTML");
        model.addAttribute("aJour", reponsePublicationQCM.getMessage());
        model.addAttribute("classAJour", "text-error");
        model.addAttribute("id", "qcm-error-publication");
        return Routes.ADR_GENERIC_MESSAGE;
    }

    @PatchMapping("/qcm/question")
    public String changerTitreQuestion(Model model, @RequestParam("id") Long idQuestion, @ModelAttribute MessageDto messageDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        CoursService.ReponseChangementQuestion qcmQuestion = coursService.changeQCMQuestion(idQuestion, messageDto);
        if(qcmQuestion.diagnostic().isPresent()){
            reponse.setHeader("HX-Retarget", "#error");
            reponse.setHeader("HX-Reswap", "outerHTML");
            model.addAttribute("error", qcmQuestion.diagnostic().get());
            return Routes.ADR_FORM_ERROR;
        }
        model.addAttribute("question", qcmQuestion.question());
        return Routes.ADR_QCM_QUESTION_LISTE;

    }

    @PatchMapping("/qcm/question/ordre")
    public String changerOrdreQuestion(Model model, @RequestParam("id") Long idQuestion, @RequestParam("ordre") int ordre, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre qcm = coursService.changeQCMQuestionOrdre(idQuestion, ordre);
        model.addAttribute("qcm", qcm);
        return Routes.ADR_ADMIN_QCM_QUESTION_LISTE;
    }

    @PatchMapping("/qcm/choix/valid")
    public String changerStatusValidationChoix(Model model, @RequestParam("id") Long idChoix, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        boolean valid = coursService.changeStatusValidationChoix(idChoix);
        model.addAttribute("id", idChoix);
        model.addAttribute("style" , valid ? "filter: invert(42%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);" : "");
        return Routes.ADR_COMPOSANT_VALIDATION;
    }


    /**
     * Deletes a chapter from the course.
     *
     * @param  model        the model object to hold data
     * @param  idChapitre   the ID of the chapter to delete
     * @param  reponse      the HTTP servlet response
     * @return              the URL to redirect to after the deletion
     */
    @DeleteMapping("/chapitre")
    public String deleteChapitre(Model model, @RequestParam("id") Long idChapitre, HttpServletResponse reponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Long idCours = coursService.supprimerChapitre(userInfos, idChapitre);
        coursIrrigator.irrigateEditionCours(model, idCours, userInfos);
        reponse.setHeader("HX-Push-Url", "/cours/admin/chapitre?id=" + idCours);
        return Routes.ADR_COURS_ADMIN_EDITER;
    }

    @DeleteMapping("/qcm/choix")
    public String deleteChoix(Model model, @RequestParam("id") Long idChoix, HttpServletResponse reponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        QCMQuestion question = coursService.supprimerChoix(idChoix);
        model.addAttribute("question", question);
        return Routes.ADR_QCM_CHOIX_LISTE;
    }

    @DeleteMapping("/qcm/question")
    public String deleteQuestion(Model model, @RequestParam("id") Long idQuestion, HttpServletResponse reponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre chapitre = coursService.supprimerQuestion(userInfos, idQuestion);
        chapitreIrrigator.irrigateAdminQCM(model, chapitre);
        return Routes.ADR_ADMIN_QCM;
    }

}
