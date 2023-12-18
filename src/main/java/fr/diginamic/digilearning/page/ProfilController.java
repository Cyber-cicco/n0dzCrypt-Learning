package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.page.irrigator.ProfilIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("profil")
public class ProfilController {

    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final ProfilIrrigator profilIrrigator;

    @GetMapping("/api")
    public String getProfilApi( Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        profilIrrigator.irrigateBaseAttributes(userInfos, model, response);
        return Routes.ADR_PROFIL;
    }
    @GetMapping
    public String getProfil( Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        profilIrrigator.irrigateBaseAttributes(userInfos, model, response);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_PROFIL);
        return Routes.ADR_BASE_LAYOUT;
    }


    @PatchMapping("/finished/progress")
    public String patchFinishedWithProgress( @RequestParam("id") Long idCours, Model model) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        profilIrrigator.irrigatePatchProgress(model, userInfos, idCours);
        return Routes.ADR_PROFIL_PROGRES;
    }
}
