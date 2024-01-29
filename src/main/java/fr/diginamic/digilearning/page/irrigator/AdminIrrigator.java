package fr.diginamic.digilearning.page.irrigator;

import fr.diginamic.digilearning.entities.Cours;
import fr.diginamic.digilearning.entities.Module;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.CoursRepository;
import fr.diginamic.digilearning.repository.ModuleRepository;
import fr.diginamic.digilearning.repository.SessionRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
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

    public void irrigateAdminPanelApprenants(Model model, AuthenticationInfos userInfos, List<Session> sessions, String adminInsert){
        model.addAttribute("_sessions", sessions);
        model.addAttribute("currDate", LocalDate.now());
        model.addAttribute("adminInsert", adminInsert);
    }

    public void irrigateSession(Model model, AuthenticationInfos userInfos, Long idSession) {
        Session session = sessionRepository.findById(idSession)
                .orElseThrow(EntityNotFoundException::new);
        model.addAttribute("_session", session);
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
}
