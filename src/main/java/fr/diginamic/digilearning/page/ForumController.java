package fr.diginamic.digilearning.page;


import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.dto.PostFilDto;
import fr.diginamic.digilearning.page.irrigator.ForumIrrigator;
import fr.diginamic.digilearning.service.ForumService;
import fr.diginamic.digilearning.utils.hx.HX;
import fr.diginamic.digilearning.validators.FilValidator;
import fr.diginamic.digilearning.validators.PostForumValidator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final AuthenticationService authenticationService;
    private final PostForumValidator postForumValidator;
    private final FilValidator filValidator;
    private final ForumIrrigator forumIrrigator;
    private final ForumService forumService;
    @GetMapping("/api")
    public String getForumApi(@CookieValue("AUTH-TOKEN") String cookie, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_PRESENTATION);
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        return Routes.ADR_FORUM;
    }

    @GetMapping
    public String getForum(@CookieValue("AUTH-TOKEN") String cookie, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_PRESENTATION);
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/salon")
    public String getBaseSalon(@CookieValue("AUTH-TOKEN") String cookie,@RequestParam("id") Long idSalon, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, idSalon);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_SALON);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/fil")
    public String getBaseFil(@CookieValue("AUTH-TOKEN") String cookie,@RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, idFil, page);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_FIL);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/salon/api")
    public String getSalonById(@CookieValue("AUTH-TOKEN") String cookie, @RequestParam("id") Long idSalon, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, idSalon);
        return Routes.ADR_FORUM_SALON;
    }


    @GetMapping("/fil/api")
    public String getFil(@CookieValue("AUTH-TOKEN") String cookie, @RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, idFil, page);
        return Routes.ADR_FORUM_FIL;
    }

    @GetMapping("/regles/api")
    public String getRegles(@CookieValue("AUTH-TOKEN") String cookie,@RequestParam Long id, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateRegles(model, id);
        return Routes.ADR_FORUM_FIL;
    }
    @GetMapping("/regles")
    public String getBaseRegles(@CookieValue("AUTH-TOKEN") String cookie,@RequestParam Long id,  Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateRegles(model, id);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_FIL);
        return Routes.ADR_BASE_LAYOUT;
    }


    @PostMapping("/message")
    public String postNewMessage(@CookieValue("AUTH-TOKEN") String cookie,@RequestParam Long id, @RequestParam Long page, @ModelAttribute MessageDto postForumDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        postForumValidator.validatePostForum(postForumDto);
        if(!(userInfos.isAdministrateur() || userInfos.isFormateur())){
            forumService.verifyIfUserIsAllowed(userInfos, id, response);
        }
        forumService.saveNewMessage(userInfos, id, postForumDto);
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, id, page);
        return Routes.ADR_FORUM_FIL;
    }
    @PostMapping("/fil")
    public String postNewFil(@CookieValue("AUTH-TOKEN") String cookie, @RequestParam Long id, @ModelAttribute PostFilDto postFilDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(cookie);
        if(authenticationService.checkBanned(userInfos.getId())){
            return handleBan(response);
        }
        filValidator.validateFil(postFilDto);
        if(!(userInfos.isAdministrateur() || userInfos.isFormateur())){
            forumService.getSalonByIdAndCheckIfUserAuthorized(userInfos.getId(), id);
        }
        forumService.saveNewFil(userInfos, id, postFilDto);
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, id);
        return Routes.ADR_FORUM_SALON;
    }

    private String handleBan(HttpServletResponse response){
        response.setHeader(HX.RETARGET, "#insert");
        return Routes.ADR_FORUM_BANNED;
    }
}
