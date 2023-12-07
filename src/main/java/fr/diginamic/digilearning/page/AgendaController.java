package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.page.service.AgendaService;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AuthenticationService authenticationService;
    private final NavBarService navBarService;
    private final AgendaService agendaService;
    private final CoursRepository coursRepository;

    record DateUtil(){
        public LocalDateTime getLdt(LocalDate date, LocalTime time) {
            return LocalDateTime.of(date, time);
        }

        public String getId(LocalDate date, LocalTime time) {
            return "T" + LocalDateTime.of(date, time).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss"));
        }
    }

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
        irrigateCalendar(model, date, userInfos);
    }

    private void irrigateCalendar(Model model, LocalDate date, AuthenticationInfos userInfos){
        List<CoursDto> coursPrevus = agendaService.getCoursPrevus(userInfos.getId());
        model.addAttribute("cal", agendaService.getCalendarInfos(date));
        model.addAttribute("week", agendaService.getDayInfosForWeek(date));
        model.addAttribute("dateValue", date.toString());
        model.addAttribute("hours", agendaService.getHeuresJournee());
        model.addAttribute("hourMap", agendaService.getHourMap(coursPrevus));
        model.addAttribute("dateUtil", new DateUtil());
        model.addAttribute("coursPrevus", coursPrevus);
        model.addAttribute("prev", date.minusWeeks(1).toString());
        model.addAttribute("next", date.plusWeeks(1).toString());
    }

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
        return "pages/agenda";
    }
}
