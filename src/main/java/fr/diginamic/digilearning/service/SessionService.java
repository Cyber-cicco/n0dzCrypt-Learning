package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.repository.AdministrationSessionRepository;
import fr.diginamic.digilearning.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
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
}
