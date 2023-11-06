package fr.diginamic.digilearning.components.service;

import fr.diginamic.digilearning.components.elements.NavLink;
import fr.diginamic.digilearning.components.elements.NavLinks;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NavBarService {

    public NavLinks[] getLinks(AuthenticationInfos userInfos) {
        return new NavLinks[]{
                NavLinks.builder()
                        .navLinks(new NavLink[]{
                                NavLink.builder()
                                        .iconSource("home.svg")
                                        .url("home")
                                        .libelle("Accueil")
                                        .build(),
                                NavLink.builder()
                                        .iconSource("school.svg")
                                        .url("cours")
                                        .libelle("Mes cours")
                                        .build(),
                                NavLink.builder()
                                        .iconSource("conversation.svg")
                                        .url("conversation")
                                        .libelle("Mon suivi")
                                        .build(),
                        })
                        .build(),
                NavLinks.builder()
                        .navLinks(new NavLink[]{
                                NavLink.builder()
                                        .iconSource("profil.svg")
                                        .url("profil")
                                        .libelle("Profil")
                                        .build(),
                        })
                        .build(),
        };
    }
}
