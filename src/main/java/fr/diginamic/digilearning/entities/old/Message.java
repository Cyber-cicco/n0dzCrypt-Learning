package fr.diginamic.digilearning.entities.old;

import fr.diginamic.digilearning.entities.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;    

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "dl_message")
public class Message {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "emmetteur_id")
    private Utilisateur emetteur;
    private String contenu;


}
