package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.DTO.CoursAdminDto;
import fr.diginamic.digilearning.DTO.CoursDto;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.service.AgendaService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Irrigateur du model donné par le controleur
 * HyperMédia de l'agenda
 */
@Service
@RequiredArgsConstructor
public class AgendaIrrigator {
    private final AgendaService agendaService;
    private final CoursRepository coursRepository;
    private final DateUtil dateUtil;

    /***
     * Irrigue le model avec les informations nécessaires à la construction de la page de l'agenda et de ses fragments
     * Donne les liens de la navbar en fonction du rôle de l'utilisateur.
     * Donne les cours de l'utilisateur triées pour sa liste de cours
     * Irrigue la partie spécifique au calendrier
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param date représentation de la date à laquelle l'utilisateur veut voir le calendrire de la semaine
     */
    public void irrigateBaseModel(AuthenticationInfos userInfos, Model model, LocalDate date) {
        model.addAttribute("cours", agendaService.getCoursForAgenda(userInfos.getId()));
        model.addAttribute("calendar", Routes.ADR_AGENDA_BODY);
        irrigateCalendar(model, date, userInfos);
    }

    public void irrigateCoursPrevus(AuthenticationInfos userInfos, Model model){
        model.addAttribute("cours", agendaService.getCoursForAgenda(userInfos.getId()));
    }

    public void irrigateCoursAdmin(Model model, Long idSession) {
        List<CoursAdminDto> cours = agendaService.getCoursForAdmin(idSession);
        model.addAttribute("cours", cours);
    }

    /**
     * Irrigue le modèle d'un cours dans le calendrier
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param temps la date à laquelle le cours est prévu
     * @param cours le cours prévu avec ses flags associés
     */
    public void irrigateCoursOnCalendar(AuthenticationInfos userInfos, Model model, LocalDateTime temps, CoursDto cours) {
        model.addAttribute("id", cours.getId());
        model.addAttribute("date", temps);
        model.addAttribute("dureeEstimee", cours.getDureeEstimee());
        model.addAttribute("cours", cours);
    }

    public void irrigateCoursAdminOnCalendar(AuthenticationInfos userInfos, Model model, LocalDateTime temps, CoursAdminDto cours){
        model.addAttribute("id", cours.getId());
        model.addAttribute("date", temps);
        model.addAttribute("dureeEstimee", cours.getDureeEstimee());
        model.addAttribute("cours", cours);
    }

    /**
     * Irrigue le model avec les informations nécessaires au fait de créer le calendrier
     * Donne le mois courant
     * Donne les informations nécessaires au fait d'afficher chaque jour de la semaine pour la date donnée
     * Donne la date sélectionnée
     * Donne les heures de la journée
     * Donne les cours prévus associés aux heures de la journée
     * Donne un utilitaire permettant de gérer les dates dans le template
     * Donne une date pour la semaine précédente
     * Donne une date pour la semaine suivante
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param date représentation de la date à laquelle l'utilisateur veut voir le calendrire de la semaine
     */
    private void irrigateCalendar(Model model, LocalDate date, AuthenticationInfos userInfos){
        List<CoursDto> coursPrevus = agendaService.getCoursPrevus(userInfos.getId());
        model.addAttribute("cal", agendaService.getCalendarInfos(date));
        model.addAttribute("week", agendaService.getDayInfosForWeek(date));
        model.addAttribute("dateValue", date.toString());
        model.addAttribute("hours", agendaService.getHeuresJournee());
        model.addAttribute("hourMap", agendaService.getHourMap(coursPrevus));
        model.addAttribute("dateUtil", dateUtil);
        model.addAttribute("coursPrevus", coursPrevus);
        model.addAttribute("_user", userInfos);
        model.addAttribute("prev", date.minusWeeks(1).toString());
        model.addAttribute("next", date.plusWeeks(1).toString());
    }

    public void irrigateAdminCalendar(Model model, LocalDate date, Long idSession, AuthenticationInfos userInfos) {
        List<CoursAdminDto> cours = agendaService.getCoursForAdmin(idSession);
        List<CoursAdminDto> coursPrevus = agendaService.getCoursPrevusForSession(idSession);
        model.addAttribute("idSession", idSession);
        model.addAttribute("cours", cours);
        model.addAttribute("cal", agendaService.getCalendarInfos(date));
        model.addAttribute("week", agendaService.getDayInfosForWeek(date));
        model.addAttribute("dateValue", date.toString());
        model.addAttribute("hours", agendaService.getHeuresJournee());
        model.addAttribute("hourMap", agendaService.getHourMapAdmin(coursPrevus));
        model.addAttribute("dateUtil", dateUtil);
        model.addAttribute("coursPrevus", coursPrevus);
        model.addAttribute("_user", userInfos);
        model.addAttribute("prev", date.minusWeeks(1).toString());
        model.addAttribute("next", date.plusWeeks(1).toString());
    }
}
