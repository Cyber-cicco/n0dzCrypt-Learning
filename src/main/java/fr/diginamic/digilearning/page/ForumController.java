package fr.diginamic.digilearning.page;


import fr.diginamic.digilearning.dto.PostFilDto;
import fr.diginamic.digilearning.dto.PostForumDto;

import fr.diginamic.digilearning.entities.enums.TypeRole;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.page.irrigator.ForumIrrigator;
import fr.diginamic.digilearning.page.service.ForumService;
import fr.diginamic.digilearning.page.validators.FilValidator;
import fr.diginamic.digilearning.page.validators.PostForumValidator;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String getForumApi(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_PRESENTATION);
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        return Routes.ADR_FORUM;
    }

    @GetMapping
    public String getForum(Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_PRESENTATION);
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/salon")
    public String getBaseSalon(@RequestParam("id") Long idSalon, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, idSalon);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_SALON);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/fil")
    public String getBaseFil(@RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, idFil, page);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_FIL);
        return Routes.ADR_BASE_LAYOUT;
    }

    @GetMapping("/salon/api")
    public String getSalonById( @RequestParam("id") Long idSalon, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, idSalon);
        return Routes.ADR_FORUM_SALON;
    }


    @GetMapping("/fil/api")
    public String getFil( @RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, idFil, page);
        return Routes.ADR_FORUM_FIL;
    }

    @GetMapping("/regles/api")
    public String getRegles(@RequestParam Long id, Model model){
        forumIrrigator.irrigateRegles(model, id);
        return Routes.ADR_FORUM_FIL;
    }
    @GetMapping("/regles")
    public String getBaseRegles(@RequestParam Long id,  Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        forumIrrigator.irrigateBaseTemplate(userInfos, model, response);
        forumIrrigator.irrigateRegles(model, id);
        forumIrrigator.irrigateCard(model, userInfos, Routes.ADR_FORUM_FIL);
        return Routes.ADR_BASE_LAYOUT;
    }


    @PostMapping("/message")
    public String postNewMessage(@RequestParam Long id, @RequestParam Long page, @ModelAttribute PostForumDto postForumDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        postForumValidator.validatePostForum(postForumDto);
        forumService.saveNewMessage(userInfos, id, postForumDto);
        forumService.verifyIfUserIsAllowed(userInfos, id, response);
        forumIrrigator.irrigateFilAttribute(userInfos, model, response, id, page);
        return Routes.ADR_FORUM_FIL;
    }
    @PostMapping("/fil")
    public String postNewFil(@RequestParam Long id, @ModelAttribute PostFilDto postFilDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos();
        filValidator.validateFil(postFilDto);
        forumService.getSalonByIdAndCheckIfUserAuthorized(userInfos.getId(), id);
        forumService.saveNewFil(userInfos, id, postFilDto);
        forumIrrigator.irrigateSalonAttribute(userInfos, model, response, id);
        return Routes.ADR_FORUM_SALON;
    }
}
