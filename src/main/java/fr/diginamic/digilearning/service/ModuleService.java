package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.Formation;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.SousModule;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.FormationRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.validators.ModuleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.thymeleaf.model.IModel;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleValidator moduleValidator;
    private final FormationRepository formationRepository;
    private final SousModuleRepository sousModuleRepository;

    public Module updateNewPhoto(String fileName, Long idModule) {
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        module.setPhoto(fileName);
        return moduleRepository.save(module);
    }

    @Transactional
    public Module putFormation(Long idModule, String nomFormation) {
        Formation formation = formationRepository.findByNom(nomFormation)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        if(module.getFormations().contains(formation)){
            return module;
        }
        module.getFormations().add(formation);
        return moduleRepository.save(module);
    }

    @Transactional
    public Module deleteFormation(Long idFormation, Long idModule) {
        Formation formation = formationRepository.findById(idFormation)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        module.getFormations().remove(formation);
        return moduleRepository.save(module);
    }

    @Transactional
    public Formation deleteModuleFromFormation(Long idFormation, Long idModule){
        Formation formation = formationRepository.findById(idFormation)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        formation.getModules().remove(module);
        return formationRepository.save(formation);
    }
    @Transactional
    public Module deleteSousModule(Long idSmodule, Long idModule) {
        SousModule sousModule = sousModuleRepository.findById(idSmodule)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        module.getSousModules().remove(sousModule);
        return moduleRepository.save(module);
    }

    @Transactional
    public Module putSousModule(Long idModule, String smodule) {
        SousModule sousModule = sousModuleRepository.findByTitre(smodule)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        if(module.getSousModules().contains(sousModule)){
            return module;
        }
        module.getSousModules().add(sousModule);
        return moduleRepository.save(module);
    }

    public Module createNewModule() {
        Module module = Module.builder()
                .libelle("Nouveau module")
                .build();
        return moduleRepository.save(module);
    }

    @Transactional
    public Formation putModuleInFormation(Long idFormation, String nomModule) {
        Module module = moduleRepository.findByLibelle(nomModule)
                .orElseThrow(EntityNotFoundException::new);
        Formation formation = formationRepository.findById(idFormation)
                .orElseThrow(EntityNotFoundException::new);
        if(formation.getModules().contains(module)){
            return formation;
        }
        formation.getModules().add(module);
        return formationRepository.save(formation);
    }

    public SousModule updateNewPhotoSmodule(String fileName, Long idSmodule) {
        SousModule sousModule = sousModuleRepository.findById(idSmodule)
                .orElseThrow(EntityNotFoundException::new);
        sousModule.setPhoto(fileName);
        return  sousModuleRepository.save(sousModule);
    }

    public SousModule putModuleInSousModule(Long idSmodule, String nomModule) {
        SousModule sousModule = sousModuleRepository.findById(idSmodule)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findByLibelle(nomModule)
                .orElseThrow(EntityNotFoundException::new);
        if(sousModule.getModules().contains(module)){
            return sousModule;
        }
        sousModule.getModules().add(module);
        return sousModuleRepository.save(sousModule);
    }

    @Transactional
    public SousModule deleteModuleFromSousModule(Long idSmodule, Long idModule) {
        SousModule sousModule = sousModuleRepository.findById(idSmodule)
                .orElseThrow(EntityNotFoundException::new);
        Module module = moduleRepository.findById(idModule)
                .orElseThrow(EntityNotFoundException::new);
        sousModule.getModules().remove(module);
        return sousModuleRepository.save(sousModule);
    }

    public record ReponseChangementTitreSmodule(SousModule smodule, Optional<String> diagnostic){}

    public ReponseChangementTitreSmodule patchSmoduleTitre(String titre, Long idSmodule) {
        Optional<String> diagnostic = moduleValidator.validateModuleTitre(titre);
        if(diagnostic.isPresent()){
            return new ReponseChangementTitreSmodule(null, diagnostic);
        }
        SousModule smodule = sousModuleRepository.findById(idSmodule).orElseThrow(EntityNotFoundException::new);
        smodule.setTitre(titre);
        return new ReponseChangementTitreSmodule(sousModuleRepository.save(smodule), diagnostic);
    }

    public record ReponseChangementTitre(Module module, Optional<String> diagnostic){}
    public ReponseChangementTitre patchModuleTitre(String titre, Long idModule){
        Optional<String> diagnostic = moduleValidator.validateModuleTitre(titre);
        if(diagnostic.isPresent()){
            return new ReponseChangementTitre(null, diagnostic);
        }
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        module.setLibelle(titre);
        return new ReponseChangementTitre(moduleRepository.save(module), diagnostic);
    }
}
