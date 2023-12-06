package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.page.service.AgendaService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final AgendaService agendaService;
    private final CoursRepository coursRepository;

    @GetMapping("/api")
    public String getAgendaApi(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, LocalDate.now());
        return "pages/agenda";
    }
    @GetMapping
    public String getAgenda(@CookieValue("AUTH-TOKEN") String token, Model model){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, LocalDate.now());
        model.addAttribute("insert", "pages/agenda");
        return "base";
    }

    @GetMapping("date")
    public String getAgendaOnDate(@CookieValue("AUTH-TOKEN") String token, Model model, @RequestParam("semaine") LocalDate semaine){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseModel(userInfos, model, semaine);
        model.addAttribute("insert", "pages/agenda");
        return "base";
    }
    private void irrigateBaseModel(AuthenticationInfos userInfos, Model model, LocalDate date) {
        model.addAttribute("links", navBarService.getLinks(userInfos));
        model.addAttribute("cours", coursRepository.getAllCoursForUser(userInfos.getId()));
        model.addAttribute("calendar", "pages/fragments/agenda/agenda.calendar");
        irrigateCalendar(model, date);
    }

    private void irrigateCalendar(Model model, LocalDate date){
        model.addAttribute("cal", agendaService.getCalendarInfos(date));
        model.addAttribute("week", agendaService.getDayInfosForWeek(date));
        model.addAttribute("dateValue", date.toString());
        model.addAttribute("hours", agendaService.getHeuresJournee());
    }
}
