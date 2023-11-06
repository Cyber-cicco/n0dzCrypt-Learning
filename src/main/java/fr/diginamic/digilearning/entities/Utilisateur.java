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
@Table(name = "UTILISATEUR")
public class Utilisateur {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "PRENOM")
    private String prenom;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String motDePasse;
    @Column(name = "TELEPHONE")
    private String telephone;
    @ManyToMany
    @JoinTable(name = "dl_utilisateur_session",
            joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "id")
    )
    private List<Session> sessionList;
    @ManyToMany
    @JoinTable(name = "ROLE_UTILISATEUR",
            joinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID")
    )
    private List<Role> roleList;
    @ManyToOne
    @JoinColumn(name = "ds_adresse_id")
    private Adresse adresse;
    @ManyToMany
    @JoinTable(name = "dl_utilisateur_conversation",
            joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    )
    private List<Conversation> conversationList;
    @OneToMany(mappedBy = "emetteur")
    private List<Post> postList;
    @OneToMany(mappedBy = "emetteur")
    private List<Message> messageList;


}
