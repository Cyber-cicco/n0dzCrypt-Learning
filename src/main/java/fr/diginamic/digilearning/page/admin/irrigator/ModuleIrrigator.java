package fr.diginamic.digilearning.page.admin.irrigator;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.CoursElement;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.SousModule;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.ModuleRepository;
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
    public void irrigateBaseModule(Model model){
        List<Module> modules = moduleRepository.findAll();
        Set<SousModule> sousModules = modules
                .stream()
                .flatMap(module -> module.getSousModules().stream())
                .collect(Collectors.toUnmodifiableSet());
        Set<Cours> cours = sousModules
                .stream()
                .flatMap(sousModule -> sousModule.getCours().stream())
                .collect(Collectors.toUnmodifiableSet());
        List<CoursElement> elements = new ArrayList<>();
        elements.addAll(modules);
        elements.addAll(sousModules);
        elements.addAll(cours);
        model.addAttribute("currDate", LocalDate.now());
        model.addAttribute("elements", elements);
        model.addAttribute("modules", modules);
        model.addAttribute("smodules", sousModules);
        model.addAttribute("cours", cours);
        model.addAttribute("m", modules.get(0));
    }

    public void irrigateModuleDetails(Model model, Long idModule, HttpServletResponse response) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("module", module);
    }
}
