package fr.diginamic.digilearning.page.admin.irrigator;

import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.FormationRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModuleIrrigator {

    private final ModuleRepository moduleRepository;
    private final FormationRepository formationRepository;
    private final SousModuleRepository sousModuleRepository;
    public void irrigateBaseModule(Model model){
        List<Formation> formations = formationRepository.findAllActive();
        List<Module> modules = moduleRepository.findAll();
        Set<SousModule> sousModules = modules
                .stream()
                .flatMap(module -> module.getSousModules().stream())
                .collect(Collectors.toUnmodifiableSet());
        List<CoursElement> elements = new ArrayList<>();
        elements.addAll(formations);
        elements.addAll(modules);
        elements.addAll(sousModules);
        model.addAttribute("currDate", LocalDate.now());
        model.addAttribute("elements", elements);
        model.addAttribute("formations", formations);
        model.addAttribute("modules", modules);
        model.addAttribute("smodules", sousModules);
    }

    public void irrigateModuleDetails(Model model, Long idModule, HttpServletResponse response) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("module", module);
    }

    public void irrigateFormations(Model model, Long idModule) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        List<Formation> formations = formationRepository.findAllActive();
        model.addAttribute("module", module);
        model.addAttribute("formations", formations);
    }

    public void irrigateDetailsFormation(Model model, Module module) {
        model.addAttribute("module", module);
    }

    public void irrigateSousModules(Model model, Long idModule) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        List<SousModule> sousModules = sousModuleRepository.findAll();
        model.addAttribute("module", module);
        model.addAttribute("smodules", sousModules);
    }

    public void irrigateDetailsSousModule(Model model, Module module) {
        model.addAttribute("module", module);
    }
}
