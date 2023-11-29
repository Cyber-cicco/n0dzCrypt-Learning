package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.entities.Question;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.repository.QuestionRepository;
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
    private final QuestionRepository questionRepository;
    private final AuthenticationService authenticationService;
    @PatchMapping("/question/like")
    public String likeQuestion(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Question question = questionRepository
                .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        coursService.likeQuestionPushed(question, userInfos.getId());
        System.out.println(question.getRelationQuestions());
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/fragments/cours/cours.rating";
    }
    @PatchMapping("/question/dislike")
    public String dislikeQuestion(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("id") Long idQuestion){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        Question question = questionRepository.findByIdAndUtilisateurId(idQuestion, userInfos.getId()).orElseThrow(EntityNotFoundException::new);
        coursService.dislikeQuestionPushed(question, userInfos.getId());
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return "pages/fragments/cours/cours.rating";
    }
}
