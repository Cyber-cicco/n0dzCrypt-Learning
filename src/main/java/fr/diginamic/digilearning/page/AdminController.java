package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.CoursAdminDto;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.irrigator.AdminIrrigator;
import fr.diginamic.digilearning.page.irrigator.AgendaIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.repository.SessionRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.AgendaService;
import fr.diginamic.digilearning.service.SessionService;
import fr.diginamic.digilearning.utils.hx.HX;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AuthenticationService authenticationService;
    private final LayoutIrrigator layoutIrrigator;
    private final AdminIrrigator adminIrrigator;
    private final SessionService sessionService;
    private final SessionRepository sessionRepository;
    private final AgendaService agendaService;
    private final AgendaIrrigator agendaIrrigator;
    @GetMapping
    public String getAdminPanel(Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        LocalDate dateRecherche = LocalDate.now();
        response.addHeader(HX.PUSH_URL, "/admin/apprenant?date=" + dateRecherche);
        var sessions = sessionService.getSessionsWhereDateFinAfter(dateRecherche);
        adminIrrigator.irrigateAdminPanelApprenants(model, userInfos, sessions, Routes.ADR_ADMIN_PRESENTATION);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_APPRENANTS);
        return Routes.ADR_BASE_LAYOUT;
    }
    @GetMapping("/api")
    public String getAdminPanelApi(Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        LocalDate dateRecherche = LocalDate.now();
        response.addHeader(HX.PUSH_URL, "/admin/apprenants?date=" + dateRecherche);
        var sessions = sessionService.getSessionsWhereDateFinAfter(dateRecherche);
        adminIrrigator.irrigateAdminPanelApprenants(model, userInfos, sessions, Routes.ADR_ADMIN_PRESENTATION);
        return Routes.ADR_ADMIN_APPRENANTS;
    }

    @GetMapping("/apprenants/api")
    public String getAdminApprenantsApi(
            Model model,
            @RequestParam(name = "date", required = false) LocalDate dateRecherche,
            @RequestParam(name = "idSession", required = false) Long idSession,
            @RequestParam(name = "idUtilisateur", required = false) Long idUtilisateur,
            HttpServletResponse response
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        List<Session> sessions = getSessions(dateRecherche);
        irrigateForApprenant(idSession, idUtilisateur, model, userInfos, sessions);
        return Routes.ADR_ADMIN_APPRENANTS;
    }

    @GetMapping("/apprenants")
    public String getAdminApprenants(
            Model model,
            @RequestParam(name = "date", required = false) LocalDate dateRecherche,
            @RequestParam(name = "idSession", required = false) Long idSession,
            @RequestParam(name = "idUtilisateur", required = false) Long idUtilisateur,
            HttpServletResponse response
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        List<Session> sessions = getSessions(dateRecherche);
        irrigateForApprenant(idSession, idUtilisateur, model, userInfos, sessions);
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_ADMIN_APPRENANTS);
        return Routes.ADR_BASE_LAYOUT;
    }

    private void irrigateForApprenant(Long idSession, Long idUtilisateur, Model model, AuthenticationInfos userInfos, List<Session> sessions){
        if(idSession != null) {
            adminIrrigator.irrigateSession(model, userInfos, idSession);
            adminIrrigator.irrigateAdminPanelApprenants(model, userInfos, sessions, Routes.ADR_ADMIN_SESSION);
        } else if (idUtilisateur != null){
            adminIrrigator.irrigateUtilisateur(model, userInfos, idUtilisateur);
            adminIrrigator.irrigateAdminPanelApprenants(model, userInfos, sessions, Routes.ADR_ADMIN_UTILISATEUR);
        } else {
            adminIrrigator.irrigateAdminPanelApprenants(model, userInfos, sessions, Routes.ADR_ADMIN_PRESENTATION);
        }
    }

    private List<Session> getSessions(LocalDate dateRecherche){
        if(dateRecherche != null) {
            return sessionService.getSessionsWhereDateFinAfter(dateRecherche);
        }
        return sessionService.getAllSessions();
    }

    @GetMapping("/session")
    public String getSessionInfos(Model model, @RequestParam("id") Long id, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        adminIrrigator.irrigateSession(model, userInfos, id);
        return Routes.ADR_ADMIN_SESSION;
    }

    @GetMapping("/session/cours")
    public String getSessionCoursModal(Model model, @RequestParam("id") Long idSession, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        adminIrrigator.irrigateCoursSessionModal(model, userInfos, idSession);
        return Routes.ADR_ADMIN_SESSION_COURS_MODAL;
    }

    @PostMapping("/session/cours")
    public String prevoirCours(
            Model model,
            @RequestParam("date") LocalDateTime temps,
            @RequestParam("id") Long coursId,
            @RequestParam("idSession") Long idSession,
            HttpServletResponse response
    ){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Optional<CoursAdminDto> cours = agendaService.prevoirCoursForSession(userInfos, temps, coursId, idSession);
        if(cours.isPresent()){
            agendaIrrigator.irrigateCoursAdminOnCalendar(userInfos, model, temps, cours.get());
            return Routes.ADR_COURS_CAL;
        }
        response.setHeader(HX.RETARGET, "#insert");
        agendaIrrigator.irrigateAdminCalendar(model, temps.toLocalDate(), idSession, userInfos);
        return Routes.ADR_AGENDA_BODY;
    }

}
