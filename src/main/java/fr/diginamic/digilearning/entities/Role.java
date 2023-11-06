package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ROLE")
public class Role {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;      
    @ManyToMany
    @JoinTable(name = "ROLE_UTILISATEUR",
            joinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_UTILISATEUR", referencedColumnName = "ID")
    )
    private List<Utilisateur> utilisateurList;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private RoleEnum typeRole;


}
