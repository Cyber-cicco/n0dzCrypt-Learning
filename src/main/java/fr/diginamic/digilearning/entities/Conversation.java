package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;    
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "dl_conversation")
public class Conversation {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    @ManyToMany
    @JoinTable(name = "dl_utilisateur_conversation",
            joinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    )
    private List<Utilisateur> participants;
    private String libelleGroupe;
    @OneToMany(mappedBy = "conversation")
    private List<Message> messageList;


}
