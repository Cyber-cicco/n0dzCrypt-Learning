package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Représente le message d'un fil de discussion entre un stagiaire
 * et un responsable de la session
 *
 * @author Abel Ciccoli
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "dl_message")
public class Message implements Comparable<Message> {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @Column(name = "emetteur_id", insertable = false, updatable = false)
    private Long emetteurId;
    @ManyToOne
    @JoinColumn(name = "emetteur_id")
    private Utilisateur emetteur;
    private String contenu;
    private LocalDateTime dateEmission;

    @OneToOne
    @JoinColumn(name = "next_id")
    private Message next;

    @OneToOne
    @JoinColumn(name = "previous_id")
    private Message previous;

    public LocalDate getJourEmission(){
        return dateEmission.toLocalDate();
    }

    @Override
    public int compareTo(Message o) {
        return this.dateEmission.compareTo(o.getDateEmission());
    }

    @Override
    public String toString() {
        return "Message{" +
                "contenu='" + contenu + '\'' +
                '}';
    }
}
