package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.QCMChoixDto;
import fr.diginamic.digilearning.dto.QCMDto;
import fr.diginamic.digilearning.dto.QCMQuestionDto;
import fr.diginamic.digilearning.dto.ReponseQCMDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QCMService {

    private final ChapitreRepository chapitreRepository;
    private final QCMPasseRepository qcmPasseRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final QCMChoixRepository qcmChoixRepository;
    private final ResultatQuestionRepository resultatQuestionRepository;

    public QCMDto getQCM(Chapitre qcm){
        return QCMDto.builder()
                .id(qcm.getId())
                .libelle(qcm.getLibelle())
                .questions(qcm.getQcmQuestions().stream().map(question ->
                        QCMQuestionDto.builder()
                                .id(question.getId())
                                .libelle(question.getLibelle())
                                .ordre(question.getOrdre())
                                .commentaire(question.getCommentaire())
                                .illustration(question.getIllustration())
                                .choix(question.getChoix().stream().map(qcmChoix ->
                                        QCMChoixDto.builder()
                                                .id(qcmChoix.getId())
                                                .libelle(qcmChoix.getLibelle())
                                                .build()
                                ).toList())
                                .build()
                ).toList())
                .build();
    }

    public QCMDto getQCM(long idQcm, long idUtilisateur){
        Chapitre qcm = chapitreRepository.findByIdAndUtilisateurId(idQcm, idUtilisateur)
                .orElseThrow(EntityNotFoundException::new);
        return getQCM(qcm);
    }

    public void archiveQCMPasse(QCMPasse qcmPasse) {
        qcmPasse.setArchived(true);
        qcmPasseRepository.save(qcmPasse);
    }


    public record RepriseQCMInfos(Cours cours, Chapitre chapitre, QCMPasse qcmPasse, int index){}

    @Transactional
    public RepriseQCMInfos recommencerQCM(Long idUtilisateur, Long idChapitre) {
        QCMPasse qcmPasse = qcmPasseRepository.findByUtilisateurAndQCM(idUtilisateur, idChapitre)
                .orElseThrow(EntityNotFoundException::new);
        Chapitre chapitre = qcmPasse.getQcm();
        qcmPasse.setArchived(true);
        qcmPasseRepository.save(qcmPasse);
        return new RepriseQCMInfos(chapitre.getCours(), chapitre, qcmPasse, 0);
    }

    public RepriseQCMInfos reprendreQCM(Long idUtilisateur, Long idChapitre) {
        QCMPasse qcmPasse = qcmPasseRepository.findByUtilisateurAndQCM(idUtilisateur, idChapitre)
                .orElseThrow(EntityNotFoundException::new);
        Chapitre chapitre = qcmPasse.getQcm();
        Cours cours = chapitre.getCours();
        int index = qcmPasse.getIndex();
        return new RepriseQCMInfos(cours, chapitre, qcmPasse, index);
    }


    public record ResponseForNewResponse(Chapitre qcm, QCMPasse qcmPasse, Optional<Integer> index){}

    /**
     * Sauvegarde une nouvelle réponse.
     * Très peu optimisé d'un point du nombre de requête SQL mais
     * TODO : OPTIMISATION SQL
     * @param idUtilisateur
     * @param idQCM
     * @param idQuestion
     * @param reponseQCM
     */
    public ResponseForNewResponse postNewResponse(Long idUtilisateur, Long idQCM, Long idQuestion, List<ReponseQCMDto> reponseQCM) {
        Chapitre qcm = chapitreRepository.findByIdAndUtilisateurId(idQCM, idUtilisateur)
                .orElseThrow(EntityNotFoundException::new);
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElseThrow(EntityNotFoundException::new);
        QCMPasse qcmPasse = qcmPasseRepository.findByUtilisateurAndQCM(idUtilisateur, idQCM)
                .orElseGet(() -> qcmPasseRepository.save(QCMPasse
                        .builder()
                        .qcm(qcm)
                        .qcmPublication(qcm.getQcmPublication())
                        .datePassage(LocalDateTime.now())
                        .archived(false)
                        .utilisateur(utilisateur)
                        .build()));
        QCMQuestion question = qcm.getQcmQuestionsPubliees()
                .stream()
                .filter(question1 -> question1.getId().equals(idQuestion))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
        ResultatQuestion resultatQuestion = ResultatQuestion.builder()
                .question(question)
                .qcmPasse(qcmPasse)
                .choixValides(reponseQCM.stream()
                        .filter(ReponseQCMDto::getValue)
                        .map(reponseQCMDto -> qcmChoixRepository.findById(reponseQCMDto.getId())
                                .orElseThrow(EntityNotFoundException::new))
                        .toList()
                )
                .build();
        resultatQuestionRepository.save(resultatQuestion);
        int idCurrQ = qcm.getQcmQuestionsPubliees().indexOf(question);
        if(idCurrQ + 1 >= qcm.getQcmQuestionsPubliees().size()){
            return new ResponseForNewResponse(qcm, qcmPasse, Optional.empty());
        }
        return new ResponseForNewResponse(qcm, qcmPasse, Optional.of(idCurrQ + 1));
    }

    //public QCMQuestion getCurrentQuestion(Chapitre qcm, AuthenticationInfos userInfos) {
    //
    //}
}
