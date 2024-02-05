package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.dto.ReponseQCMDto;
import fr.diginamic.digilearning.entities.QCMPasse;
import fr.diginamic.digilearning.page.irrigator.CoursIrrigator;
import fr.diginamic.digilearning.page.irrigator.LayoutIrrigator;
import fr.diginamic.digilearning.repository.QCMPasseRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.ChapitreService;
import fr.diginamic.digilearning.service.QCMService;
import fr.diginamic.digilearning.utils.hx.HX;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("cours/qcm")
public class QCMController {
    private final AuthenticationService authenticationService;
    private final ChapitreService chapitreService;
    private final QCMPasseRepository qcmPasseRepository;
    private final CoursIrrigator coursIrrigator;
    private final LayoutIrrigator layoutIrrigator;
    private final QCMService qcmService;
    @GetMapping("/resultat")
    public String getResultats(Model model, @RequestParam("id") Integer id, @RequestParam("idCours") Long idCours) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var chapitreInfos = chapitreService.getChapitreInfos(userInfos, id, idCours);
        Optional<QCMPasse> qcmPasse = qcmPasseRepository.findByUtilisateurAndQCM(userInfos.getId(), chapitreInfos.chapitre().getId());
        coursIrrigator.irrigateQCMFinished(model, userInfos, chapitreInfos.chapitre(), qcmPasse.get());
        layoutIrrigator.irrigateBaseLayout(model, userInfos, Routes.ADR_COURS_VISIONNEUSE);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/continue")
    public String continueQCM(Model model, @RequestParam("id") Long idQCM) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var repriseQCMInfos = qcmService.reprendreQCM(userInfos.getId(), idQCM);
        coursIrrigator.irrigateQCM(model, userInfos, repriseQCMInfos.chapitre(), repriseQCMInfos.qcmPasse().getQcmPublication().getQuestions(), repriseQCMInfos.cours(), repriseQCMInfos.index());
        return Routes.ADR_QCM;
    }
    @GetMapping("/recommencer")
    public String recommencerQCM(Model model, @RequestParam("id") Long idQCM) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var repriseQCMInfos = qcmService.recommencerQCM(userInfos.getId(), idQCM);
        coursIrrigator.irrigateQCM(model, userInfos, repriseQCMInfos.chapitre(), repriseQCMInfos.chapitre().getQcmQuestionsPubliees(),  repriseQCMInfos.cours(), repriseQCMInfos.index());
        return Routes.ADR_QCM;
    }

    @PostMapping("/response")
    public String postNewResponseToQCM(Model model, @RequestParam("id") Long idQCM, @RequestParam("idQuestion") Long idQuestion, @RequestBody List<ReponseQCMDto> reponseQCM, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        var nextPage = qcmService.postNewResponse(userInfos.getId(), idQCM, idQuestion, reponseQCM);
        if(nextPage.index().isPresent()){
            coursIrrigator.irrigateQCM(model, userInfos, nextPage.qcm(), nextPage.qcmPasse().getQcmPublication().getQuestions(), nextPage.qcm().getCours(), nextPage.index().get());
            return Routes.ADR_QCM;
        }
        qcmService.archiveQCMPasse(nextPage.qcmPasse());
        coursIrrigator.irrigateQCMFinished(model, userInfos, nextPage.qcm(), nextPage.qcmPasse());
        response.setHeader(HX.PUSH_URL, "/cours/qcm/resultat?id=" + nextPage.qcm().getId());
        return Routes.ADR_QCM_TERMINE;
    }
}
