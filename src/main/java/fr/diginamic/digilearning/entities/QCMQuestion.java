package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private String illustration;
    private String commentaire;
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

    public List<QCMChoix> getBonChoix(){
        return choix.stream().filter(QCMChoix::getValid).toList();
    }
    public QCMQuestion clone() {
        QCMQuestion qcmQuestion = QCMQuestion.builder()
                .libelle(libelle)
                .ordre(ordre)
                .illustration(illustration)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QCMQuestion question = (QCMQuestion) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
