package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.QCMChoixDto;
import fr.diginamic.digilearning.dto.QCMDto;
import fr.diginamic.digilearning.dto.QCMQuestionDto;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.QCMChoix;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.ChapitreRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QCMService {

    private final ChapitreRepository chapitreRepository;

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

    public void getCurrentQuestion(Chapitre qcm, AuthenticationInfos userInfos) {
    }
}
