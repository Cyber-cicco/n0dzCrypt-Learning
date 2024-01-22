package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.dto.MessageForumDto;
import fr.diginamic.digilearning.dto.PostFilDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.repository.*;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final SalonRepository salonRepository;
    private final FilDiscussionRepository filDiscussionRepository;
    private final PostForumRepository postForumRepository;
    private final UtilisateurRepository utilisateurRepository;
    public static final Long TAILLE_PAGE = 10L;

    public Salon getSalonByIdAndCheckIfUserAuthorized(Long idUtilisateur, Long idSalon) {
        return salonRepository.getSalonByIdAndCheckAuthorized(idSalon, idUtilisateur).orElseThrow(EntityNotFoundException::new);
    }

    public FilDiscussion getRegles() {
        return filDiscussionRepository.findRegles();
    }

    public List<FilDiscussion> getFilOrderedFilDiscussion(Long id) {
        return filDiscussionRepository.findDiscussion(id);
    }

    public void verifyIfUserIsAllowed(AuthenticationInfos userInfos, Long idFil, HttpServletResponse response) {
        int num = salonRepository.getForumByIdAndCheckIfAuthorized(idFil, userInfos.getId());
        if(num < 1) {
            throw new UnauthorizedException();
        };
    }

    public List<MessageForumDto> getMessageFromFilDiscussion(Long idFil, Long page) {
        return postForumRepository.getPostInfosFromFil(idFil, (page-1) * TAILLE_PAGE, TAILLE_PAGE).stream()
                .map(resultRow -> SqlResultMapper.mapToObject(MessageForumDto.class, resultRow))
                .toList();
    }

    public FilDiscussion getFilDiscussion(Long idFil) {
        return filDiscussionRepository.findById(idFil).orElseThrow(EntityNotFoundException::new);
    }

    public void saveNewMessage(AuthenticationInfos userInfos, Long id, MessageDto postForumDto) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        FilDiscussion filDiscussion = filDiscussionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postForumRepository.save(PostForum.builder()
                .auteur(utilisateur)
                .contenu(postForumDto.getMessage())
                .dateEmission(LocalDateTime.now())
                .filDiscussion(filDiscussion)
                .build());
    }

    public Integer getNbPages(FilDiscussion filDiscussion) {
        int nbPages = (int) Math.ceil(filDiscussionRepository.countMessagesOfFil(filDiscussion.getId()) / Double.parseDouble(TAILLE_PAGE.toString()));
        return (nbPages == 0) ? 1 : nbPages;
    }

    public void saveNewFil(AuthenticationInfos userInfos, Long id, PostFilDto postFilDto) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        Salon salon = salonRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        filDiscussionRepository.save(FilDiscussion.builder()
                .titre(postFilDto.getTitre())
                .description(postFilDto.getDescription())
                .supprime(false)
                .ferme(false)
                .salon(salon)
                .auteur(utilisateur)
                .dateCreation(LocalDateTime.now())
                .epingle(false)
                .build());
    }

}
