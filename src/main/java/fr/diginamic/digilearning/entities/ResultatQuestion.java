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
public class ResultatQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @JoinTable(name = "resultat_choix",
            joinColumns = @JoinColumn(name = "resultat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "choix_id", referencedColumnName = "id")
    )
    private List<QCMChoix> choixValides;
    @ManyToOne
    @JoinColumn(name = "qcmPasse_id")
    private QCMPasse qcmPasse;

    public record Resultat(Map<QCMChoix, Boolean> choixToValidite, boolean isQuestionBienRepondue){}
    public Resultat getResultats(QCMQuestion question){
        List<QCMChoix> bonChoix = question.getBonChoix();
        Map<QCMChoix, Boolean> choixToValidite = new HashMap<>();
        boolean isQuestionBienRepondue = true;
        for (QCMChoix choixValide : choixValides) {
            if(bonChoix.contains(choixValide)) {
                choixToValidite.put(choixValide, true);
            } else {
                choixToValidite.put(choixValide, false);
                isQuestionBienRepondue = false;
            }
        }
        for (QCMChoix choix : bonChoix) {
            if(choixValides.contains(choix)) {
                choixToValidite.put(choix, true);
            } else {
                choixToValidite.put(choix, false);
                isQuestionBienRepondue = false;
            }
        }
        return new Resultat(choixToValidite, isQuestionBienRepondue);
    }
}