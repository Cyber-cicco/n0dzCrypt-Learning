package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusResponsableSession;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entité servant de relation entre un utilisateur
 * et une session pour déterminer son role au sein de
 * celle-ci
 *
 * @author Abel Ciccoli
 */
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
