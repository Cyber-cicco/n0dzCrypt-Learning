package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "dl_flag_cours")
public class FlagCours {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean liked;
    private Boolean boomarked;
    private Boolean finished;
    private LocalDateTime datePrevue;
    @ManyToOne
    @JoinColumn(name = "stagiaire_id")
    private Utilisateur stagiaire;

    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;

    @Override
    public String toString() {
        return "FlagCours{" +
                "liked=" + liked +
                ", boomarked=" + boomarked +
                ", finished=" + finished +
                ", cours=" + cours.getTitre() +
                '}';
    }
}
