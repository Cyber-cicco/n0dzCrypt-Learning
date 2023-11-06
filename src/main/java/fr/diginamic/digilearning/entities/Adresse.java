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
