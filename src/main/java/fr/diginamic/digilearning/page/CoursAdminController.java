package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.ChapitreDto;
import fr.diginamic.digilearning.dto.ContenuChapitreDto;
import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.Chapitre;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.irrigator.ChapitreIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.page.service.CoursService;
import fr.diginamic.digilearning.page.service.PhotoService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cours/admin")
public class CoursAdminController {

    private final CoursService coursService;
    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final ChapitreIrrigator chapitreIrrigator;
    private final PhotoService photoService;

    @GetMapping("/chapitre/editer/api")
    public String getAdminPanelChapitreApi(Model model, @RequestParam("id") Long idChapitre) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateAdminChapitre(model, userInfos, idChapitre);
        return Routes.ADR_ADMIN_CHAPITRE;
    }
    @GetMapping("/chapitre/editer")
    public String getAdminPanelChapitre(Model model, @RequestParam("id") Long idChapitre) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        chapitreIrrigator.irrigateAdminChapitre(model, userInfos, idChapitre);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_CHAPITRE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @PostMapping("/description")
    public String editerDescription(@RequestParam("id") Long idCours, @ModelAttribute MessageDto descriptionCours) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        coursService.editerDescription(idCours, descriptionCours, userInfos);
        return Routes.ADR_FORM_ERROR;
    }

    @PostMapping("/chapitre")
    public String creerChapitre(Model model, @RequestParam("id") Long idCours, @ModelAttribute ChapitreDto chapitreDto, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Chapitre chapitre = coursService.createNewChapitre(userInfos, idCours, chapitreDto);
        chapitreIrrigator.irrigateAdminChapitre(model, userInfos, chapitre.getId());
        response.setHeader("HX-Push-Url", "/cours/admin/chapitre/editer?id=" + chapitre.getId());
        return Routes.ADR_ADMIN_CHAPITRE;
    }
    @PostMapping("/photo")
    public ResponseEntity<?> ajouterPhoto(Model model, @ModelAttribute("file") MultipartFile file, HttpServletResponse response) throws IOException {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), response);
        String fileName = photoService.uploadPhoto(file, userInfos);
        return ResponseEntity.ok(Map.of("name", fileName));
    }

    @PostMapping("/chapitre/contenu")
    public String editerChapitre(Model model, @RequestParam("id") Long idChapitre, @ModelAttribute ContenuChapitreDto contenuChapitreDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre chapitre = coursService.updateContenu(userInfos, idChapitre, contenuChapitreDto);
        model.addAttribute("content", coursService.getHtmlFromChapitreMarkdown(chapitre.getContenuNonPublie()));
        return Routes.ADR_COURS_CONTENT;
    }

    @PostMapping("/chapitre/publier")
    public String publierChapitre(Model model, @RequestParam("id") Long idChapitre, @ModelAttribute ContenuChapitreDto contenuChapitreDto, HttpServletResponse reponse) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.rolesMustMatchOne(userInfos.getRoles(), List.of(TypeRole.ROLE_FORMATEUR, TypeRole.ROLE_ADMINISTRATEUR), reponse);
        Chapitre chapitre = coursService.publierContenu(userInfos, idChapitre, contenuChapitreDto);
        model.addAttribute("content", "La version de votre cours est publiée");
        return Routes.ADR_MESSAGE;
    }
}
