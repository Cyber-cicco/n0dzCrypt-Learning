package fr.diginamic.digilearning.components.service;

import fr.diginamic.digilearning.components.elements.NavLink;
import fr.diginamic.digilearning.components.elements.NavLinks;
import fr.diginamic.digilearning.entities.enums.RoleEnum;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NavBarService {

    public NavLinks[] getLinks(AuthenticationInfos userInfos) {
        if(userInfos.getRoles().contains(RoleEnum.ROLE_STAGIAIRE.getLibelle())){
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
                                            .url("conversation/stagiaire")
                                            .libelle("Mon suivi")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("forum.svg")
                                            .url("forum")
                                            .libelle("Forum")
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
        } else if(userInfos.getRoles().contains(RoleEnum.ROLE_FORMATEUR.getLibelle())) {
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
                                            .libelle("Gérer des cours")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("conversation.svg")
                                            .url("conversation")
                                            .libelle("Mon suivi")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("forum.svg")
                                            .url("forum")
                                            .libelle("Forum")
                                            .build(),
                            })
                            .build(),
                    NavLinks.builder()
                            .navLinks(new NavLink[]{})
                            .build(),
            };
        } else {
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
                                            .libelle("Gérer des cours")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("forum.svg")
                                            .url("forum")
                                            .libelle("Forum")
                                            .build(),
                            })
                            .build(),
                    NavLinks.builder()
                            .navLinks(new NavLink[]{})
                            .build(),
            };
        }
    }
}
