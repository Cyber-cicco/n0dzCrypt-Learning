package fr.diginamic.digilearning.page.admin.irrigator;

import fr.diginamic.digilearning.entities.Session;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.repository.SessionRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationAdminIrrigator {

    private final SessionRepository sessionRepository;
    public void irrigateConversationAdmin(Model model, AuthenticationInfos userInfos, String insert) {
        List<Session> sessionsAsResponsable = sessionRepository.findByResponsabiliteAndDatefin(userInfos.getId(), LocalDate.now());
        model.addAttribute("sessions", sessionsAsResponsable);
        model.addAttribute("chatInsert", insert);
    }
}
