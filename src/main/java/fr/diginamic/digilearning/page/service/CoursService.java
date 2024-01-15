package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.dto.*;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.enums.StatusChapitre;
import fr.diginamic.digilearning.entities.enums.StatusPublication;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.page.service.types.CoursCreationDiagnostics;
import fr.diginamic.digilearning.page.service.types.CoursCreationResult;
import fr.diginamic.digilearning.page.validators.CoursValidator;
import fr.diginamic.digilearning.page.validators.QCMValidator;
import fr.diginamic.digilearning.repository.*;
import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.reflection.SqlResultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursService {
    private final FlagCoursRepository flagCoursRepository;
    private final SousModuleRepository sousModuleRepository;
    private final CoursRepository coursRepository;
    private final ModuleRepository moduleRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final QuestionRepository questionRepository;
    private final ReponseRepository reponseRepository;
    private final ChapitreRepository chapitreRepository;
    private final CoursValidator coursValidator;
    private final QCMQuestionRepository qcmQuestionRepository;
    private final QCMValidator qcmValidator;
    private final QCMChoixRepository qcmChoixRepository;
    public List<ModuleDto> findSModulesByUtilisateur(Long id, Long idModule){
        List<ModuleDto> sousModules = sousModuleRepository.findModulesByUtilisateur(id, idModule)
                .stream()
                .map(res -> SqlResultMapper.mapToObject(ModuleDto.class, res))
                .toList();
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
        return cours
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursDto.class, c))
                .sorted()
                .toList();
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
        if(contenu == null) contenu = "";
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

    public List<CoursDto> getCoursCeJour(Long id) {
        return coursRepository.getPrevusCeJour(id)
                .stream()
                .map(c -> SqlResultMapper.mapToObject(CoursDto.class, c))
                .toList();
    }

    public FlagCours getFlagByCoursAndStagiaire(Cours cours, AuthenticationInfos userInfos) {
        return flagCoursRepository.findByCoursAndStagiaire_Id(cours, userInfos.getId())
                .orElseGet(() -> FlagCours.builder()
                        .liked(false)
                        .boomarked(false)
                        .finished(false)
                        .build());
    }

    public Chapitre getChapitreIfExistsElseThrow(Cours cours, Integer idChapitre) {
        return cours.getChapitres()
                .stream()
                .filter(chapitre1 -> chapitre1.getOrdre().equals(idChapitre))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<ModuleDto> findModulesByUtilisateur(Long id) {
        return moduleRepository.findModulesByUtilisateur(id)
                .stream().map(mod -> SqlResultMapper.mapToObject(ModuleDto.class, mod))
                .toList();
    }

    public void editerDescription(Long idCours, MessageDto descriptionCours, AuthenticationInfos userInfos) {
        Cours cours = coursRepository.getCoursByIdAndFormateur(idCours, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        cours.setDescription(descriptionCours.getMessage());
        coursRepository.save(cours);
    }

    public Chapitre createNewChapitre(AuthenticationInfos userInfos, Long idCours, ChapitreDto chapitreDto) {
        Cours cours = coursRepository.getCoursByIdAndFormateur(idCours, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        coursValidator.validateTitreChapitre(chapitreDto.getTitre());
        switch (chapitreDto.getStatusChapitre()) {
            case QCM -> {
                Chapitre chapitre = chapitreRepository.save(Chapitre.builder()
                        .aJour(false)
                        .statusPublication(StatusPublication.NON_PUBLIE)
                        .statusChapitre(StatusChapitre.QCM)
                        .cours(cours)
                        .libelle(chapitreDto.getTitre())
                        .ordre(coursRepository.findNombreChapitre(cours.getId()) + 1)
                        .build());
                QCMQuestion question = qcmQuestionRepository.save(QCMQuestion.builder()
                                .libelle("Nouvelle question")
                                .qcm(chapitre)
                                .build());
                chapitre.getQcmQuestions().add(question);
                return chapitre;
            }
            case COURS -> {
                return chapitreRepository.save(Chapitre.builder()
                        .cours(cours)
                        .libelle(chapitreDto.getTitre())
                        .statusChapitre(StatusChapitre.COURS)
                        .ordre(coursRepository.findNombreChapitre(cours.getId()) + 1)
                        .aJour(false)
                        .build());
            }
            case EXERCICE -> {
                return null;
            }
            default -> throw new BrokenRuleException("Il est nécessaire de préciser le type du chapitre");

        }
    }

    public Chapitre updateContenu(AuthenticationInfos userInfos, Long idChapitre, ContenuChapitreDto contenuChapitreDto) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        chapitre.setContenuNonPublie(contenuChapitreDto.getContenu());
        chapitre.setAJour(false);
        return chapitreRepository.save(chapitre);
    }


    public Chapitre publierContenu(AuthenticationInfos userInfos, Long idChapitre, ContenuChapitreDto contenuChapitreDto) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        chapitre.setContenuNonPublie(contenuChapitreDto.getContenu());
        chapitre.setContenu(contenuChapitreDto.getContenu());
        chapitre.setAJour(true);
        return chapitreRepository.save(chapitre);
    }

    public Long supprimerChapitre(AuthenticationInfos userInfos, Long idChapitre) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        chapitreRepository.delete(chapitre);
        return chapitre.getCours().getId();
    }

    public CoursCreationResult creerCours(AuthenticationInfos userInfos, Long idSousModule, CreationCoursDto creationCoursDto) {
        CoursCreationDiagnostics diagnostics = coursValidator.validateCoursCreation(creationCoursDto, idSousModule);
        if(diagnostics.isValid()) {
            Cours newCours = Cours.builder()
                    .auteurs(List.of(utilisateurRepository.findById(userInfos.getId()).orElseThrow(EntityNotFoundException::new)))
                    .ordre(creationCoursDto.getOrdre())
                    .titre(creationCoursDto.getTitre())
                    .difficulte(creationCoursDto.getDifficulte())
                    .dureeEstimee(creationCoursDto.getDuree())
                    .sousModule(sousModuleRepository.findById(idSousModule).orElseThrow(EntityNotFoundException::new))
                    .build();
            coursRepository.changeOrdre(newCours.getOrdre(), idSousModule);
            return new CoursCreationResult(
                    coursRepository.save(newCours),
                    diagnostics
            );
        }
        return new CoursCreationResult(null, diagnostics);
    }

    public boolean changeStatusValidationChoix(Long idChoix) {
        QCMChoix qcmChoix = qcmChoixRepository.findById(idChoix).orElseThrow(EntityNotFoundException::new);
        qcmChoix.setValid(!qcmChoix.getValid());
        return qcmChoixRepository.save(qcmChoix).getValid();
    }

    public QCMQuestion supprimerChoix(Long idChoix) {
        QCMChoix qcmChoix = qcmChoixRepository.findById(idChoix).orElseThrow(EntityNotFoundException::new);
        qcmChoixRepository.delete(qcmChoix);
        return qcmChoix.getQuestion();
    }

    public QCMQuestion creerQuestion(Chapitre qcm) {
        return qcmQuestionRepository.save(QCMQuestion.builder()
                .libelle("Nouvelle question")
                .ordre(qcm.getQcmQuestions().size() + 1)
                .qcm(qcm)
                .build());
    }

    public QCMQuestion getQuestion(Long idQuestion, AuthenticationInfos userInfos) {
        return qcmQuestionRepository.findByIdAndAdminId(idQuestion, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
    }

    public Chapitre supprimerQuestion(AuthenticationInfos userInfos, Long idQuestion) {
        Chapitre chapitre = chapitreRepository.findByAdminIdAndQuestionId(userInfos.getId(), idQuestion)
                .orElseThrow(EntityNotFoundException::new);
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        qcmChoixRepository.deleteAll(chapitre.getQcmQuestions().stream().filter(q -> q.getId().equals(idQuestion)).toList().get(0).getChoix());
        qcmQuestionRepository.delete(question);
        qcmQuestionRepository.updateOrdreAfterSuppression(question.getOrdre(), chapitre.getId());
        chapitre.setQcmQuestions(chapitre.getQcmQuestions().stream().filter(q -> !q.getId().equals(idQuestion)).toList());
        return chapitre;
    }

    @Transactional
    public Chapitre changeQCMQuestionOrdre(Long idQuestion, int ordre) {
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        Chapitre qcm = question.getQcm();
        int oldOrdre = question.getOrdre();
        if(oldOrdre == ordre) {
            return qcm;
        }
        if(ordre < 1 || ordre > qcm.getQcmQuestions().size()) {
            throw new BrokenRuleException("Ordre invalide");
        }
        if(ordre > oldOrdre) {
            qcmQuestionRepository.updateOrdreAscendant(oldOrdre, ordre, qcm.getId());
        } else {
            qcmQuestionRepository.updateOrdreDescendant(oldOrdre, ordre, qcm.getId());
        }
        question.setOrdre(ordre);
        qcmQuestionRepository.save(question);
        qcm = chapitreRepository.findById(qcm.getId()).orElseThrow(EntityNotFoundException::new);
        return qcm;
    }

    public record ReponseChangementQuestion(QCMQuestion question, Optional<String> diagnostic){}

    public ReponseChangementQuestion changeQCMQuestion(Long idQuestion, MessageDto messageDto) {
        var question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        Optional<String> diagnostic = qcmValidator.validateQCMQuestion(messageDto.getMessage());
        if(diagnostic.isEmpty()){
            question.setLibelle(messageDto.getMessage());
            qcmQuestionRepository.save(question);
        }
        return new ReponseChangementQuestion(question, diagnostic);
    }

    public record ReponseCreationChoix(QCMQuestion question, Optional<String> diagnostic){}

    public ReponseCreationChoix creerChoix(Long idQuestion, ChoixDto choixDto) {
        Optional<String> diagnostic = qcmValidator.validateQCMChoix(choixDto.getLibelle());
        if(diagnostic.isEmpty()){
            QCMQuestion question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
            QCMChoix qcmChoix = qcmChoixRepository.save(QCMChoix.builder()
                    .libelle(choixDto.getLibelle())
                    .question(question)
                    .valid(choixDto.getValid() != null)
                    .build());
            question.getChoix().add(qcmChoix);
            return new ReponseCreationChoix(question, diagnostic);
        }
        return new ReponseCreationChoix(null, diagnostic);
    }
}
