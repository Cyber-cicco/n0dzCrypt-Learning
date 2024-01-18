package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.FunctionalException;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final CoursRepository coursRepository;

    public Integer getProgression(Utilisateur utilisateur, List<CoursDto> bookmarked) {
        Integer nbCours = coursRepository.getNbCours(utilisateur);
        if(nbCours != 0) return (int) Math.ceil(((double) bookmarked.size() / Double.valueOf(nbCours)) * 100d);
        return 101;
    }

    public Integer getProgressionJournee(Long id) {
        List<CoursDto> coursDtos = coursRepository.getPrevusCeJour(id)
                .stream()
                .map(res -> SqlResultMapper.mapToObject(CoursDto.class, res))
                .toList();
        long nbCours = coursDtos.size();
        long nbCoursTermines = coursDtos
                .stream()
                .filter(coursDto -> coursDto.getFinished() != null && coursDto.getFinished())
                .count();
        if(nbCours != 0) return (int) Math.ceil(((double) nbCoursTermines / (double) nbCours) * 100d);
        return 100;
    }

    public static class Progression {
        public Integer nbCours;
        public Integer nbCoursTermines;

        public Integer getPourcentageCompletion(){
            if(nbCours != 0) return (int) Math.ceil((Double.valueOf(nbCoursTermines) / Double.valueOf(nbCours)) * 100d);
            return 101;
        }
    }
    public Integer getProgression(Long id){
        return coursRepository.getProgression(id)
                .stream()
                .map(res -> SqlResultMapper.mapToObject(Progression.class, res).getPourcentageCompletion())
                .findFirst()
                .orElseThrow(FunctionalException::new);
    }
}

