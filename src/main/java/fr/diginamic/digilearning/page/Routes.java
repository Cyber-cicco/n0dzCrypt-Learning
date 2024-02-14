package fr.diginamic.digilearning.page;

/**
 * Classe ayant pour but de définir les adresses des templates
 * NE PAS Y INSÉRER DE MÉTHODES NI AUTRE CHOSE QUE DES ADRESSES,
 * LA CLASSE A POUR VOCATION DE SE FAIRE ÉCRIRE PAR UN PROCESSUS
 * DE GÉNÉRATION DE CODE EN FONCTION DU NOM DES DIFFÉRENTS
 * FICHIERS DANS LE FRONT
 * */
public class Routes {

    public static final String ADR_BASE_LAYOUT = "layout/base";
    public static final String ADR_CONVERSATION_BODY ="pages/conversation/conversation";
    public static final String ADR_OUTER_CHAT ="pages/conversation/fragments/conversation.outer-chat";
    public static final  String ADR_INNER_CHAT = "pages/conversation/fragments/conversation.inner-chat";
    public static final String ADR_AGENDA_BODY = "pages/agenda/agenda.main";
    public static final String ADR_AGENDA_COURSAPREVOIR = "pages/agenda/fragments/agenda.cours-a-prevoir";
    public static final String ADR_COURS_QUESTION_RATING = "pages/cours/visionneuse/fragments/cours.question.rating";
    public static final String ADR_COURS_REPONSE_RATING = "pages/cours/visionneuse/fragments/cours.reponse.rating";
    public static final String ADR_MODAL_COURS_REPONSE = "components/modal/modal.cours.reponse";
    public static final String ADR_MODAL_COURS_QUESTION = "components/modal/modal.cours.question";
    public static final String ADR_CHAPITRE = "pages/cours/visionneuse/fragments/cours.chapitre";
    public static final String ADR_COURS_VISIONNEUSE = "pages/cours/visionneuse/cours.visionneuse";
    public static final String ADR_SOUS_MODULE = "pages/cours/sous-modules/cours.sous-modules";
    public static final String ADR_COURS_SOMMAIRE = "pages/cours/visionneuse/fragments/cours.sommaire";
    public static final String ADR_LISTE_QUESTIONS = "pages/cours/visionneuse/fragments/chapitre.questions";
    public static final String ADR_LISTE_REPONSES = "pages/cours/visionneuse/fragments/chapitre.reponses";
    public static final String ADR_LISTE_COURS = "pages/cours/liste/cours.liste";
    public static final String ADR_VISIONNEUSE_COURS = "pages/cours/visionneuse/cours.visionneuse";
    public static final String ADR_BOOKMARK_ICON = "components/flag-icons/bookmark";
    public static final String ADR_FINISHED_ICON = "components/flag-icons/finished";
    public static final String ADR_UTILISATEUR_ERROR = "pages/errors/utilisateur.error";
    public static final String ADR_FORM_ERROR = "components/reponses/form.error";
    public static final String ADR_FORUM = "pages/forum/forum";
    public static final String ADR_FORUM_FIL = "pages/forum/fragments/forum.fil";
    public static final String ADR_FORUM_PRESENTATION = "pages/forum/fragments/forum.presentation";
    public static final String ADR_FORUM_SALON = "pages/forum/fragments/forum.salon";
    public static final String ADR_HOME = "pages/home/home";
    public static final String ADR_LOGIN = "pages/login/login";
    public static final String ADR_PROFIL = "pages/profil/profil";
    public static final String ADR_PROFIL_PROGRES = "pages/profil/fragments/profil.progress";
    public static final String ADR_COURS_MODULE = "pages/cours/modules/cours.modules";
    public static final String ADR_COURS_MODULE_BODY = "pages/cours/modules/fragments/cours.modules.body";
    public static final String ADR_COURS_CAL = "pages/agenda/fragments/agenda.calendar.cours";
    public static final String ADR_COURS_CAL_ADMIN = "pages/agenda/fragments/cours.admin";
    public static final String ADR_COURS_ADMIN = "pages/cours/admin/cours.admin.main-panel";
    public static final String ADR_COURS_ADMIN_EDITER = "pages/cours/admin/fragments/cours.admin.editer.sommaire";
    public static final String ADR_ADMIN_CHAPITRE = "pages/cours/admin/fragments/cours.admin.editer.chapitre";
    public static final String ADR_COURS_CONTENT = "pages/cours/admin/fragments/cours.admin.html-fragment";
    public static final String ADR_MESSAGE = "components/reponses/message-reponse.html";
    public static final String ADR_MODAL_AJOUT_COURS = "pages/cours/admin/fragments/cours.admin.modal-ajout-cours";
    public static final String ADR_ADMIN_QCM = "pages/cours/admin/fragments/cours.admin.editer.qcm";
    public static final String ADR_QCM_QUESTION_LISTE = "pages/cours/admin/fragments/qcm.question.list-item";
    public static final String ADR_QCM_CHOIX_LISTE = "pages/cours/admin/fragments/qcm.choix.liste";
    public static final String ADR_COMPOSANT_VALIDATION = "components/flag-icons/validation";
    public static final String ADR_QCM_EDITION_QUESTION = "pages/cours/admin/fragments/cours.admin.editer.qcm.question";
    public static final String ADR_ADMIN_QCM_QUESTION_LISTE = "pages/cours/admin/fragments/qcm.question.liste";
    public static final String ADR_GENERIC_MESSAGE = "components/reponses/generic-message";
    public static final String ADR_QCM_ILLUSTRATION = "pages/cours/admin/fragments/qcm.illustration";
    public static final String ADR_QCM_CHOIX_ROW = "pages/cours/admin/fragments/qcm.choix.row";
    public static final String ADR_SOMMAIRE_CHAPITRES = "pages/cours/admin/fragments/sommaire.chapitres";
    public static final String ADR_COURS_TITRE = "pages/cours/admin/fragments/titre.chapitre";
    public static final String ADR_QCM = "pages/cours/visionneuse/fragments/qcm/cours.qcm" ;
    public static final String ADR_QCM_REFAIRE = "pages/cours/visionneuse/fragments/qcm/already-done";
    public static final String ADR_QCM_EN_COURS = "pages/cours/visionneuse/fragments/qcm/en-cours";
    public static final String ADR_QCM_TERMINE = "pages/cours/visionneuse/fragments/qcm/termine";
    public static final String ADR_ADMIN_APPRENANTS = "pages/admin/apprenants/base";
    public static final String ADR_ADMIN_PRESENTATION = "pages/admin/fragments/presentation";
    public static final String ADR_ADMIN_SESSION = "pages/admin/apprenants/fragments/session";
    public static final String ADR_ADMIN_UTILISATEUR = "pages/admin/apprenants/fragments/utilisateur";
    public static final String ADR_ADMIN_SESSION_COURS_MODAL = "pages/admin/apprenants/fragments/session.cours.modal";
    public static final String ADR_ADMIN_MODULE = "pages/admin/apprenants/fragments/session.cours.module";
    public static final String ADR_ADMIN_COURS_LISTE = "pages/admin/apprenants/fragments/session.cours.liste-cours";
    public static final String ADR_CHAPITRE_VIDEO = "pages/cours/admin/fragments/chapitre.video";
    public static final String ADR_ADMIN_MODULES = "pages/admin/modules/base";
    public static final String ADR_ADMIN_MODULES_DETAILS = "pages/admin/modules/fragments/module-details/details";
    public static final String ADR_ADMIN_MODULES_TITRE = "pages/admin/modules/fragments/module-details/details.titre";

    public static final String ADR_ADMIN_MODULES_PHOTO = "pages/admin/modules/fragments/module-details/module.photo";
    public static final String ADR_ADMIN_MODULE_FORMATION_MODAL = "pages/admin/modules/fragments/module-details/details.formation.modal";
    public static final String ADR_ADMIN_MODULE_FORMATION_DETAILS = "pages/admin/modules/fragments/module-details/details.formations";
    public static final String ADR_ADMIN_MODULE_SMODULES_DETAILS = "pages/admin/modules/fragments/module-details/details.smodules";
    public static final String ADR_ADMIN_MODULE_SMODULES_MODAL = "pages/admin/modules/fragments/module-details/module.smodules.modal";
    public static final String ADR_ADMIN_MODULE_LISTE_ITEM = "pages/admin/modules/fragments/modules.liste-item";
    public static final String ADR_ADMIN_MODULES_FORMATION_DETAILS = "pages/admin/modules/fragments/formation-details/details";
    public static final String ADR_ADMIN_MODULES_SMODULE_DETAILS = "pages/admin/modules/fragments/smodule-details/details";
    public static final String ADR_ADMIN_MODULES_SMODULE_TITRE = "pages/admin/modules/fragments/smodule-details/details.titre";
    public static final String ADR_ADMIN_MODULE_FORMATION_MODULE_DETAILS = "pages/admin/modules/fragments/formation-details/details.modules";
    public static final String ADR_ADMIN_MODULE_FORMATION_MODULES_MODAL = "pages/admin/modules/fragments/formation-details/modules.modal";
    public static final String ADR_ADMIN_MODULES_SMODULE_PHOTO = "pages/admin/modules/fragments/smodule-details/details.photo";
    public static final String ADR_ADMIN_MODULE_SMODULES_MODULES_MODAL = "pages/admin/modules/fragments/smodule-details/modules.modal";
    public static final String ADR_ADMIN_MODULE_SMODULES_MODULE_DETAILS = "pages/admin/modules/fragments/smodule-details/details.modules";
    public static final String ADR_ADMIN_SMODULE_LISTE_ITEM = "pages/admin/modules/fragments/smodules.liste-item";
    public static final String ADR_ADMIN_MODULE_DESC = "pages/admin/modules/fragments/description";
    public static final String ADR_ADMIN_FORUM_MODAL = "pages/admin/apprenants/fragments/forum.modal";
    public static final String ADR_ADMIN_APPRENANTS_DETAILS = "pages/admin/apprenants/fragments/apprenant.details";
    public static final String ADR_FORUM_BANNED = "pages/forum/fragments/banned";
    public static final String ADR_ADMIN_APPRENANTS_DETAILS_BAN = "pages/admin/apprenants/fragments/apprenant.details :: banButton()";
    public static final String ADR_ADMIN_CONVERSATIONS = "pages/conversation/admin/base";
    public static final String ADR_ADMIN_CONVERSATIONS_PRESENTATION = "pages/conversation/admin/presentation";
    public static final String ADR_ADMIN_RESPONSABLES_MODAL = "pages/admin/apprenants/fragments/responsables.modal";
    public static final String ADR_ADMIN_SESSION_RESPONSABLES = "pages/admin/apprenants/fragments/respontable";
    public static final String ADR_ADMIN_VISIONNEUSE_CHAPITRE = "pages/cours/admin/fragments/chapitre.admin.visionneuse";
    public static final String ADR_ADMIN_VISIONNEUSE_CHAPITRE_FRAGMENT = "pages/cours/admin/fragments/cours.visionneuse.chapitre";

    private Routes(){}
}
