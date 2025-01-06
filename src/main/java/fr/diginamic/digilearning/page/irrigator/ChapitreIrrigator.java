package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.DTO.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.service.CoursService;
import fr.diginamic.digilearning.validators.QuestionValidator;
import fr.diginamic.digilearning.validators.ReponseValidator;
import fr.diginamic.digilearning.repository.ChapitreRepository;
import fr.diginamic.digilearning.repository.QuestionRepository;
import fr.diginamic.digilearning.repository.ReponseRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Irrigateur du model donné par le controlleur
 * HyperMédia du chapitre
 */
@Service
@RequiredArgsConstructor
public class ChapitreIrrigator {
    private final CoursService coursService;
    private final QuestionValidator questionValidator;
    private final ReponseValidator reponseValidator;
    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;
    private final ChapitreRepository chapitreRepository;

    /**
     * Irrigateur d'un élément n'affichant qu'une question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idQuestion l'identifiant de la question
     * @return la question
     */
    public Question irrigateCoursQuestion(Model model, AuthenticationInfos userInfos, Long idQuestion) {
        Question question;
        if(userInfos.isAdministrateur() || userInfos.isFormateur()){
            question = questionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        } else {
            question = questionRepository
                    .findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                    .orElseThrow(EntityNotFoundException::new);
        }
        model.addAttribute("question", question);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return question;
    }


    /**
     * Irrigateur d'un élément n'affichant qu'une réponse
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idReponse l'identifiant de la réponse
     * @return la question
     */
    public Reponse irrigateReponse(Model model, AuthenticationInfos userInfos, Long idReponse) {
        Reponse reponse;
        if(userInfos.isFormateur() || userInfos.isAdministrateur()){
            reponse = reponseRepository
                    .findById(idReponse)
                    .orElseThrow(EntityNotFoundException::new);
        } else {
            reponse = reponseRepository
                    .findByIdAndUtilisateurId(idReponse, userInfos.getId())
                    .orElseThrow(EntityNotFoundException::new);
        }
        model.addAttribute("reponse", reponse);
        model.addAttribute("idUtilisateur", userInfos.getId());
        return reponse;
    }

    /**
     * Irrigue une liste de questions pour un chapitre
     * Sauvegarde une nouvelle question
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idChapitre l'identifiant du chapitre concerné
     * @param questionDto représentation de la question posée.
     */
    public void irrigateListQuestions(Model model, AuthenticationInfos userInfos, Long idChapitre, MessageDto questionDto) {
        questionValidator.validateQuestion(questionDto.getMessage());
        Chapitre chapitre = coursService.saveQuestion(userInfos, idChapitre, questionDto);
        model.addAttribute("chapitre", chapitre);
        model.addAttribute("questions", chapitre.getQuestions());
    }
    /**
     * Irrigue une liste de réponses pour un chapitre
     * Sauvegarde une nouvelle réponse
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param idQuestion l'identifiant de la question
     * @param reponseDto représentation de la réponse donnée.
     */
    public void irrigateListeReponses(Model model, AuthenticationInfos userInfos, Long idQuestion, MessageDto reponseDto) {
        reponseValidator.validateReponse(reponseDto.getMessage());
        Question updatedQuestion = coursService.saveReponse(userInfos, idQuestion, reponseDto);
        model.addAttribute("question", updatedQuestion);
        model.addAttribute("idUtilisateur", userInfos.getId());
    }

    public void irrigateAdminChapitre(Model model, AuthenticationInfos userInfos, Chapitre chapitre) {
        model.addAttribute("contenuHTML", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenuNonPublie()));
        model.addAttribute("contenu", chapitre.getContenuNonPublie());
        model.addAttribute("video", chapitre.getLienVideo());
        model.addAttribute("id", chapitre.getId());
        model.addAttribute("idCours", chapitre.getCours().getId());
        model.addAttribute("ordre", chapitre.getOrdre());
        irrigateAjour(model, chapitre);
    }

    public void irrigateAdminChapitre(Model model, AuthenticationInfos userInfos, Long idChapitre){
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        irrigateAdminChapitre(model, userInfos, chapitre);
    }

    public void irrigateAdminQCM(Model model, Chapitre qcm) {
        model.addAttribute("qcm", qcm);
        if(!qcm.getQcmQuestions().isEmpty()) {
            model.addAttribute("question", qcm.getQcmQuestions().get(0));
        } else {
            model.addAttribute("question", null);
        }
    }

    public void irrigateAjour(Model model, Chapitre chapitre) {
        String aJour;
        String classAJour;
        switch (chapitre.getStatusPublication()) {
            case NON_PUBLIE ->{
                aJour = "La version de votre cours n'est pas encore publiée";
                classAJour = "text-error grow text-center";
            }
            case PUBLIE_PAS_A_JOUR -> {
                aJour = "La version de votre cours est en avance par rapport à la version publiée";
                classAJour = "text-error grow text-center";
            }
            case PUBLIE_A_JOUR -> {
                aJour = "La version de votre cours est publiée et à jour";
                classAJour = "text-validation grow text-center";
            }
            default -> throw new RuntimeException();
        }
        model.addAttribute("aJour", aJour);
        model.addAttribute("classAJour", classAJour);
    }
}
