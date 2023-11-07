package fr.diginamic.digilearning.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ds_adresse")
public class Adresse {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    @OneToMany(mappedBy = "adresse")
    private List<Utilisateur> utilisateurList;
    private String rue;
    private String ville;
    private String codePostal;


}
