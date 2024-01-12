package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusPublication;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_qcm")
public class QCM {

    public QCM() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Enumerated
    private StatusPublication publie;
    @OneToMany(mappedBy = "qcm")
    @Builder.Default
    private List<QCMQuestion> qcmQuestions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;
}
