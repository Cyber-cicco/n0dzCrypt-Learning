package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.page.service.AgendaService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller chargé de gérer les requêtes concernant la partie agenda
 * */
@Controller
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final AgendaService agendaService;
    private final DateUtil dateUtil;

    /**
     * Récupère un fragment de la page principale de l'agenda
     * @param token le token d'authentification
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @return l'agenda
     * */
    @GetMapping("/api")
    public String getAgendaApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, LocalDate.now());
        return "pages/agenda/agenda.main";
    }

    /**
     * Récupère la page principale de l'agenda
     * @param token le token d'authentification
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @return l'agenda
     * */
    @GetMapping
    public String getAgenda(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, LocalDate.now());
        model.addAttribute("insert", "pages/agenda/agenda.main");
        return "base";
    }

    /**
     * Récupère la page principale de l'agenda à la date précisée.
     * @param token le token d'authentification
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param semaine représentation de la date à laquelle l'utilisateur veut voir le calendrire de la semaine
     * @return un template de l'agenda à la date demandée
     * */
    @GetMapping("date")
    public String getAgendaOnDate(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("semaine") LocalDate semaine){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, semaine);
        model.addAttribute("insert", "pages/agenda/agenda.main");
        return "base";
    }

    /***
     * Irrigue le model avec les informations nécessaires à la construction de la page de l'agenda et de ses fragments
     * Donne les liens de la navbar en fonction.
     * Donne les cours de l'utilisateur triées pour sa liste de cours
     * Irrigue la partie spécifique au calendrier
     * @param userInfos les informations d'authentification de l'utilisateur
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param date représentation de la date à laquelle l'utilisateur veut voir le calendrire de la semaine
     */
    private void irrigateBaseModel(AuthenticationInfos userInfos, Model model, LocalDate date) {
        model.addAttribute("links", navBarService.getLinks(userInfos));
        model.addAttribute("cours", agendaService.getCoursForAgenda(userInfos.getId()));
        model.addAttribute("calendar", "pages/agenda/fragments/agenda.calendar");
        irrigateCalendar(model, date, userInfos);
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
        model.addAttribute("prev", date.minusWeeks(1).toString());
        model.addAttribute("next", date.plusWeeks(1).toString());
    }

    /**
     * Permet d'ajouter un cours à une date donnée
     * @param token le token d'authentification
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param temps la date et l'heure choisie pour le cours
     * @param coursId l'identifiant du cours
     * @return le fragment de page contenant l'agenda
     */
    @PostMapping("/cours")
    public String postDate(
            @CookieValue("AUTH-TOKEN") String token,
            Model model,
            @RequestParam("date") LocalDateTime temps,
            @RequestParam("id") Long coursId
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        agendaService.putCoursInDate(userInfos, temps, coursId);
        irrigateBaseModel(userInfos, model, temps.toLocalDate());
        return "pages/agenda/agenda.main";
    }

    /**
     * Permet d'ajouter un cours à une date donnée
     * @param token le token d'authentification
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param temps la date et l'heure choisie pour le cours
     * @param coursId l'identifiant du cours
     * @return le fragment de page contenant l'agenda
     */
    @DeleteMapping("/cours")
    public String removeCoursFromAgenda(
            @CookieValue("AUTH-TOKEN") String token,
            Model model,
            @RequestParam("date") LocalDateTime temps,
            @RequestParam("id") Long coursId
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        agendaService.removeCoursFromAgenda(userInfos, coursId);
        irrigateBaseModel(userInfos, model, temps.toLocalDate());
        return "pages/agenda/agenda.main";
    }
}
