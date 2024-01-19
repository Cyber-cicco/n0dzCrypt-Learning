package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.dto.CreationCoursDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.service.CoursService;
import fr.diginamic.digilearning.service.QCMService;
import fr.diginamic.digilearning.service.types.CoursCreationDiagnostics;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

/**
 * Irrigateur du modèle donnée par le controlleur
 * HyperMédia du cours
 */
@Service
@RequiredArgsConstructor
public class CoursIrrigator {
    private final ModuleRepository moduleRepository;
    private final CoursService coursService;
    private final QCMService qcmService;
    private final CoursRepository coursRepository;
    private final SousModuleRepository sousModuleRepository;

    /**
     * Permet d'irriguer la page des modules
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     */
    public void irrigateBaseModel(AuthenticationInfos userInfos, Model model){
        model.addAttribute("subinsert", Routes.ADR_COURS_MODULE_BODY);
        model.addAttribute("modules", coursService.findModulesByUtilisateur(userInfos.getId()));
    }

    /**
     * Permet d'irriguer la page des sous-modules d'un module donné
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idModule l'identifiant du module dont sont tirés les sous modules.
     */
    public void irrigateModule(AuthenticationInfos userInfos, Long idModule, Model model){
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("titre", module.getLibelle());
        model.addAttribute("id", module.getId());
        model.addAttribute("smodules", coursService.findSModulesByUtilisateur(userInfos.getId(), idModule));
        model.addAttribute("bookmarked", coursRepository.getBookMarked(userInfos.getId()));
    }

    /**
     * Permet d'irriguer la vue avec la liste de cours.
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idSModule l'identifiant du sous module.
     * @param idModule l'identifiant du module dont il est tiré.
     */
    public void irrigateListeCours(AuthenticationInfos userInfos, Long idSModule, Long idModule, Model model) {
        List<CoursDto> coursDtos = coursService.getCours(userInfos, idSModule);
        model.addAttribute("cours", coursDtos);
        SousModule sousModule = sousModuleRepository.findById(idSModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("smodule", sousModule);
        model.addAttribute("idModuleOrigine", idModule);
    }

    /**
     * Permet d'irriguer la page de sommaire
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idCours l'identifiant du cours dont on veut le sommaire.
     */
    public void irrigateSommaire(AuthenticationInfos userInfos, Long idCours, Model model) {
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        model.addAttribute("cours", cours);
        model.addAttribute("flags", flagCours);
        model.addAttribute("slide", Routes.ADR_COURS_SOMMAIRE);
    }

    /**
     * Permet d'irriguer le modèle de la page de chapitre
     * Ajoute une entité chapitre
     * Ajoute l'id de l'utilisateur
     * Ajoute le contenu au format HTML
     * Ajoute une liste des questions
     * Ajoute une entité cours
     * Ajoute les flags du cours
     * Ajoute l'adresse d'un autre template correpondant au chapitre du cours
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idChapitre l'identifiant du chapitre
     * @param idCours l'identifiant du cours
     */
    public void irrigateChapitre(AuthenticationInfos userInfos, Integer idChapitre, Long idCours, Model model) {
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        Chapitre chapitre = coursService.getChapitreIfExistsElseThrow(cours, idChapitre);
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        irrigateChapitre(userInfos, chapitre, cours, flagCours, model);
    }
    public void irrigateChapitre(AuthenticationInfos userInfos, Chapitre chapitre , Cours cours, FlagCours flagCours, Model model) {
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("contenu", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenu()));
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestionsNonSuppr());
        model.addAttribute("cours", cours);
        model.addAttribute("flags", flagCours);
        model.addAttribute("idCours", cours.getId());
        model.addAttribute("slide", Routes.ADR_CHAPITRE);
    }

    /**
     * Irrigue un model contenant juste une checkmark pour dire si un cours est fini
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idCours l'identifiant du cours
     */
    public void irrigateFinished(Model model, AuthenticationInfos userInfos, Long idCours) {
        boolean finished = coursService.patchFinished(userInfos, idCours);
        model.addAttribute("style", (finished)
                ? "filter: invert(42%) sepia(93%) saturate(1352%) hue-rotate(87deg) brightness(119%) contrast(119%);"
                : ""
        );
        model.addAttribute("id", idCours);
    }

    /**
     * Irrigue un model contenant juste une icone pour signifier l'ajout dans un favori
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idCours l'identifiant du cours
     */
    public void irrigateBookmarked(Model model, AuthenticationInfos userInfos, Long idCours) {
        boolean bookmarked = coursService.patchBookmark(userInfos, idCours);
        model.addAttribute("style", (bookmarked)
                ? "filter: invert(73%) sepia(83%) saturate(1960%) hue-rotate(3deg) brightness(99%) contrast(108%);"
                : ""
        );
        model.addAttribute("id", idCours);
    }

    /**
     * Irrigue le panel d'administration
     *
     * @param  userInfos  les informations d'authentification de l'utilisateur
     * @param  model      un objet permettant d'irriguer le template thymeleaf
     */
    public void irrigateAdminPanel(AuthenticationInfos userInfos, Model model) {
        List<SousModule> sousModules = sousModuleRepository.findAll();
        List<Long> idCoursCrees = coursRepository.getCoursCrees(userInfos.getId()).stream().map(Cours::getId).toList();
        model.addAttribute("user", userInfos);
        model.addAttribute("smodules", sousModules);
        model.addAttribute("coursCrees", idCoursCrees);
    }

    public void irrigateEditionCours(Model model, Long idCours, AuthenticationInfos userInfos) {
        model.addAttribute("cours", coursRepository.getCoursByIdAndFormateur(idCours, userInfos.getId())
            .orElseThrow(EntityNotFoundException::new));
    }
    public void irrigateEditionCours(Model model, Cours cours, AuthenticationInfos userInfos) {
        model.addAttribute("cours", cours);
    }

    public void irragateFormCreationCoursError(Model model, CoursCreationDiagnostics diagnostics, CreationCoursDto cours, Long idSousModule) {
        model.addAttribute("id", idSousModule);
        model.addAttribute("titreError", diagnostics.getTitre());
        model.addAttribute("difficulteError", diagnostics.getDifficulte());
        model.addAttribute("ordreError", diagnostics.getOrdre());
        model.addAttribute("dureeError", diagnostics.getDuree());
        model.addAttribute("titre", cours.getTitre());
        model.addAttribute("difficulte", cours.getDifficulte());
        model.addAttribute("ordre", cours.getOrdre());
        model.addAttribute("duree", cours.getDuree());
    }

    public void irrigateQCM(Model model, AuthenticationInfos userInfos, Chapitre qcm, Cours cours, int index) {
        model.addAttribute("slide", Routes.ADR_QCM);
        irrigateBaseQCM(model, userInfos, qcm, cours, index);
    }

    public void irrigateBaseQCM(Model model, AuthenticationInfos userInfos, Chapitre qcm, Cours cours, int index) {
        model.addAttribute("qcm", qcm);
        model.addAttribute("question", qcm.getQcmQuestions().get(index));
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("cours", cours);
        model.addAttribute("idCours", cours.getId());
    }

    public void irrigateQCMFinished(Model model, AuthenticationInfos userInfos, Chapitre qcm, QCMPasse qcmPasse) {
        model.addAttribute("resultat", qcmPasse);
        model.addAttribute("qcm", qcm);
        model.addAttribute("slide", Routes.ADR_QCM_TERMINE);
    }
}
