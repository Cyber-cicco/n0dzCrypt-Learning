package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.service.RatingService;
import fr.diginamic.digilearning.page.validators.QuestionValidator;
import fr.diginamic.digilearning.page.validators.ReponseValidator;
import fr.diginamic.digilearning.repository.QuestionRepository;
import fr.diginamic.digilearning.repository.ReponseRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
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

    private final CoursService coursService;
    private final QuestionValidator questionValidator;
    private final ReponseValidator reponseValidator;
    private final QuestionRepository questionRepository;
    private final RatingService<Question, RelationQuestion> questionRatingService;
    private final RatingService<Reponse, RelationReponse> reponseRatingService;
    private final ReponseRepository reponseRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AuthenticationService authenticationService;

    @PatchMapping("/question/like")
    public String changeLikeQuestion(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Question question = questionRepository
                .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        likeQuestion(question, userInfos.getId());
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/cours/visionneuse/fragments/cours.question.rating";
    }
    @PatchMapping("/question/dislike")
    public String changeDislikeQuestion(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Question question = questionRepository.findByIdAndUtilisateurId(idQuestion, userInfos.getId()).orElseThrow(EntityNotFoundException::new);
        dislikeQuestion(question, userInfos.getId());
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/cours/visionneuse/fragments/cours.question.rating";
    }

    @PatchMapping("/reponse/like")
    public String changeLikeReponse(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Reponse reponse = reponseRepository
                .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        likeReponse(reponse, userInfos.getId());
        model.addAttribute("reponse", reponse);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/cours/visionneuse/fragments/cours.reponse.rating";
    }

    @PatchMapping("/reponse/dislike")
    public String changeDislikeReponse(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Reponse reponse = reponseRepository
                .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        dislikeReponse(reponse, userInfos.getId());
        model.addAttribute("reponse", reponse);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/cours/visionneuse/fragments/cours.reponse.rating";
    }

    @GetMapping("/reponse/modal")
    public String getReponseModal(Model model, @RequestParam("id") Long idQuestion) {
        model.addAttribute("idQuestion", idQuestion);
        return "/components/modal/modal.cours.reponse";
    }

    @GetMapping("/question/modal")
    public String getQuestionModal(Model model, @RequestParam("id") Long idChapitre) {
        model.addAttribute("idChapitre", idChapitre);
        return "/components/modal/modal.cours.question";
    }

    @PostMapping("/question")
    public String postNewQuestion(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idChapitre, @ModelAttribute MessageDto questionDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        questionValidator.validateQuestion(questionDto.getMessage());
        Chapitre chapitre = coursService.saveQuestion(userInfos.getId(), idChapitre, questionDto);
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestions());
        return "pages/cours/visionneuse/fragments/chapitre.questions";

    }
    @PostMapping("/reponse")
    public String postNewReponse(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion, @ModelAttribute MessageDto reponseDto) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        reponseValidator.validateReponse(reponseDto.getMessage());
        Question updatedQuestion = coursService.saveReponse(userInfos.getId(), idQuestion, reponseDto);
        model.addAttribute("question", updatedQuestion);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/cours/visionneuse/fragments/chapitre.reponses";

    }
    private void likeReponse(Reponse reponse, Long idUtilisateur) {
        reponseRatingService.likePushed(reponse, idUtilisateur, () ->
                RelationReponse.builder()
                        .reponse(reponse)
                        .utilisateur(utilisateurRepository.findById(idUtilisateur)
                                .orElseThrow(EntityNotFoundException::new))
                        .liked(true)
                        .disliked(false)
                        .build()
        );
    }

    private void dislikeReponse(Reponse reponse, Long idUtilisateur) {
        reponseRatingService.dislikePushed(reponse, idUtilisateur, () ->
                RelationReponse.builder()
                        .reponse(reponse)
                        .utilisateur(utilisateurRepository.findById(idUtilisateur)
                                .orElseThrow(EntityNotFoundException::new))
                        .liked(false)
                        .disliked(true)
                        .build()
        );
    }
    private void likeQuestion(Question question, Long idUtilisateur){
        questionRatingService.likePushed(question, idUtilisateur, ()->
                RelationQuestion.builder()
                        .liked(true)
                        .disliked(false)
                        .question(question)
                        .utilisateur(utilisateurRepository.findById(idUtilisateur)
                                .orElseThrow(EntityNotFoundException::new))
                        .build()
        );
    }
    public void dislikeQuestion(Question question, Long idUtilisateur) {
        questionRatingService.dislikePushed(question, idUtilisateur, () ->
                RelationQuestion.builder()
                        .liked(false)
                        .disliked(true)
                        .question(question)
                        .utilisateur(utilisateurRepository.findById(idUtilisateur)
                                .orElseThrow(EntityNotFoundException::new))
                        .build()
        );
    }
}
