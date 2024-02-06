package fr.diginamic.digilearning.page.admin;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.page.Routes;
import fr.diginamic.digilearning.page.irrigator.ForumIrrigator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import fr.diginamic.digilearning.service.ForumService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/forum")
public class ForumAdminController {

    private final AuthenticationService authenticationService;
    private final ForumIrrigator forumIrrigator;
    private final ForumService forumService;
    @DeleteMapping("/post")
    public String deletePost(
            Model model,
            @RequestParam("id") Long idPost,
            @RequestParam("page") Long page,
            HttpServletResponse response
    ) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        authenticationService.mustBeOfRole(userInfos.getRoles(), TypeRole.ROLE_ADMINISTRATEUR, response);
        Long idFil = forumService.supprimerPost(idPost);
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, idFil, page);
        return Routes.ADR_FORUM_FIL;
    }
}
