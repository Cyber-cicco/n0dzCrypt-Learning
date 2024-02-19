package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.*;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminIrrigator {

    private final UtilisateurRepository utilisateurRepository;
    private final SessionRepository sessionRepository;
    private final ModuleRepository moduleRepository;
    private final CoursRepository coursRepository;
    private final SousModuleRepository sousModuleRepository;
    private final SujetRepository sujetRepository;
    private final AdministrationSessionRepository administrationSessionRepository;

    public void irrigateAdminPanelApprenants(Model model, AuthenticationInfos userInfos, List<Session> sessions, String adminInsert){
        model.addAttribute("_sessions", sessions);
        model.addAttribute("currDate", LocalDate.now());
        model.addAttribute("adminInsert", adminInsert);
    }

    public void irrigateSession(Model model, AuthenticationInfos userInfos, Long idSession) {
        Session session = sessionRepository.findById(idSession)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("_session", session);
        model.addAttribute("responsables", session.getAdministrationSessions());
    }

    public void irrigateUtilisateur(Model model, AuthenticationInfos userInfos, Long idUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findById(idUtilisateur).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("utilisateur", utilisateur);
    }

    public void irrigateCoursSessionModal(Model model, AuthenticationInfos userInfos, Long idSession) {
        List<Module> modules = moduleRepository.findModuleBySession(idSession);
        List<Cours> cours = coursRepository.getCoursBySession(idSession);
        model.addAttribute("modules", modules);
        model.addAttribute("cours", cours);
    }

    public void irrigateSousModulesModal(Model model, AuthenticationInfos userInfos, Long idModule) {
        Module module = moduleRepository.findById(idModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("smodules", module.getSousModules());
        model.addAttribute("module", module);
    }

    public void irrigateCoursModal(Model model, AuthenticationInfos userInfos, Long idSModule) {
        SousModule sousModule = sousModuleRepository.findById(idSModule).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("smodule", sousModule);
    }

    public void irrigateForumModal(Model model, Long idSession) {
        List<Sujet> sujets = sujetRepository.findAll();
        Session session = sessionRepository.findById(idSession).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("_session", session);
        model.addAttribute("salonAutorises", session.getSalonsAutorises());
        model.addAttribute("sujets", sujets);
    }

    public void irrigateResponsablesModal(Model model, Long idSession) {
        List<AdministrationSession> administrationSessions = administrationSessionRepository.findBySession_Id(idSession);
        List<Utilisateur> candidats = utilisateurRepository.findAllResponsables();
        model.addAttribute("responsables", administrationSessions);
        model.addAttribute("candidats", candidats);
        model.addAttribute("idSession", idSession);
    }

    public void irrigateChangementResponsables(Model model, Long idSession) {
        List<AdministrationSession> administrationSessions = administrationSessionRepository.findBySession_Id(idSession);
        model.addAttribute("responsables", administrationSessions);
        model.addAttribute("idSession", idSession);
    }
}
