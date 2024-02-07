package fr.diginamic.digilearning.components.service;

import fr.diginamic.digilearning.components.elements.NavLink;
import fr.diginamic.digilearning.components.elements.NavLinks;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NavBarService {

    /**
     * Permet de récupérer un array d'objets représentant les différents liens de la navbar
     * en fonction des autorisations que l'on a.
     * @param userInfos les informations de l'utilisateur authentifié
     * @return la liste des liens de la navbar en fonction du role de l'utilisateur
     */
    public NavLinks[] getLinks(AuthenticationInfos userInfos) {
        if(userInfos.isAdministrateur()){
            return new NavLinks[]{
                    NavLinks.builder()
                            .navLinks(new NavLink[]{
                                    NavLink.builder()
                                            .iconSource("svg/icons/home")
                                            .url("home")
                                            .libelle("Accueil")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/school")
                                            .url("cours/admin")
                                            .libelle("Mes cours")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/admin")
                                            .url("admin")
                                            .libelle("Administration")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/conversation")
                                            .url("admin/conversation")
                                            .libelle("Mes messages")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/forum")
                                            .url("forum")
                                            .libelle("Forum")
                                            .build(),
                            })
                            .build(),
                    NavLinks.builder()
                            .navLinks(new NavLink[]{})
                            .build(),
            };
        } else if (userInfos.isFormateur()){
            return new NavLinks[]{
                    NavLinks.builder()
                            .navLinks(new NavLink[]{
                                    NavLink.builder()
                                            .iconSource("svg/icons/home")
                                            .url("home")
                                            .libelle("Accueil")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/school")
                                            .url("cours/admin")
                                            .libelle("Mes cours")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/conversation")
                                            .url("conversation/admin")
                                            .libelle("Mes messages")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/forum")
                                            .url("forum")
                                            .libelle("Forum")
                                            .build(),
                            })
                            .build(),
            };
        } else if(userInfos.isStagiaire()) {
            return new NavLinks[]{
                    NavLinks.builder()
                            .navLinks(new NavLink[]{
                                    NavLink.builder()
                                            .iconSource("svg/icons/home")
                                            .url("home")
                                            .libelle("Accueil")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/school")
                                            .url("cours")
                                            .libelle("Mes cours")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/calendar")
                                            .url("agenda")
                                            .libelle("Mon agenda")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/conversation")
                                            .url("conversation/stagiaire")
                                            .libelle("Mon suivi")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/forum")
                                            .url("forum")
                                            .libelle("Forum")
                                            .build(),
                            })
                            .build(),
                    NavLinks.builder()
                            .navLinks(new NavLink[]{
                                    NavLink.builder()
                                            .iconSource("svg/icons/profil")
                                            .url("profil")
                                            .libelle("Profil")
                                            .build(),
                            })
                            .build(),
            };
        } else {
            return new NavLinks[]{
                    NavLinks.builder()
                            .navLinks(new NavLink[]{
                                    NavLink.builder()
                                            .iconSource("svg/icons/home")
                                            .url("home")
                                            .libelle("Accueil")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/school")
                                            .url("cours")
                                            .libelle("Cours disponibles")
                                            .build(),
                                    NavLink.builder()
                                            .iconSource("svg/icons/forum")
                                            .url("forum")
                                            .libelle("Consulter le forum")
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

