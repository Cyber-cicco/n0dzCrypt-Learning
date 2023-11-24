package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.SousModuleInfosDto;
import fr.diginamic.digilearning.entities.SousModule;
import fr.diginamic.digilearning.exception.FunctionalException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.repository.SousModuleRepository;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final SousModuleRepository sousModuleRepository;

    public List<SousModule> findModulesByUtilisateur(String email, Long idModule){
        List<SousModule> sousModules = sousModuleRepository.findModulesByUtilisateur(email, idModule);
        if(sousModules.isEmpty()){
            throw new UnauthorizedException();
        }
        return sousModules;
    }
}
