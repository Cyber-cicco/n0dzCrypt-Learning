package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "dl_question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
    private Boolean supprimee;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Reponse> reponses;
    @ManyToOne
    @JoinColumn(name = "chapitre_id")
    private Chapitre chapitre;

    public List<Reponse> getReponses() {
        return reponses.stream().filter(reponse -> !reponse.getSupprimee()).toList();
    }
}
