package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.page.irrigator.ChapitreIrrigator;
import fr.diginamic.digilearning.page.service.ChapitreService;
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

    @PatchMapping("/question/like")
    public String changeLikeQuestion( Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Question question = chapitreIrrigator.irrigateCoursQuestion(model, userInfos, idQuestion);
        chapitreService.likeQuestion(question, userInfos.getId());
        return Routes.ADR_COURS_QUESTION_RATING;
    }
    @PatchMapping("/question/dislike")
    public String changeDislikeQuestion( Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Question question = chapitreIrrigator.irrigateCoursQuestion(model, userInfos, idQuestion);
        chapitreService.dislikeQuestion(question, userInfos.getId());
        return Routes.ADR_COURS_QUESTION_RATING;
    }

    @PatchMapping("/reponse/like")
    public String changeLikeReponse( Model model, @RequestParam("id") Long idReponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Reponse reponse = chapitreIrrigator.irrigateReponse(model, userInfos, idReponse);
        chapitreService.likeReponse(reponse, userInfos.getId());
        return Routes.ADR_COURS_REPONSE_RATING;
    }

    @PatchMapping("/reponse/dislike")
    public String changeDislikeReponse( Model model, @RequestParam("id") Long idReponse){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Reponse reponse = chapitreIrrigator.irrigateReponse(model, userInfos, idReponse);
        chapitreService.dislikeReponse(reponse, userInfos.getId());
        return Routes.ADR_COURS_REPONSE_RATING;
    }

    @GetMapping("/reponse/modal")
    public String getReponseModal(Model model, @RequestParam("id") Long idQuestion) {
        model.addAttribute("idQuestion", idQuestion);
        return Routes.ADR_MODAL_COURS_REPONSE;
    }

    @GetMapping("/question/modal")
    public String getQuestionModal(Model model, @RequestParam("id") Long idChapitre) {
        model.addAttribute("idChapitre", idChapitre);
        return Routes.ADR_MODAL_COURS_QUESTION;
    }

    @PostMapping("/question")
    public String postNewQuestion( Model model, @RequestParam("id") Long idChapitre, @ModelAttribute MessageDto questionDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateListQuestions(model, userInfos, idChapitre, questionDto);
        return Routes.ADR_LISTE_QUESTIONS;

    }
    @PostMapping("/reponse")
    public String postNewReponse( Model model, @RequestParam("id") Long idQuestion, @ModelAttribute MessageDto reponseDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateListeReponses(model, userInfos, idQuestion, reponseDto);
        return Routes.ADR_LISTE_REPONSES;
    }
}
