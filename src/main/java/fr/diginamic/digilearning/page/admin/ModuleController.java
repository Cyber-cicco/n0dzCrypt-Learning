package fr.diginamic.digilearning.page.admin;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.DiagnoticHandler;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.admin.irrigator.ModuleIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.ModuleService;
import fr.diginamic.digilearning.service.PhotoService;
import fr.diginamic.digilearning.service.SessionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/modules")
public class ModuleController implements DiagnoticHandler {
    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final ModuleIrrigator moduleIrrigator;
    private final ModuleService moduleService;
    private final PhotoService photoService;
    private final SessionService sessionService;

    @GetMapping
    public String getModulesApi(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateBaseModule(model);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_MODULES);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/api")
    public String getModule(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateBaseModule(model);
        return Routes.ADR_ADMIN_MODULES;
    }

    @GetMapping("/details")
    public String getModuleDetails(Model model, @RequestParam("id") Long idModule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateModuleDetails(model, idModule, response);
        return Routes.ADR_ADMIN_MODULES_DETAILS;
    }

    @GetMapping("/formations-smodule")
    public String getFormationsForModule(Model model, @RequestParam("id") Long idModule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateFormations(model, idModule);
        return Routes.ADR_ADMIN_MODULE_FORMATION_MODAL;
    }

    @PatchMapping("titre")
    public String patchModuleTitre(Model model, @RequestParam("id") Long idModule, @ModelAttribute MessageDto titre, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        var changementResponse = moduleService.patchModuleTitre(titre.getMessage(), idModule);
        if(changementResponse.diagnostic().isPresent()){
            return handleSingleDiagnostic(model, response, changementResponse.diagnostic().get(), "error", Routes.ADR_FORM_ERROR);
        }
        moduleIrrigator.irrigateModuleDetails(model, idModule, response);
        return Routes.ADR_ADMIN_MODULES_TITRE;
    }

    @PostMapping("/photo")
    public String postNewPhoto(Model model, @RequestParam("id") Long idModule, @ModelAttribute("file") MultipartFile file, HttpServletResponse response) throws IOException {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        String fileName = photoService.uploadPhoto(file, "/public/modules/", userInfos);
        Module module = moduleService.updateNewPhoto(fileName, idModule);
        model.addAttribute("module", module);
        return Routes.ADR_ADMIN_MODULES_PHOTO;
    }

    public record FormationRequest(String formation){}
    @PutMapping("/formation")
    public String putNewFormation(Model model, @RequestParam("id") Long idModule, @ModelAttribute FormationRequest formationRequest, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.putFormation(idModule, formationRequest.formation());
        moduleIrrigator.irrigateDetailsFormation(model, module);
        return Routes.ADR_ADMIN_MODULE_FORMATION_DETAILS;
    }
    @DeleteMapping("/formation")
    public String deleteFormation(Model model, @RequestParam("idFormation") Long idFormation, @RequestParam("idModule") Long idModule, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.deleteFormation(idFormation, idModule);
        moduleIrrigator.irrigateDetailsFormation(model, module);
        return Routes.ADR_ADMIN_MODULE_FORMATION_DETAILS;
    }
}
