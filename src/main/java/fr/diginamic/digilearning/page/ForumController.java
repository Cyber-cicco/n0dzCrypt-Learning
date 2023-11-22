package fr.diginamic.digilearning.page;


import fr.diginamic.digilearning.entities.FilDiscussion;

import fr.diginamic.digilearning.entities.Salon;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.ForumService;
import fr.diginamic.digilearning.repository.SujetRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/forum")
public class ForumController {

    private final AuthenticationService authenticationService;
    private final ForumService forumService;
    private final UtilisateurRepository utilisateurRepository;
    private final SujetRepository sujetRepository;
    @GetMapping("/api")
    public String getForumApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("cardInsert", "pages/fragments/forum/presentation.forum");
        irrigateBaseTemplate(userInfos, model, response);
        return "pages/forum";
    }


    @GetMapping("/error")
    public String getForumError() {
        return "pages/fragments/forum/presentation.forum";
    }

    @GetMapping
    public String getForum(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("cardInsert", "pages/fragments/forum/presentation.forum");
        model.addAttribute("insert", "pages/forum");
        irrigateBaseTemplate(userInfos, model, response);
        return "base";
    }

    @GetMapping("/salon")
    public String getBaseSalon(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSalon, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        irrigateSalonAttribute(userInfos, model, response, idSalon);
        model.addAttribute("cardInsert", "pages/fragments/forum/salon.forum");
        model.addAttribute("insert", "pages/forum");
        return "base";
    }

    private void irrigateSalonAttribute(AuthenticationInfos userInfos, Model model, HttpServletResponse response, Long idSalon) {
        Salon salon = forumService.getSalonByIdAndCheckIfUserAuthorized(userInfos.getId(), idSalon);
        FilDiscussion regles = forumService.getRegles();
        List<FilDiscussion> discussions = forumService.getFilOrderedFilDiscussion(salon.getId());
        model.addAttribute("salon", salon);
        model.addAttribute("regles", regles);
        model.addAttribute("discussions", discussions);
    }

    @GetMapping("/fil")
    public String getBaseFil(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idFil, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        irrigateFilAttribute(userInfos, model, response, idFil);
        model.addAttribute("insert", "pages/forum");
        model.addAttribute("cardInsert", "pages/fragments/forum/fil.forum");
        return "base";
    }

    @GetMapping("/salon/api")
    public String getSalonById(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSalon, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSalonAttribute(userInfos, model, response, idSalon);
        return "pages/fragments/forum/salon.forum";
    }

    private void irrigateBaseTemplate(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        model.addAttribute("utilisateur", utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("sujets", sujetRepository.findAll());
    }


    @GetMapping("/fil/api")
    public String getFil(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idFil, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateFilAttribute(userInfos, model, response, idFil);
        return "pages/fragments/forum/fil.forum";
    }

    private void irrigateFilAttribute(AuthenticationInfos userInfos, Model model, HttpServletResponse response, Long idFil) {
        forumService.verifyIfUserIsAllowed(userInfos, idFil, response);
        FilDiscussion fil = forumService.getFilDiscussion(idFil);
        model.addAttribute("id", fil.getSalon().getId());
        model.addAttribute("fil", fil);
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil));
    }

    @GetMapping("/regles/api")
    public String getRegles(@RequestParam Long id, Model model){
        Long idFil = forumService.getRegles().getId();
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil));
        model.addAttribute("fil", forumService.getFilDiscussion(idFil));
        model.addAttribute("id", id);
        return "pages/fragments/forum/fil.forum";
    }
    @GetMapping("/regles")
    public String getBaseRegles(@RequestParam Long id, @CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        Long idFil = forumService.getRegles().getId();
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil));
        model.addAttribute("fil", forumService.getFilDiscussion(idFil));
        model.addAttribute("id", id);
        model.addAttribute("insert", "pages/forum");
        model.addAttribute("cardInsert", "pages/fragments/forum/fil.forum");
        return "base";
    }
}
