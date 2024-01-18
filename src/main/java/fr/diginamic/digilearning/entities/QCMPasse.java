package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QCMPasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    private Chapitre qcm;

    @OneToMany(mappedBy = "qcmPasse")
    private List<ResultatQuestion> resultatsChoix;
}
