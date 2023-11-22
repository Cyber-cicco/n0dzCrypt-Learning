package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.MessageForumDto;
import fr.diginamic.digilearning.entities.FilDiscussion;
import fr.diginamic.digilearning.entities.Salon;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.FunctionalException;
import fr.diginamic.digilearning.repository.FilDiscussionRepository;
import fr.diginamic.digilearning.repository.PostForumRepository;
import fr.diginamic.digilearning.repository.SalonRepository;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {

    private final SalonRepository salonRepository;
    private final FilDiscussionRepository filDiscussionRepository;
    private final PostForumRepository postForumRepository;

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
            try {
                response.sendRedirect("/forum/error");
            } catch (IOException e) {
                throw new FunctionalException(e.getMessage());
            }
        };
    }

    public List<MessageForumDto> getMessageFromFilDiscussion(Long idFil) {
        return postForumRepository.getPostInfosFromFil(idFil).stream()
                .map(resultRow ->
                        {
                            try {
                                return SqlResultMapper.mapToObject(MessageForumDto.class, resultRow);
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new FunctionalException(e.getMessage());
                            }
                        }
                ).toList();
    }

    public FilDiscussion getFilDiscussion(Long idFil) {
        return filDiscussionRepository.findById(idFil).orElseThrow(EntityNotFoundException::new);
    }
}
