package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_qcm_question")
public class QCMQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private Integer ordre;
    @OneToMany(mappedBy = "question")
    @Builder.Default
    private List<QCMChoix> choix = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    Chapitre qcm;
    @ManyToOne
    @JoinColumn(name = "qcm_publie_id")
    Chapitre qcmPublie;

    public QCMQuestion clone() {
        QCMQuestion qcmQuestion = QCMQuestion.builder()
                .libelle(libelle)
                .ordre(ordre)
                .qcmPublie(qcm)
                .build();
        qcmQuestion.setChoix(choix.stream().map(choix -> choix.clone(qcmQuestion)).toList());
        return qcmQuestion;
    }

    @Override
    public String toString() {
        return "QCMQuestion{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", ordre=" + ordre +
                '}';
    }
}
