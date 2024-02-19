package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.enums.StatusPublication;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.ChapitreRepository;
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
    private final ChapitreRepository chapitreRepository;
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

    public Chapitre depublierChapitre(Long idUtilisateur, Long idChapitre) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idUtilisateur, idChapitre).orElseThrow(EntityNotFoundException::new);
        chapitre.setStatusPublication(StatusPublication.NON_PUBLIE);
        return chapitreRepository.save(chapitre);
    }

    public record ChapitreInfos(Chapitre chapitre, FlagCours flagCours, Cours cours){}
    public ChapitreInfos getChapitreInfos(AuthenticationInfos userInfos, Integer ordreChapitre, Long idCours){
        Cours cours;
        Chapitre chapitre;
        FlagCours flagCours = null;
        if(userInfos.isFormateur() || userInfos.isAdministrateur()){
            cours = coursRepository.findById(idCours).orElseThrow(EntityNotFoundException::new);
            chapitre = coursService.getChapitreIfExists(cours, ordreChapitre);
        } else {
            cours = coursRepository.findByUserAndId(userInfos.getId(), idCours).orElseThrow(EntityNotFoundException::new);
            chapitre = coursService.getChapitreIfExistsAndPublie(cours, ordreChapitre);
        }

        if(userInfos.isStagiaire()){
            flagCours = coursService.getFlagByCoursAndStagiaire(cours, userInfos);
        }
        return new ChapitreInfos(chapitre, flagCours, cours);
    }
}
