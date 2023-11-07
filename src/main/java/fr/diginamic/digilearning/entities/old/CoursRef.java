package fr.diginamic.digilearning.entities.old;

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
@Table(name = "COURS_REF")
public class CoursRef {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "TYPE_COURS")
    private String typeCourt;
    @OneToMany(mappedBy = "coursRef")
    private List<RessourceCours> ressourceCoursList;


}
