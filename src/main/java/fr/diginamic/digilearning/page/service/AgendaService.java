package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.CalendarInfos;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.dto.DayInfos;
import fr.diginamic.digilearning.dto.HourInfos;
import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.page.service.enums.DateOption;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.FlagCoursRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class AgendaService {

    private final Map<String, String> englishWeekDayToFr = new HashMap<>();
    private final Map<String, String> englishMonthToFr = new HashMap<>();
    private final CoursRepository coursRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final FlagCoursRepository flagCoursRepository;

    public AgendaService(
            CoursRepository coursRepository,
            UtilisateurRepository utilisateurRepository,
            FlagCoursRepository flagCoursRepository
    ) {
        englishWeekDayToFr.put("MONDAY", "Lundi");
        englishWeekDayToFr.put("TUESDAY", "Mardi");
        englishWeekDayToFr.put("WEDNESDAY", "Mercredi");
        englishWeekDayToFr.put("THURSDAY", "Jeudi");
        englishWeekDayToFr.put("FRIDAY", "Vendredi");
        englishWeekDayToFr.put("SATURDAY", "Samedi");
        englishWeekDayToFr.put("SUNDAY", "Dimanche");

        englishMonthToFr.put("JANUARY", "Janvier");
        englishMonthToFr.put("FEBRUARY", "Févirier");
        englishMonthToFr.put("MARCH", "Mars");
        englishMonthToFr.put("APRIL", "Avril");
        englishMonthToFr.put("MAY", "Mai");
        englishMonthToFr.put("JUNE", "Juin");
        englishMonthToFr.put("JULY", "Juillet");
        englishMonthToFr.put("AUGUST", "Aôut");
        englishMonthToFr.put("SEPTEMBER", "Septembre");
        englishMonthToFr.put("OCTOBER", "Octobre");
        englishMonthToFr.put("NOVEMBER", "Novembre");
        englishMonthToFr.put("DECEMBER", "Décembre");
        this.coursRepository = coursRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.flagCoursRepository = flagCoursRepository;
    }

    public List<HourInfos> getHeuresJournee() {
        List<HourInfos> heures = new ArrayList<>(24);
        for (int i = 8; i < 24; i++) {
            heures.add(
                    new HourInfos(
                            String.valueOf((i < 10) ? "0" + i : i),
                            LocalTime.of(i, 0)
                    )
            );
        }
        return heures;
    }

    public CalendarInfos getCalendarInfos(LocalDate date) {
        return new CalendarInfos(
                englishMonthToFr.get(date.getMonth().toString()) + " " + date.getYear(),
                DateOption.SEMAINE_OUVREE
        );
    }

    public List<DayInfos> getDayInfosForWeek(LocalDate date) {
        List<DayInfos> dayInfos = new ArrayList<>();
        date = date.minusDays(date.getDayOfWeek().getValue() - 1);
        while (date.getDayOfWeek().getValue() != 6){
            dayInfos.add(new DayInfos(
                    date,
                    date.getDayOfMonth(),
                    englishWeekDayToFr.get(date.getDayOfWeek().toString())
            ));
            date = date.plusDays(1);
        }
        return dayInfos;
    }

    public List<CoursDto> getCoursPrevus(Long id) {
        return coursRepository.getCoursPrevus(id)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursDto.class, c))
                .toList();
    }

    public Map<LocalDateTime, CoursDto> getHourMap(List<CoursDto> coursPrevus) {
        Map<LocalDateTime, CoursDto> mapDateToCours = new HashMap<>();
        coursPrevus.forEach(coursDto -> mapDateToCours.put(coursDto.getDatePrevue(), coursDto));
        return mapDateToCours;
    }

    public void putCoursInDate(AuthenticationInfos userInfos, LocalDateTime datePrevue, Long coursId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        Cours cours = coursRepository.findByUserAndId(utilisateur.getId(), coursId)
                .orElseThrow(UnauthorizedException::new);
        FlagCours flagCours = flagCoursRepository.findByCoursAndStagiaire(cours, utilisateur).orElseGet(() -> FlagCours.builder()
                .finished(false)
                .boomarked(false)
                .stagiaire(utilisateur)
                .cours(cours)
                .liked(false)
                .datePrevue(datePrevue)
                .build());
        flagCours.setDatePrevue(datePrevue);
        List<FlagCours> coursPrevusCeJour = flagCoursRepository
                .findByDatePrevueBetweenAndStagiaire_Id(
                        LocalDateTime.of(datePrevue.getYear(), datePrevue.getMonth(), datePrevue.getDayOfMonth(), 0, 0, 0),
                        LocalDateTime.of(datePrevue.getYear(), datePrevue.getMonth(), datePrevue.getDayOfMonth(), 23, 0, 0),
                        utilisateur.getId());
        for (FlagCours flagCours1 : coursPrevusCeJour) {
            if(
                    (datePrevue.isEqual(flagCours1.getDatePrevue())
                            || (datePrevue.isBefore(flagCours1.getDatePrevue()) && datePrevue.plusHours(cours.getDureeEstimee()).isAfter(flagCours1.getDatePrevue()))
                            || (datePrevue.isAfter(flagCours1.getDatePrevue()) && flagCours1.getDatePrevue().plusHours(flagCours1.getCours().getDureeEstimee()).isAfter(datePrevue)))
                            && !flagCours1.getId().equals(flagCours.getId())
            ) return;
        }
        flagCoursRepository.save(flagCours);
    }

    public void removeCoursFromAgenda(AuthenticationInfos userInfos, Long coursId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        Cours cours = coursRepository.findByUserAndId(utilisateur.getId(), coursId)
                .orElseThrow(UnauthorizedException::new);
        FlagCours flagCours = flagCoursRepository.findByCoursAndStagiaire(cours, utilisateur).orElseThrow(UnauthorizedException::new);
        flagCours.setDatePrevue(null);
        flagCoursRepository.save(flagCours);
    }

    public List<CoursDto> getCoursForAgenda(Long id) {
        return coursRepository.getAllCoursForUser(id)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursDto.class, c))
                .sorted(Comparator.comparing(CoursDto::getDatePrevue, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();
    }
}
