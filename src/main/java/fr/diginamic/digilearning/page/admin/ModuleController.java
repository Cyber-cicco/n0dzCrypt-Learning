package fr.diginamic.digilearning.page.admin;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.Formation;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.SousModule;
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
    @GetMapping("formation/details")
    public String getFormationDetails(Model model, @RequestParam("id") Long idFormation, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateDetailsPageFormation(model, idFormation);
        return Routes.ADR_ADMIN_MODULES_FORMATION_DETAILS;
    }
    @GetMapping("smodule/details")
    public String getSmoduleDetails(Model model, @RequestParam("id") Long idSmodule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateDetailsPageSmodule(model, idSmodule, response);
        return Routes.ADR_ADMIN_MODULES_SMODULE_DETAILS;
    }

    @GetMapping("/formations-module")
    public String getFormationsForModule(Model model, @RequestParam("id") Long idModule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateFormations(model, idModule);
        return Routes.ADR_ADMIN_MODULE_FORMATION_MODAL;
    }

    @GetMapping("/smodules-module")
    public String getSousModulesForModule(Model model, @RequestParam("id") Long idModule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateSousModules(model, idModule);
        return Routes.ADR_ADMIN_MODULE_SMODULES_MODAL;
    }
    @GetMapping("/smodule/modules-modal")
    public String getModulesForSmodule(Model model, @RequestParam("id") Long idSmodule, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateModulesModalSmodule(model, idSmodule);
        return Routes.ADR_ADMIN_MODULE_SMODULES_MODULES_MODAL;
    }
    @GetMapping("/formation/modules-modal")
    public String getModulesForFormationModal(Model model, @RequestParam("id") Long idFormation, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        moduleIrrigator.irrigateModulesModalFormation(model, idFormation);
        return Routes.ADR_ADMIN_MODULE_FORMATION_MODULES_MODAL;
    }
    @PatchMapping("smodule/titre")
    public String patchSmoduleTitre(Model model, @RequestParam("id") Long idSmodule, @ModelAttribute MessageDto titre, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        var changementResponse = moduleService.patchSmoduleTitre(titre.getMessage(), idSmodule);
        if(changementResponse.diagnostic().isPresent()){
            return handleSingleDiagnostic(model, response, changementResponse.diagnostic().get(), "error", Routes.ADR_FORM_ERROR);
        }
        moduleIrrigator.irrigateSmoduleDetails(model, changementResponse.smodule());
        return Routes.ADR_ADMIN_MODULES_SMODULE_TITRE;
    }
    @PatchMapping("titre")
    public String patchModuleTitre(Model model, @RequestParam("id") Long idModule, @ModelAttribute MessageDto titre, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        var changementResponse = moduleService.patchModuleTitre(titre.getMessage(), idModule);
        if(changementResponse.diagnostic().isPresent()){
            return handleSingleDiagnostic(model, response, changementResponse.diagnostic().get(), "error", Routes.ADR_FORM_ERROR);
        }
        moduleIrrigator.irrigateModuleDetails(model, changementResponse.module(), response);
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

    @PostMapping("/photo/smodule")
    public String postNewPhotoSmodule(Model model, @RequestParam("id") Long idSmodule, @ModelAttribute("file") MultipartFile file, HttpServletResponse response) throws IOException {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        String fileName = photoService.uploadPhoto(file, "/public/smodules/", userInfos);
        SousModule smodule = moduleService.updateNewPhotoSmodule(fileName, idSmodule);
        model.addAttribute("smodule", smodule);
        return Routes.ADR_ADMIN_MODULES_SMODULE_PHOTO;
    }
    @PostMapping
    public String postNewModule(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.createNewModule();
        model.addAttribute("module", module);
        return Routes.ADR_ADMIN_MODULE_LISTE_ITEM;
    }

    @PostMapping("/smodule")
    public String postNewSousModule(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        SousModule smodule = moduleService.createNewSousModule();
        model.addAttribute("smodule", smodule);
        return Routes.ADR_ADMIN_SMODULE_LISTE_ITEM;
    }
    public record FormationRequest(String formation){}
    @PutMapping("/formation")
    public String putNewFormation(Model model, @RequestParam("id") Long idModule, @ModelAttribute FormationRequest formationRequest, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.putFormation(idModule, formationRequest.formation());
        moduleIrrigator.irrigateDetailsSousModule(model, module);
        return Routes.ADR_ADMIN_MODULE_FORMATION_DETAILS;
    }
    private record ModuleRequest(String module){}
    @PutMapping("/formation/module")
    public String putNewModuleForFormation(Model model, @RequestParam("id") Long idFormation, @ModelAttribute ModuleRequest moduleRequest, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Formation formation = moduleService.putModuleInFormation(idFormation, moduleRequest.module);
        moduleIrrigator.irrigateDetailsModulesForFormation(model, formation);
        return Routes.ADR_ADMIN_MODULE_FORMATION_MODULE_DETAILS;
    }
    @PutMapping("/smodule/module")
    public String putNewModuleForSousModule(Model model, @RequestParam("id") Long idSmodule, @ModelAttribute ModuleRequest moduleRequest, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        SousModule smodule = moduleService.putModuleInSousModule(idSmodule, moduleRequest.module);
        moduleIrrigator.irrigateDetailsModulesForSousModule(model, smodule);
        return Routes.ADR_ADMIN_MODULE_SMODULES_MODULE_DETAILS;
    }
    private record SModuleRequest(String smodule){}
    @PutMapping("/smodule")
    public String putNewSModule(Model model, @RequestParam("id") Long idModule, @ModelAttribute SModuleRequest formationRequest, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.putSousModule(idModule, formationRequest.smodule);
        moduleIrrigator.irrigateDetailsFormation(model, module);
        return Routes.ADR_ADMIN_MODULE_SMODULES_DETAILS;
    }
    @DeleteMapping("/formation/module")
    public String deleteModuleFromFormation(Model model, @RequestParam("idFormation") Long idFormation, @RequestParam("idModule") Long idModule, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Formation formation = moduleService.deleteModuleFromFormation(idFormation, idModule);
        moduleIrrigator.irrigateDetailsModulesForFormation(model, formation);
        return Routes.ADR_ADMIN_MODULE_FORMATION_MODULE_DETAILS;
    }
    @DeleteMapping("/smodule/module")
    public String deleteModuleFromSousModule(Model model, @RequestParam("idSmodule") Long idSmodule, @RequestParam("idModule") Long idModule, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        SousModule sousModule = moduleService.deleteModuleFromSousModule(idSmodule, idModule);
        moduleIrrigator.irrigateDetailsModulesForSousModule(model, sousModule);
        return Routes.ADR_ADMIN_MODULE_SMODULES_MODULE_DETAILS;
    }
    @DeleteMapping("/formation")
    public String deleteFormation(Model model, @RequestParam("idFormation") Long idFormation, @RequestParam("idModule") Long idModule, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.deleteFormation(idFormation, idModule);
        moduleIrrigator.irrigateDetailsFormation(model, module);
        return Routes.ADR_ADMIN_MODULE_FORMATION_DETAILS;
    }
    @DeleteMapping("/smodule")
    public String deleteSousModule(Model model, @RequestParam("idSmodule") Long idSmodule, @RequestParam("idModule") Long idModule, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Module module = moduleService.deleteSousModule(idSmodule, idModule);
        moduleIrrigator.irrigateDetailsSousModule(model, module);
        return Routes.ADR_ADMIN_MODULE_SMODULES_DETAILS;
    }
}
