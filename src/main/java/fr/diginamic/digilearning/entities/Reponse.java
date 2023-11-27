package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dl_reponse")
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Boolean supprimee;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
