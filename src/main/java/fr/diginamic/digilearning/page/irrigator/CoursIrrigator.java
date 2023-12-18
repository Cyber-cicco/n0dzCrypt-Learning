package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * Irrigateur du modèle donnée par le controlleur
 * HyperMédia du cours
 */
@Service
@RequiredArgsConstructor
public class CoursIrrigator {
    private final ModuleRepository moduleRepository;
    private final CoursService coursService;
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
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        Chapitre chapitre = coursService.getChapitreIfExistsElseThrow(cours, idChapitre);
        model.addAttribute("idUtilisateur", userInfos.getId());
        model.addAttribute("contenu", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenu()));
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestionsNonSuppr());
        model.addAttribute("cours", cours);
        model.addAttribute("flags", flagCours);
        model.addAttribute("idCours", idCours);
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

    public void irrigateAdminPanel(AuthenticationInfos userInfos, Model model) {
        model.addAttribute("user", userInfos);
        model.addAttribute("coursCrees", coursRepository.getCoursCrees(userInfos.getId()));
    }
}
