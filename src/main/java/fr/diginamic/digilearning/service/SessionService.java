package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.AdministrationSession;
import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.entities.Utilisateur;
import fr.diginamic.digilearning.entities.enums.StatusResponsableSession;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.repository.AdministrationSessionRepository;
import fr.diginamic.digilearning.repository.SessionRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final AdministrationSessionRepository administrationSessionRepository;

    public List<Session> getSessionsWhereDateFinAfter(LocalDate date) {
        return sessionRepository.findByDateFinAfter(date);
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public void deleteResponsable(Long idResponsable) {
        administrationSessionRepository.deleteById(idResponsable);
    }

    public void addResponsable(Long idResponsable, Long idSession) {
        Utilisateur responsable = utilisateurRepository.findById(idResponsable).orElseThrow(EntityNotFoundException::new);
        Session session = sessionRepository.findById(idSession).orElseThrow(EntityNotFoundException::new);
        administrationSessionRepository.save(AdministrationSession.builder()
                .statusResponsableSession(StatusResponsableSession.RESPONSABLE_PEDAGOGIQUE)
                .utilisateur(responsable)
                .session(session)
                .build());
    }
}
