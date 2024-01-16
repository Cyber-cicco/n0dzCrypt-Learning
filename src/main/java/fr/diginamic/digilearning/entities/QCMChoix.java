package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

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

}
