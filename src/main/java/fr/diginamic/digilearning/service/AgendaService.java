package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.*;
import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.CoursSession;
import fr.diginamic.digilearning.entities.FlagCours;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.repository.*;
import fr.diginamic.digilearning.service.enums.DateOption;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CoursSessionRepository coursSessionRepository;
    private final SessionRepository sessionRepository;

    public AgendaService(
            CoursRepository coursRepository,
            UtilisateurRepository utilisateurRepository,
            FlagCoursRepository flagCoursRepository,
            CoursSessionRepository coursSessionRepository,
            SessionRepository sessionRepository
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
        this.coursSessionRepository = coursSessionRepository;
        this.sessionRepository = sessionRepository;
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
    public Map<LocalDateTime, CoursAdminDto> getHourMapAdmin(List<CoursAdminDto> coursPrevus) {
        Map<LocalDateTime, CoursAdminDto> mapDateToCours = new HashMap<>();
        coursPrevus.forEach(coursDto -> mapDateToCours.put(coursDto.getDatePrevue(), coursDto));
        return mapDateToCours;
    }

    public Optional<CoursDto> putCoursInDate(AuthenticationInfos userInfos, LocalDateTime datePrevue, Long coursId) {
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
            ) {
                return Optional.empty();
            }
        }
        flagCoursRepository.save(flagCours);
        return Optional.ofNullable(CoursDto.builder()
                .id(cours.getId())
                .titre(cours.getTitre())
                .ordre(cours.getOrdre())
                .difficulte(cours.getDifficulte())
                .dureeEstimee(cours.getDureeEstimee())
                .boomarked(flagCours.getBoomarked())
                .datePrevue(flagCours.getDatePrevue())
                .finished(flagCours.getFinished())
                .liked(flagCours.getLiked())
                .build());
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
    @Transactional
    public void removeCoursFromAgendaForSession(AuthenticationInfos userInfos, Long idCours, Long idSession) {
        List<Utilisateur> stagiairesSession = utilisateurRepository.findBySession(idSession);
        List<FlagCours> flagCours = stagiairesSession
                .stream()
                .map(utilisateur -> flagCoursRepository.findByStagiaire_IdAndCours_Id(utilisateur.getId(), idCours)
                        .orElseThrow(EntityNotFoundException::new))
                .toList();
        for (FlagCours flagCour : flagCours) {
            flagCour.setDatePrevue(null);
        }
        flagCoursRepository.saveAll(flagCours);
        CoursSession coursSession = coursSessionRepository.findByCours_IdAndSession_Id(idCours, idSession)
                .orElseThrow(EntityNotFoundException::new);
        coursSession.setDatePrevue(null);
        coursSessionRepository.save(coursSession);
    }

    public List<CoursDto> getCoursForAgenda(Long id) {
        return coursRepository.getAllCoursForUser(id)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursDto.class, c))
                .sorted(Comparator.comparing(CoursDto::getDatePrevue, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();
    }

    public List<CoursAdminDto> getCoursForAdmin(Long idSession) {
        return coursRepository.getAllCoursForSessionAdmin(idSession)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursAdminDto.class, c))
                .sorted(Comparator.comparing(CoursAdminDto::getDatePrevue, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();
    }

    public List<CoursAdminDto> getCoursPrevusForSession(Long idSession) {
        return coursRepository.getCoursPrevusForSession(idSession)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursAdminDto.class, c))
                .toList();
    }

    @Transactional
    public Optional<CoursAdminDto> prevoirCoursForSession(AuthenticationInfos userInfos, LocalDateTime temps, Long idCours, Long idSession) {
        CoursSession coursSession = coursSessionRepository.findByCours_IdAndSession_Id(idCours, idSession)
                .orElseGet(() ->
                    CoursSession
                            .builder()
                            .cours(coursRepository.findById(idCours).orElseThrow(EntityNotFoundException::new))
                            .session(sessionRepository.findById(idSession).orElseThrow(EntityNotFoundException::new))
                            .build()
                );
        coursSession.setDatePrevue(temps);
        List<CoursSession> coursPrevusCeJour = coursSessionRepository
                .findByDatePrevueBetweenAndSession_Id(
                        LocalDateTime.of(temps.getYear(), temps.getMonth(), temps.getDayOfMonth(), 0, 0, 0),
                        LocalDateTime.of(temps.getYear(), temps.getMonth(), temps.getDayOfMonth(), 23, 0, 0),
                        idSession);
        for (CoursSession cours : coursPrevusCeJour) {
            if(
                    (temps.isEqual(cours.getDatePrevue())
                            || (temps.isBefore(cours.getDatePrevue()) && temps.plusHours(cours.getCours().getDureeEstimee()).isAfter(cours.getDatePrevue()))
                            || (temps.isAfter(cours.getDatePrevue()) && cours.getDatePrevue().plusHours(cours.getCours().getDureeEstimee()).isAfter(temps)))
                            && !cours.getId().equals(coursSession.getId())
            ) {
                return Optional.empty();
            }
        }
        coursSessionRepository.save(coursSession);
        flagCoursRepository.setToNullForDateAndSession(temps, idCours, idSession);
        List<Utilisateur> stagiairesSession = utilisateurRepository.findBySession(idSession);
        List<FlagCours> flagCours = stagiairesSession.stream()
                .map(utilisateur -> flagCoursRepository
                        .findByStagiaire_IdAndCours_Id(utilisateur.getId(), idCours)
                        .orElseGet(() -> flagCoursRepository.save(FlagCours.builder()
                                        .finished(false)
                                        .liked(false)
                                        .cours(coursRepository.findById(idCours).orElseThrow(EntityNotFoundException::new))
                                        .stagiaire(utilisateur)
                                .build())))
                .toList();
        for (FlagCours flagCour : flagCours) {
            flagCour.setDatePrevue(temps);
            flagCour.setMandatory(true);
        }
        flagCoursRepository.saveAll(flagCours);
        return Optional.of(
                CoursAdminDto.builder()
                        .id(coursSession.getCours().getId())
                        .datePrevue(temps)
                        .titre(coursSession.getCours().getTitre())
                        .difficulte(coursSession.getCours().getDifficulte())
                        .dureeEstimee(coursSession.getCours().getDureeEstimee())
                        .build()
        );
    }
}
