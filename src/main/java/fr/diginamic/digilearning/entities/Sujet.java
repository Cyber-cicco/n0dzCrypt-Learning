package fr.diginamic.digilearning.entities;

import fr.diginamic.digilearning.entities.enums.StatusForum;
import fr.diginamic.digilearning.repository.SalonRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "dl_sujet")
public class Sujet {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;      
    private String libelle;
    @OneToMany(mappedBy = "sujetForum")
    private List<Salon> salonList;

    public List<Salon> getSalons(Utilisateur utilisateur){
        List<Salon> salonsUtilisateur = new ArrayList<>();
        for (Salon salon : salonList) {
            if(salon.getStatusForum().equals(StatusForum.BLACKLISTE)){
                if(!utilisateur.getSalonsSurBlacklist().contains(salon)){
                    salonsUtilisateur.add(salon);
                }
            } else if (salon.getStatusForum().equals(StatusForum.WHITELISTE)) {
                if(utilisateur.getSalonsSurWhiteList().contains(salon)){
                    salonsUtilisateur.add(salon);
                }
            }
        }
        return salonsUtilisateur;
    }

    public Long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }
}
