package fr.diginamic.digilearning.json;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.ForumService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminJsonController {

    private final AuthenticationService authenticationService;
    private final ForumService forumService;
    private record Reponse(String message){}
    @PostMapping("/forum/whitelist")
    public ResponseEntity<?> changerAutorisationsForum(
            Model model,
            @RequestParam("idSalon") Long idSalon,
            @RequestParam("idSession") Long idSession,
            HttpServletResponse response
    ){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        forumService.changerAutorisationPourSession(idSession, idSalon);
        return ResponseEntity.ok(new Reponse("mise à jour réussie"));
    }
}
