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
}
