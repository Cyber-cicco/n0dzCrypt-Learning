package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_resultat_question")
public class ResultatQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**List des choix notés bons par l'apprenant*/
    @ManyToMany
    @JoinTable(name = "dl_resultat_choix",
            joinColumns = @JoinColumn(name = "resultat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "choix_id", referencedColumnName = "id")
    )
    private List<QCMChoix> choixValides;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QCMQuestion question;
    @ManyToOne
    @JoinColumn(name = "qcmPasse_id")
    private QCMPasse qcmPasse;

    public record FlagsChoix(Boolean doitEtreValide, Boolean aEteValide){}
    public record Resultat(Map<QCMChoix, FlagsChoix> choixToValidite, boolean isQuestionBienRepondue){}
    public Resultat getResultats(){
        List<QCMChoix> bonChoix = question.getBonChoix();
        Map<QCMChoix, FlagsChoix> choixToValidite = new HashMap<>();
        boolean isQuestionBienRepondue = true;
        for (QCMChoix choix : getQuestion().getChoix()) {
            choixToValidite.put(choix, new FlagsChoix(false, false));
        }
        for (QCMChoix choix : bonChoix) {
            if(choixValides.contains(choix)) {
                choixToValidite.put(choix, new FlagsChoix(true, true));
            } else {
                choixToValidite.put(choix, new FlagsChoix(true, false));
                isQuestionBienRepondue = false;
            }
        }
        for (QCMChoix choixValide : choixValides) {
            if(bonChoix.contains(choixValide)) {
                choixToValidite.put(choixValide, new FlagsChoix(true, true));
            } else {
                choixToValidite.put(choixValide, new FlagsChoix(false, true));
                isQuestionBienRepondue = false;
            }
        }
        return new Resultat(choixToValidite, isQuestionBienRepondue);
    }
}