package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Setter
@Table(name = "dl_qcm_choix")
public class QCMChoix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private Boolean valid;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private QCMQuestion question;

    public QCMChoix clone(QCMQuestion question){
        return QCMChoix.builder()
                .question(question)
                .libelle(libelle)
                .valid(valid)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QCMChoix qcmChoix = (QCMChoix) o;
        return Objects.equals(id, qcmChoix.id) && Objects.equals(libelle, qcmChoix.libelle) && Objects.equals(valid, qcmChoix.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, valid);
    }
}
