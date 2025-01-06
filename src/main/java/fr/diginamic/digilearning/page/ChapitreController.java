package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.DTO.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.page.irrigator.ChapitreIrrigator;
import fr.diginamic.digilearning.service.ChapitreService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chapitre")
@RequiredArgsConstructor
public class ChapitreController {

    private final ChapitreIrrigator chapitreIrrigator;
    private final AuthenticationService authenticationService;
    private final ChapitreService chapitreService;

    /**
     * Permet de changer le flag liked d'une question donnée pour l'utilisateur authentifié
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idQuestion l'identifiant de la question
     * @return la zone "like / dislike" de la question donnée.
     */
    @PatchMapping("/question/like")
    public String changeLikeQuestion( Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Question question = chapitreIrrigator.irrigateCoursQuestion(model, userInfos, idQuestion);
        chapitreService.likeQuestion(question, userInfos.getId());
        return Routes.ADR_COURS_QUESTION_RATING;
    }
    /**
     * Permet de changer le flag disliked d'une question donnée pour l'utilisateur authentifié
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idQuestion l'identifiant de la question
     * @return la zone "like / dislike" de la question donnée.
     */
    @PatchMapping("/question/dislike")
    public String changeDislikeQuestion( Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Question question = chapitreIrrigator.irrigateCoursQuestion(model, userInfos, idQuestion);
        chapitreService.dislikeQuestion(question, userInfos.getId());
        return Routes.ADR_COURS_QUESTION_RATING;
    }


    /**
     * Permet de changer le flag liked d'une réponse donnée pour l'utilisateur authentifié
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idReponse l'identifiant de la réponse
     * @return la zone "like / dislike" de la question donnée.
     */
    @PatchMapping("/reponse/like")
    public String changeLikeReponse( Model model, @RequestParam("id") Long idReponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Reponse reponse = chapitreIrrigator.irrigateReponse(model, userInfos, idReponse);
        chapitreService.likeReponse(reponse, userInfos.getId());
        return Routes.ADR_COURS_REPONSE_RATING;
    }

    /**
     * Permet de changer le flag disliked d'une réponse donnée pour l'utilisateur authentifié
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idReponse l'identifiant de la réponse
     * @return la zone "like / dislike" de la question donnée.
     */
    @PatchMapping("/reponse/dislike")
    public String changeDislikeReponse( Model model, @RequestParam("id") Long idReponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Reponse reponse = chapitreIrrigator.irrigateReponse(model, userInfos, idReponse);
        chapitreService.dislikeReponse(reponse, userInfos.getId());
        return Routes.ADR_COURS_REPONSE_RATING;
    }

    /**
     * Permet d'obtenir le formulaire de création d'une nouvelle question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idQuestion l'identifiant de la question
     * @return le corps de la modale
     */
    @GetMapping("/reponse/modal")
    public String getReponseModal(Model model, @RequestParam("id") Long idQuestion) {
        model.addAttribute("idQuestion", idQuestion);
        return Routes.ADR_MODAL_COURS_REPONSE;
    }

    /**
     * Permet d'obtenir le formulaire de création d'une nouvelle question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idChapitre l'identifiant du chapitre dans lequel la question est posé
     * @return le corps de la modale
     */
    @GetMapping("/question/modal")
    public String getQuestionModal(Model model, @RequestParam("id") Long idChapitre) {
        model.addAttribute("idChapitre", idChapitre);
        return Routes.ADR_MODAL_COURS_QUESTION;
    }

    /**
     * Permet de poster une nouvelle question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idChapitre l'identifiant du chapitre à rattacher à la question
     * @param questionDto représentation de la question posée par l'utilisateur
     * @return la liste des questions
     */
    @PostMapping("/question")
    public String postNewQuestion( Model model, @RequestParam("id") Long idChapitre, @ModelAttribute MessageDto questionDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateListQuestions(model, userInfos, idChapitre, questionDto);
        return Routes.ADR_LISTE_QUESTIONS;

    }
    /**
     * Permet de poster une nouvelle question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param idQuestion l'identifiant de la réponse à rattacher à la question
     * @param reponseDto représentation de la réponse offerte à la question
     * @return la liste des réponses à la question
     */
    @PostMapping("/reponse")
    public String postNewReponse( Model model, @RequestParam("id") Long idQuestion, @ModelAttribute MessageDto reponseDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateListeReponses(model, userInfos, idQuestion, reponseDto);
        return Routes.ADR_LISTE_REPONSES;
    }
}
