package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.validators.QuestionValidator;
import fr.diginamic.digilearning.page.validators.ReponseValidator;
import fr.diginamic.digilearning.repository.QuestionRepository;
import fr.diginamic.digilearning.repository.ReponseRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class ChapitreIrrigator {
    private final CoursService coursService;
    private final QuestionValidator questionValidator;
    private final ReponseValidator reponseValidator;
    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;

    public Question irrigateCoursQuestion(Model model, AuthenticationInfos userInfos, Long idQuestion) {
        Question question = questionRepository
                .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return question;
    }

    public Reponse irrigateReponse(Model model, AuthenticationInfos userInfos, Long idReponse) {
        Reponse reponse = reponseRepository
                .findByIdAndUtilisateurId(idReponse, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("reponse", reponse);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return reponse;
    }

    public void irrigateListQuestions(Model model, AuthenticationInfos userInfos, Long idChapitre, MessageDto questionDto) {
        questionValidator.validateQuestion(questionDto.getMessage());
        Chapitre chapitre = coursService.saveQuestion(userInfos.getId(), idChapitre, questionDto);
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestions());
    }
    public void irrigateListeReponses(Model model, AuthenticationInfos userInfos, Long idQuestion, MessageDto reponseDto) {
        reponseValidator.validateReponse(reponseDto.getMessage());
        Question updatedQuestion = coursService.saveReponse(userInfos.getId(), idQuestion, reponseDto);
        model.addAttribute("question", updatedQuestion);
        model.addAttribute("idUtilisateur", userInfos.getId());
    }
}
