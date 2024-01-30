package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "dl_qcm_publication")
public class QCMPublication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean derniere;

    @ManyToMany
    @JoinTable(name = "publication_question",
            joinColumns = @JoinColumn(name = "publication_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id")
    )
    @Builder.Default
    private List<QCMQuestion> questions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "qcm_id")
    private Chapitre qcm;
}
