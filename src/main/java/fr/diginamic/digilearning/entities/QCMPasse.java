package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.repository.QCMPasseRepository;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dl_qcm_passe")
public class QCMPasse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean archived;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    private Chapitre qcm;
    private Boolean finished;
    @ManyToOne
    @JoinColumn(name = "id_publication")
    private QCMPublication qcmPublication;
    private LocalDateTime datePassage;
    @OneToMany(mappedBy = "qcmPasse")
    @Builder.Default
    private List<ResultatQuestion> resultatsQuestions = new ArrayList<>();

    public boolean isQCMFinished() {
        return resultatsQuestions.size() == qcm.getQcmQuestionsPubliees().size();
    }

    public String getNote() {
        long questionsBienRepondues = resultatsQuestions.stream()
                .filter(resultatQuestion -> resultatQuestion.getResultats().isQuestionBienRepondue())
                .count();
        int note = (int) Math.ceil(((double) questionsBienRepondues / resultatsQuestions.size())*20);
        return note + "/20";
    }

    public int getIndex() {
        return resultatsQuestions.size();
    }
}
