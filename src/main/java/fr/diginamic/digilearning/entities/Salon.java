package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusForum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * Représente le salon d'un forum.
 *
 * @author Abel Ciccoli
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "dl_salon")
public class Salon {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String titre;
    @Enumerated
    private StatusForum statusForum;
    @ManyToMany
    @JoinTable(name = "whitelist",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID")
    )
    private List<Utilisateur> whiteList;
    @ManyToMany
    @JoinTable(name = "blacklist",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "ID")
    )
    private List<Utilisateur> blackList;
    @ManyToMany
    @JoinTable(name = "salon_moderateurs",
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "moderateur_id", referencedColumnName = "id")
    )
    private List<Utilisateur> moderateurs;
    @ManyToOne
    @JoinColumn(name = "sujet_id")
    private Sujet sujetForum;
    @OneToMany(mappedBy = "salon")
    private List<FilDiscussion> filDiscussionList;


    @ManyToMany
    @JoinTable(name = "dl_salon_session",
            inverseJoinColumns = @JoinColumn(name = "session_id", referencedColumnName = "ID"),
            joinColumns = @JoinColumn(name = "salon_id", referencedColumnName = "id")
    )
    private List<Session> sessionsAutorisees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salon salon = (Salon) o;
        return Objects.equals(id, salon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
