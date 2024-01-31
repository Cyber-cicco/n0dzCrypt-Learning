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
    @ManyToMany
    @JoinTable(name = "dl_publication_question",
            joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "id")
    )
    private List<QCMPublication> publications;

    public List<QCMChoix> getBonChoix(){
        return choix.stream()
                .filter(QCMChoix::getValid)
                .toList();
    }
    public QCMQuestion clone() {
        QCMQuestion qcmQuestion = QCMQuestion.builder()
                .libelle(libelle)
                .ordre(ordre)
                .illustration(illustration)
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

    public boolean isSimilar(QCMQuestion question) {
        if(question.choix.size() != choix.size()){
            return false;
        }
        if(
        ! (Objects.equals(this.libelle, question.getLibelle())
                && Objects.equals(this.illustration, question.getIllustration())
                && Objects.equals(this.ordre, question.getOrdre())
                && Objects.equals(this.commentaire, question.getCommentaire()))
        ) {
            return false;
        }
        for (int i = 0; i < choix.size(); ++i) {
            if(!(
                    Objects.equals(choix.get(i).getLibelle(), question.getChoix().get(i).getLibelle()) &&
                    Objects.equals(choix.get(i).getValid(),question.getChoix().get(i).getValid())
                )) {
                return false;
            }
        }
        return true;
    }
}
