package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.entities.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    public String getProgression(Utilisateur utilisateur){
        return "50";
    }
}
