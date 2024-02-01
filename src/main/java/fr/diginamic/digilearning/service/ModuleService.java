package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.validators.ModuleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.model.IModel;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleValidator moduleValidator;

    public Module updateNewPhoto(String fileName, Long idModule) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        module.setPhoto(fileName);
        return moduleRepository.save(module);
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
