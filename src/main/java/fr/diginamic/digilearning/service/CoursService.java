package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.dto.*;
import fr.diginamic.digilearning.entities.*;
import fr.diginamic.digilearning.entities.enums.StatusChapitre;
import fr.diginamic.digilearning.entities.enums.StatusPublication;
import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.service.types.CoursCreationDiagnostics;
import fr.diginamic.digilearning.service.types.CoursCreationResult;
import fr.diginamic.digilearning.validators.CoursValidator;
import fr.diginamic.digilearning.validators.QCMValidator;
import fr.diginamic.digilearning.repository.*;
import jakarta.servlet.http.HttpServletResponse;
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

import java.util.*;

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
    private final QCMPublicationRepository qcmPublicationRepository;
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

    public Question saveReponse(AuthenticationInfos userInfos, Long idQuestion, MessageDto reponseDto) {
        Question question;
        if(userInfos.isAdministrateur() || userInfos.isFormateur()){
            question = questionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        } else {
            question = questionRepository.findByIdAndUtilisateurId(idQuestion, userInfos.getId())
                    .orElseThrow(EntityNotFoundException::new);
        }
        Utilisateur utilisateur = utilisateurRepository.findById(userInfos.getId()).orElseThrow(EntityNotFoundException::new);
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

    public Chapitre saveQuestion(AuthenticationInfos userInfos, Long idChapitre, MessageDto questionDto) {
        Chapitre chapitre;
        if (userInfos.isAdministrateur() || userInfos.isFormateur()){
            chapitre = chapitreRepository.findById(idChapitre).orElseThrow(EntityNotFoundException::new);
        } else {
            chapitre = chapitreRepository.findByIdAndUtilisateurId(idChapitre, userInfos.getId())
                    .orElseThrow(EntityNotFoundException::new);
        }
        Utilisateur utilisateur = utilisateurRepository.findById(userInfos.getId())
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

    public Chapitre getChapitreIfExistsAndPublie(Cours cours, Integer ordreChapitre) {
        return cours.getChapitres()
                .stream()
                .filter(chapitre1 -> chapitre1.getOrdre().equals(ordreChapitre))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }
    public Chapitre getChapitreIfExists(Cours cours, Integer ordreChapitre) {
        return cours.getAllChapitres()
                .stream()
                .filter(chapitre1 -> chapitre1.getOrdre().equals(ordreChapitre))
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


    public record ReponseCreationChapitre(Chapitre chapitre, Optional<String> diagnostic){}
    public ReponseCreationChapitre createNewChapitre(AuthenticationInfos userInfos, Long idCours, ChapitreDto chapitreDto) {
        Cours cours = coursRepository.getCoursByIdAndFormateur(idCours, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        var diagnostic = coursValidator.validateTitreChapitre(chapitreDto.getTitre());
        if(diagnostic.isPresent()){
            return new ReponseCreationChapitre(null, diagnostic);
        }
        switch (chapitreDto.getStatusChapitre()) {
            case QCM -> {
                Chapitre chapitre = chapitreRepository.save(Chapitre.builder()
                        .statusPublication(StatusPublication.NON_PUBLIE)
                        .statusChapitre(StatusChapitre.QCM)
                        .cours(cours)
                        .libelle(chapitreDto.getTitre())
                        .ordre(coursRepository.findNombreChapitre(cours.getId()) + 1)
                        .build());
                QCMQuestion question = qcmQuestionRepository.save(QCMQuestion.builder()
                                .libelle("Nouvelle question")
                                .ordre(1)
                                .qcm(chapitre)
                                .build());
                chapitre.setQcmQuestions(List.of(question));
                return new ReponseCreationChapitre(chapitre, diagnostic);
            }
            case COURS -> {
                return new ReponseCreationChapitre(chapitreRepository.save(Chapitre.builder()
                        .cours(cours)
                        .libelle(chapitreDto.getTitre())
                        .statusChapitre(StatusChapitre.COURS)
                        .ordre(coursRepository.findNombreChapitre(cours.getId()) + 1)
                        .statusPublication(StatusPublication.NON_PUBLIE)
                        .build()), diagnostic);
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
        if(chapitre.getStatusPublication() == StatusPublication.NON_PUBLIE) return chapitreRepository.save(chapitre);
        chapitre.setStatusPublication(StatusPublication.PUBLIE_PAS_A_JOUR);
        return chapitreRepository.save(chapitre);
    }


    public Chapitre publierContenu(AuthenticationInfos userInfos, Long idChapitre, ContenuChapitreDto contenuChapitreDto) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        chapitre.setContenuNonPublie(contenuChapitreDto.getContenu());
        chapitre.setContenu(contenuChapitreDto.getContenu());
        chapitre.setStatusPublication(StatusPublication.PUBLIE_A_JOUR);
        return chapitreRepository.save(chapitre);
    }

    @Transactional
    public Cours supprimerChapitre(AuthenticationInfos userInfos, Long idChapitre) {
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(UnauthorizedException::new);
        chapitreRepository.updateOrdreAfterSuppression(chapitre.getOrdre(), chapitre.getCours().getId());
        chapitreRepository.delete(chapitre);
        return chapitre.getCours();
    }

    @Transactional
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
        changeStatusPublication(qcmChoix.getQuestion().getQcm());
        return qcmChoixRepository.save(qcmChoix).getValid();
    }

    public QCMQuestion supprimerChoix(Long idChoix) {
        QCMChoix qcmChoix = qcmChoixRepository.findById(idChoix).orElseThrow(EntityNotFoundException::new);
        qcmChoixRepository.delete(qcmChoix);
        changeStatusPublication(qcmChoix.getQuestion().getQcm());
        return qcmChoix.getQuestion();
    }

    public QCMQuestion creerQuestion(Chapitre qcm) {
        changeStatusPublication(qcm);
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

    @Transactional
    public Chapitre supprimerQuestion(AuthenticationInfos userInfos, Long idQuestion) {
        Chapitre chapitre = chapitreRepository.findByAdminIdAndQuestionId(userInfos.getId(), idQuestion)
                .orElseThrow(EntityNotFoundException::new);
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        qcmChoixRepository.deleteAll(question.getChoix());
        qcmQuestionRepository.delete(question);
        qcmQuestionRepository.updateOrdreAfterSuppression(question.getOrdre(), chapitre.getId());
        changeStatusPublication(chapitre);
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
        changeStatusPublication(question.getQcm());
        qcmQuestionRepository.save(question);
        qcm = chapitreRepository.findById(qcm.getId()).orElseThrow(EntityNotFoundException::new);
        return qcm;
    }

    @Transactional
    public Cours changeOrdreChapitre(Long idChapitre, int ordre, AuthenticationInfos userInfos){
        Chapitre chapitre = chapitreRepository.findByIdAndAdminId(idChapitre, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        Cours cours = chapitre.getCours();
        int oldOrdre = chapitre.getOrdre();
        if(oldOrdre == ordre) {
            return cours;
        }
        if(ordre < 1 || ordre > cours.getChapitres().size()) {
            throw new BrokenRuleException("Ordre invalide");
        }
        if(ordre > oldOrdre) {
            chapitreRepository.updateOrdreAscendant(oldOrdre, ordre, cours.getId());
        } else {
            chapitreRepository.updateOrdreDescendant(oldOrdre, ordre, cours.getId());
        }
        chapitre.setOrdre(ordre);
        chapitreRepository.save(chapitre);
        chapitre = chapitreRepository.findById(chapitre.getId()).orElseThrow(EntityNotFoundException::new);
        return chapitre.getCours();
    }

    public ReponseChangementQuestion changeQCMQuestionCommentaire(Long idQuestion, String comentaire) {
        QCMQuestion question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        Optional<String> diagnostic = qcmValidator.validateQCMCommentaire(comentaire);
        if(diagnostic.isEmpty()){
            question.setCommentaire(comentaire);
            changeStatusPublication(question.getQcm());
            qcmQuestionRepository.save(question);
        }
        return new ReponseChangementQuestion(question, diagnostic);
    }

    public QCMQuestion creerNouveauChoix(Long idAdmin, Long idQuestion) {
        QCMQuestion question = qcmQuestionRepository.findByIdAndAdminId(idQuestion, idAdmin)
                .orElseThrow(EntityNotFoundException::new);
        QCMChoix choix = qcmChoixRepository.save(QCMChoix.builder()
                .question(question)
                .valid(false)
                .libelle("Nouveau choix")
                .build());
        question.getChoix().add(choix);
        return question;
    }


    public QCMChoix changerLibelleChoix(Long idChoix, MessageDto messageDto, AuthenticationInfos userInfos) {
        qcmValidator.validateQCMChoix(messageDto.getMessage());
        QCMChoix choix = qcmChoixRepository.findByIdAndAdminId(idChoix, userInfos.getId())
                .orElseThrow(EntityNotFoundException::new);
        Optional<String> diagnotic = qcmValidator.validateQCMChoix(messageDto.getMessage());
        if(diagnotic.isPresent()){
            return choix;
        }
        choix.setLibelle(messageDto.getMessage());
        return qcmChoixRepository.save(choix);
    }

    public record ReponseChangementTitre(Cours cours, Optional<String> diagnostics){}
    public ReponseChangementTitre changerTitre(Long idCours, MessageDto messageDto, Long idAdmin) {
        Cours cours = coursRepository.getCoursByIdAndFormateur(idCours, idAdmin)
                .orElseThrow(EntityNotFoundException::new);
        Optional<String> diagnostic = coursValidator.validateTitreChapitre(messageDto.getMessage());
        if(diagnostic.isPresent()){
            return new ReponseChangementTitre(null, diagnostic);
        }
        cours.setTitre(messageDto.getMessage());
        return new ReponseChangementTitre(coursRepository.save(cours), diagnostic);
    }

    public record ReponsePublicationQCM(Chapitre chapitre, List<String> diagnostics){
        public String getMessage() {
            return "Le QCM ne peut être publié pour les raisons suivantes :  \n" + String.join("\n", diagnostics);
        }
    }
    @Transactional
    public ReponsePublicationQCM publierQCM(Long idQCM, HttpServletResponse response) {
        Chapitre chapitre = chapitreRepository.findById(idQCM).orElseThrow(EntityNotFoundException::new);
        List<String> diagnostics = qcmValidator.validateQCM(chapitre);
        if(!diagnostics.isEmpty()){
            return new ReponsePublicationQCM(chapitre, diagnostics);
        }
        creerPublication(chapitre);
        chapitre.setStatusPublication(StatusPublication.PUBLIE_A_JOUR);
        chapitreRepository.save(chapitre);
        return new ReponsePublicationQCM(chapitreRepository.save(chapitre), diagnostics);
   }


   private QCMPublication creerPublication(Chapitre chapitre){
       if(chapitre.getQcmPublications().isEmpty()) {
           var qcmPublication = QCMPublication.builder()
                   .derniere(true)
                   .questions(qcmQuestionRepository.saveAll(chapitre.getQcmQuestions().stream()
                           .map(QCMQuestion::clone)
                           .toList()))
                   .qcm(chapitre)
                   .build();
           qcmPublication.getQuestions().forEach(System.out::println);
           qcmChoixRepository.saveAll(qcmPublication.getQuestions()
                   .stream()
                   .flatMap(qcmQuestion -> qcmQuestion.getChoix().stream())
                   .toList());
           return qcmPublicationRepository.save(qcmPublication);
       }
       var qcmPublication = QCMPublication.builder()
               .build();
       var prevPublication = chapitre.getQcmPublication();
       var nouvellesQuestions = chapitre.getQcmQuestions();
       Map<String, QCMQuestion> prevQuestionsMap = new HashMap<>();
       for (QCMQuestion question : prevPublication.getQuestions()) {
           prevQuestionsMap.put(question.getLibelle(), question);
       }
       System.out.println(prevQuestionsMap);
       for (QCMQuestion nouvelleQuestion : nouvellesQuestions) {
           var prevQuestion = prevQuestionsMap.get(nouvelleQuestion.getLibelle());
           if(prevQuestion == null){
                qcmPublication.getQuestions().add(nouvelleQuestion.clone());
                continue;
           }
           if(prevQuestion.isSimilar(nouvelleQuestion)) {
               qcmPublication.getQuestions().add(prevQuestion);
               continue;
           }
           qcmPublication.getQuestions().add(nouvelleQuestion.clone());
       }
       prevPublication.setDerniere(false);
       qcmPublicationRepository.save(prevPublication);
       qcmPublication.setDerniere(true);
       qcmPublication.setQcm(chapitre);
       qcmQuestionRepository.saveAll(qcmPublication.getQuestions());
       qcmChoixRepository.saveAll(qcmPublication.getQuestions()
               .stream()
               .flatMap(qcmQuestion -> qcmQuestion.getChoix().stream())
               .toList());
       qcmPublicationRepository.save(qcmPublication);
       return qcmPublication;
   }

    public record ReponseChangementQuestion(QCMQuestion question, Optional<String> diagnostic){}

    private void changeStatusPublication(Chapitre chapitre) {
        if(chapitre.getStatusPublication() == StatusPublication.NON_PUBLIE || chapitre.getStatusPublication() == StatusPublication.PUBLIE_PAS_A_JOUR) return;
        chapitre.setStatusPublication(StatusPublication.PUBLIE_PAS_A_JOUR);
        chapitreRepository.save(chapitre);
    }

    public ReponseChangementQuestion changeQCMQuestion(Long idQuestion, MessageDto messageDto) {
        var question = qcmQuestionRepository.findById(idQuestion).orElseThrow(EntityNotFoundException::new);
        Optional<String> diagnostic = qcmValidator.validateQCMQuestion(messageDto.getMessage());
        if(diagnostic.isEmpty()){
            question.setLibelle(messageDto.getMessage());
            changeStatusPublication(question.getQcm());
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
            changeStatusPublication(question.getQcm());
            return new ReponseCreationChoix(question, diagnostic);
        }
        return new ReponseCreationChoix(null, diagnostic);
    }
}
