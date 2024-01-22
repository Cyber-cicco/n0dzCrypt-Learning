package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.QCMQuestionRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChapitreService {
    private final RatingService<Question, RelationQuestion> questionRatingService;
    private final RatingService<Reponse, RelationReponse> reponseRatingService;
    private final UtilisateurRepository utilisateurRepository;
    private final QCMQuestionRepository qcmQuestionRepository;
    private final CoursService coursService;
    private final CoursRepository coursRepository;
    public void likeQuestion(Question question, Long idUtilisateur){
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
    public void likeReponse(Reponse reponse, Long idUtilisateur) {
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

    public void dislikeReponse(Reponse reponse, Long idUtilisateur) {
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

    public QCMQuestion updateIllustrationQCM(Long idQuestion, String fileName) {
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion)
                .orElseThrow(EntityNotFoundException::new);
        question.setIllustration(fileName);
        return qcmQuestionRepository.save(question);
    }

    public QCMQuestion deleteIllustration(Long idQuestion) {
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion)
                .orElseThrow(EntityNotFoundException::new);
        question.setIllustration(null);
        return qcmQuestionRepository.save(question);
    }

    public record ChapitreInfos(Chapitre chapitre, FlagCours flagCours, Cours cours){}
    public ChapitreInfos getChapitreInfos(AuthenticationInfos userInfos, Integer idChapitre, Long idCours){
        Cours cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        Chapitre chapitre = coursService.getChapitreIfExistsElseThrow(cours, idChapitre);
        FlagCours flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        return new ChapitreInfos(chapitre, flagCours, cours);
    }
}
