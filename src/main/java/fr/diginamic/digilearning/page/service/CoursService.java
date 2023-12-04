package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.MessageDto;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.repository.*;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import fr.diginamic.digilearning.dto.CoursDto;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final FlagCoursRepository flagCoursRepository;
    private final SousModuleRepository sousModuleRepository;
    private final CoursRepository coursRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;
    private final ChapitreRepository chapitreRepository;
    private final RelationQuestionRepository repository;
    public List<SousModule> findModulesByUtilisateur(String email, Long idModule){
        List<SousModule> sousModules = sousModuleRepository.findModulesByUtilisateur(email, idModule);
        if(sousModules.isEmpty()){
            throw new UnauthorizedException();
        }
        return sousModules;
    }
    public List<CoursDto> getCours(AuthenticationInfos userInfos, Long idSModule) {
        List<String[]> cours = coursRepository.findByUserAndSousModule(userInfos.getId(), idSModule);
        if(cours.isEmpty()){
            throw new UnauthorizedException();
        }
        return cours.stream().map(c -> SqlResultMapper.mapToObject(CoursDto.class, c)).toList();
    }

    public boolean patchBookmark(AuthenticationInfos userInfos, Long idCours) {
        FlagCours flagCours = extractFlag(userInfos, idCours);
        flagCours.setBoomarked(!flagCours.getBoomarked());
        return flagCoursRepository.save(flagCours).getBoomarked();
    }

    public boolean patchFinished(AuthenticationInfos userInfos, Long idCours) {
        FlagCours flagCours = extractFlag(userInfos, idCours);
        flagCours.setFinished(!flagCours.getFinished());
        return flagCoursRepository.save(flagCours).getFinished();
    }

    private FlagCours extractFlag(AuthenticationInfos userInfos, Long idCours) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(userInfos.getEmail()).orElseThrow(EntityNotFoundException::new);
        Cours cours = coursRepository.findByUserAndId(utilisateur.getId(), idCours).orElseThrow(EntityNotFoundException::new);
        return flagCoursRepository.findByCoursAndStagiaire(cours, utilisateur).orElseGet(() -> FlagCours.builder()
                .liked(false)
                .boomarked(false)
                .cours(cours)
                .stagiaire(utilisateur)
                .finished(false)
                .build());
    }

    public String getHtmlFromChapitreMarkdown(String contenu){
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                TaskListItemsExtension.create(),
                YamlFrontMatterExtension.create()
        );
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
        Node document = parser.parse(contenu);
        return renderer.render(document);
    }

    public Question saveReponse(Long id, Long idQuestion, MessageDto reponseDto) {
        Question question = questionRepository.findByIdAndUtilisateurId(idQuestion, id)
                .orElseThrow(EntityNotFoundException::new);
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Reponse reponse = Reponse.builder()
                .auteur(utilisateur)
                .contenu(reponseDto.getMessage())
                .supprimee(false)
                .question(question)
                .build();
        question.addReponse(reponse);
        reponseRepository.save(reponse);
        return question;
    }

    public Chapitre saveQuestion(Long id, Long idChapitre, MessageDto questionDto) {
        Chapitre chapitre = chapitreRepository.findByIdAndUtilisateurId(idChapitre, id)
                .orElseThrow(EntityNotFoundException::new);
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Question question = Question.builder()
                .auteur(utilisateur)
                .chapitre(chapitre)
                .contenu(questionDto.getMessage())
                .supprimee(false)
                .build();
        chapitre.getQuestions().add(question);
        questionRepository.save(question);
        return chapitre;
    }
}
