package fr.diginamic.digilearning.page.admin;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.irrigator.ProfilIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.ForumService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/utilisateur")
public class ApprenantsController {

    private final AuthenticationService authenticationService;
    private final ProfilIrrigator profilIrrigator;
    private final ForumService forumService;

    @GetMapping
    public String getApprenants(Model model, @RequestParam("id") Long idStagiaire, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        profilIrrigator.irrigateApprenantAdmin(model, idStagiaire, userInfos);
        return Routes.ADR_ADMIN_APPRENANTS_DETAILS;
    }

    @PutMapping("/droitsforum")
    public String changerDroits(Model model, @RequestParam("id") Long idStagiaire, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        boolean banned = forumService.revoquerDroit(idStagiaire);
        model.addAttribute("id", idStagiaire);
        model.addAttribute("banned", banned);
        return Routes.ADR_ADMIN_APPRENANTS_DETAILS_BAN;
    }


}
