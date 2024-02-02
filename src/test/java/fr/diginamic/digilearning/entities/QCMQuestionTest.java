package fr.diginamic.digilearning.entities;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QCMQuestionTest {

    @Test
    void isSimilar() {
        QCMQuestion question1 = QCMQuestion.builder().build();
        QCMQuestion question2 = QCMQuestion.builder().build();
        QCMQuestion question3 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .build();
        QCMQuestion question4 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .build();
        QCMQuestion question5 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .choix(List.of(
                        QCMChoix.builder().valid(true).libelle("question1").build(),
                        QCMChoix.builder().valid(true).libelle("question2").build(),
                        QCMChoix.builder().valid(true).libelle("question3").build()
                ))
                .build();
        QCMQuestion question6 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .choix(List.of(
                        QCMChoix.builder().valid(true).libelle("question1").build(),
                        QCMChoix.builder().valid(true).libelle("question2").build(),
                        QCMChoix.builder().valid(true).libelle("question3").build()
                ))
                .build();
        QCMQuestion question7 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .choix(List.of(
                        QCMChoix.builder().valid(false).libelle("question1").build(),
                        QCMChoix.builder().valid(true).libelle("question2").build(),
                        QCMChoix.builder().valid(true).libelle("question3").build()
                ))
                .build();
        QCMQuestion question8 = QCMQuestion.builder()
                .libelle("test1")
                .commentaire("test1")
                .ordre(1)
                .choix(List.of(
                        QCMChoix.builder().valid(true).libelle("question1").build(),
                        QCMChoix.builder().valid(true).libelle("question7").build(),
                        QCMChoix.builder().valid(true).libelle("question3").build()
                ))
                .build();
        QCMQuestion question9 = QCMQuestion.builder()
                .commentaire("test1")
                .ordre(1)
                .choix(List.of(
                        QCMChoix.builder().valid(true).libelle("question1").build(),
                        QCMChoix.builder().valid(true).libelle("question2").build(),
                        QCMChoix.builder().valid(true).libelle("question3").build()
                ))
                .build();
        assertTrue(question2.isSimilar(question1));
        assertTrue(question3.isSimilar(question4));
        assertTrue(question5.isSimilar(question6));
        assertFalse(question2.isSimilar(question4));
        assertFalse(question7.isSimilar(question6));
        assertFalse(question8.isSimilar(question6));
        assertFalse(question9.isSimilar(question6));
    }
}