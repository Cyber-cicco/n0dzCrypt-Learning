package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.DTO.CoursDto;
import fr.diginamic.digilearning.page.irrigator.AgendaIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.service.AgendaService;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Controller chargé de gérer les requêtes concernant la partie agenda
 * */
@Controller
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AuthenticationService authenticationService;
    private final AgendaIrrigator agendaIrrigator;
    private final AgendaService agendaService;
    private final LayoutIrrigator layoutIrrigator;

    /**
     * Récupère un fragment de la page principale de l'agenda
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @return l'agenda
     * */
    @GetMapping("/api")
    public String getAgendaApi(Model model, @RequestParam(required = false, value = "id") Long idSession){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        if(userInfos.isAdministrateur()){
          agendaIrrigator.irrigateAdminCalendar(model, LocalDate.now(), idSession, userInfos);
            return Routes.ADR_AGENDA_BODY;
        }
        agendaIrrigator.irrigateBaseModel(userInfos, model, LocalDate.now());
        return Routes.ADR_AGENDA_BODY;
    }

    /**
     * Récupère la page principale de l'agenda
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @return l'agenda
     * */
    @GetMapping
    public String getAgenda(Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        agendaIrrigator.irrigateBaseModel(userInfos, model, LocalDate.now());
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_AGENDA_BODY);
        return Routes.ADR_BASE_LAYOUT;
    }

    /**
     * Récupère la page principale de l'agenda à la date précisée.
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param semaine représentation de la date à laquelle l'utilisateur veut voir le calendrire de la semaine
     * @return un template de l'agenda à la date demandée
     * */
    @GetMapping("date")
    public String getAgendaOnDate( Model model, @RequestParam("semaine") LocalDate semaine){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        agendaIrrigator.irrigateBaseModel(userInfos, model, semaine);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_AGENDA_BODY);
        return Routes.ADR_BASE_LAYOUT;
    }


    /**
     * Permet d'ajouter un cours à une date donnée
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param temps la date et l'heure choisie pour le cours
     * @param coursId l'identifiant du cours
     * @return le fragment de page contenant l'agenda
     */
    @PostMapping("/cours")
    public String postDate(
            
            Model model,
            @RequestParam("date") LocalDateTime temps,
            @RequestParam("id") Long coursId,
            HttpServletResponse response
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        Optional<CoursDto> cours = agendaService.putCoursInDate(userInfos, temps, coursId);
        agendaIrrigator.irrigateBaseModel(userInfos, model, temps.toLocalDate());
        return Routes.ADR_AGENDA_BODY;

    }

    /**
     * Permet d'ajouter un cours à une date donnée
     * @param model un objet permettant d'irriguer le template thymeleaf
     * @param temps la date et l'heure choisie pour le cours
     * @param coursId l'identifiant du cours
     * @return le fragment de page contenant l'agenda
     */
    @DeleteMapping("/cours")
    public String removeCoursFromAgenda(
            
            Model model,
            @RequestParam("date") LocalDateTime temps,
            @RequestParam("id") Long coursId
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        agendaService.removeCoursFromAgenda(userInfos, coursId);
        agendaIrrigator.irrigateCoursPrevus(userInfos, model);
        return Routes.ADR_AGENDA_COURSAPREVOIR;
    }
}
