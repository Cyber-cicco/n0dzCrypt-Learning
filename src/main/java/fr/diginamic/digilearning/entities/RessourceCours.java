package fr.diginamic.digilearning.entities;

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
@Table(name = "dl_ressource_cours")
public class RessourceCours {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    @ManyToOne
    @JoinColumn(name = "coursRef_id")
    private CoursRef coursRef;
    private String libelleAffiche;
    private String libelle;
    private String typeRessource;

}
