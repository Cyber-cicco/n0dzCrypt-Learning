package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusResponsableSession;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TypeBinderType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ADMINISTRATION_SESSION")
public class AdministrationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ID_UTILISATEUR")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "ID_SESSION")
    private Session session;
    @Enumerated
    @Column(name = "STATUS_RESPONSABLE_SESSION")
    private StatusResponsableSession statusResponsableSession;
}
