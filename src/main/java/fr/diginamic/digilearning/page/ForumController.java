package fr.diginamic.digilearning.page;


import fr.diginamic.digilearning.components.service.NavBarService;
import fr.diginamic.digilearning.dto.PostFilDto;
import fr.diginamic.digilearning.dto.PostForumDto;
import fr.diginamic.digilearning.entities.FilDiscussion;

import fr.diginamic.digilearning.entities.Salon;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.page.service.ForumService;
import fr.diginamic.digilearning.page.validators.FilValidator;
import fr.diginamic.digilearning.page.validators.PostForumValidator;
import fr.diginamic.digilearning.repository.SujetRepository;
import fr.diginamic.digilearning.repository.UtilisateurRepository;
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
    private final NavBarService navBarService;
    private final ForumService forumService;
    private final UtilisateurRepository utilisateurRepository;
    private final SujetRepository sujetRepository;
    @GetMapping("/api")
    public String getForumApi(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("cardInsert", "pages/forum/fragments/forum.presentation");
        irrigateBaseTemplate(userInfos, model, response);
        return "pages/forum/forum";
    }


    @GetMapping("/error")
    public String getForumError() {
        return "pages/forum/fragments/forum.presentation";
    }

    @GetMapping
    public String getForum(@CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        model.addAttribute("cardInsert", "pages/forum/fragments/forum.presentation");
        model.addAttribute("insert", "pages/forum/forum");
        irrigateBaseTemplate(userInfos, model, response);
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "layout/base";
    }

    @GetMapping("/salon")
    public String getBaseSalon(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSalon, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        irrigateSalonAttribute(userInfos, model, response, idSalon);
        model.addAttribute("cardInsert", "pages/forum/fragments/forum.salon");
        model.addAttribute("insert", "pages/forum/forum");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "layout/base";
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
    public String getBaseFil(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        irrigateFilAttribute(userInfos, model, response, idFil, page);
        model.addAttribute("insert", "pages/forum/forum");
        model.addAttribute("cardInsert", "pages/forum/fragments/forum.fil");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "layout/base";
    }

    @GetMapping("/salon/api")
    public String getSalonById(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idSalon, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateSalonAttribute(userInfos, model, response, idSalon);
        return "pages/forum/fragments/forum.salon";
    }

    private void irrigateBaseTemplate(AuthenticationInfos userInfos, Model model, HttpServletResponse response){
        model.addAttribute("utilisateur", utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("sujets", sujetRepository.findAll());
    }


    @GetMapping("/fil/api")
    public String getFil(@CookieValue("AUTH-TOKEN") String token, @RequestParam("id") Long idFil, @RequestParam Long page, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateFilAttribute(userInfos, model, response, idFil, page);
        return "pages/forum/fragments/forum.fil";
    }

    private void irrigateFilAttribute(AuthenticationInfos userInfos, Model model, HttpServletResponse response, Long idFil, Long page) {
        forumService.verifyIfUserIsAllowed(userInfos, idFil, response);
        FilDiscussion fil = forumService.getFilDiscussion(idFil);
        model.addAttribute("page", page);
        model.addAttribute("nbPages", forumService.getNbPages(fil));
        model.addAttribute("id", fil.getSalon().getId());
        model.addAttribute("fil", fil);
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil, page));
    }

    @GetMapping("/regles/api")
    public String getRegles(@RequestParam Long id, Model model){
        irrigateRegles(model, id);
        return "pages/forum/fragments/forum.fil";
    }
    @GetMapping("/regles")
    public String getBaseRegles(@RequestParam Long id, @CookieValue("AUTH-TOKEN") String token, Model model, HttpServletResponse response){
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        irrigateBaseTemplate(userInfos, model, response);
        irrigateRegles(model, id);
        model.addAttribute("insert", "pages/forum/forum");
        model.addAttribute("cardInsert", "pages/forum/fragments/forum.fil");
        model.addAttribute("links", navBarService.getLinks(userInfos));
        return "layout/base";
    }

    private void irrigateRegles(Model model, Long id){
        Long idFil = forumService.getRegles().getId();
        model.addAttribute("page", 1);
        model.addAttribute("nbPages", 1);
        model.addAttribute("messages", forumService.getMessageFromFilDiscussion(idFil, 1L));
        model.addAttribute("fil", forumService.getFilDiscussion(idFil));
        model.addAttribute("id", id);
    }

    @PostMapping("/message")
    public String postNewMessage(@CookieValue("AUTH-TOKEN") String token, @RequestParam Long id, @RequestParam Long page, @ModelAttribute PostForumDto postForumDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        postForumValidator.validatePostForum(postForumDto);
        forumService.saveNewMessage(userInfos, id, postForumDto);
        forumService.verifyIfUserIsAllowed(userInfos, id, response);
        irrigateFilAttribute(userInfos, model, response, id, page);
        return "pages/forum/fragments/forum.fil";
    }
    @PostMapping("/fil")
    public String postNewFil(@CookieValue("AUTH-TOKEN") String token, @RequestParam Long id, @ModelAttribute PostFilDto postFilDto, Model model, HttpServletResponse response) {
        AuthenticationInfos userInfos = authenticationService.getAuthInfos(token);
        filValidator.validateFil(postFilDto);
        forumService.getSalonByIdAndCheckIfUserAuthorized(userInfos.getId(), id);
        forumService.saveNewFil(userInfos, id, postFilDto);
        irrigateSalonAttribute(userInfos, model, response, id);
        return "pages/forum/fragments/forum.salon";
    }
}
