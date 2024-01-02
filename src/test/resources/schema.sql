create table if not exists sid.FORMATION
(
    ID                     bigint auto_increment
        primary key,
    AVEC_MODULE            bit                     null,
    DATE_FIN               date                    null,
    DATE_MAJ               datetime(6)             null,
    NOM                    varchar(255)            null,
    NOM_COURT              varchar(50)             null,
    REFERENCE              varchar(10)             null,
    DUREE                  int(5)      default 0   null,
    DUREE_FACTURABLE       int(5)      default 0   null,
    REFERENCE_EXAMEN       varchar(100)            null,
    EDITEUR_CERTIFICATION  varchar(50)             null,
    NOM_CERTIF             varchar(255)            null,
    SCORE_OBTENTION_CERTIF int(5)      default 0   null,
    NB_CERTIFS_BLANCHES    int(2)      default 0   null,
    INFOS_CERTIF           varchar(3000)           null,
    TITRE                  varchar(255)            null,
    USER_MAJ               varchar(255)            null,
    ID_NEXT                bigint                  null,
    ID_PARENT              bigint                  null,
    ID_PREVIOUS            bigint                  null,
    VERSION                varchar(10) default '1' not null,
    OBJS_PEDAGOGIQUES      varchar(2000)           null,
    PROJET_FIN             varchar(2000)           null,
    EVAL_AMONT             varchar(400)            null,
    PRE_REQUIS             varchar(200)            null,
    INTRO_SYLLABUS         varchar(2000)           null,
    NOM_LOGO_CERTIF        varchar(30)             null
);

create index FK_FORMATION1
    on FORMATION (ID_NEXT);

create index FK_FORMATION2
    on FORMATION (ID_PARENT);

create index FK_FORMATION3
    on FORMATION (ID_PREVIOUS);

create table if not exists sid.SESSION
(
    ID                         bigint auto_increment
        primary key,
    DATE_DEB                   date                            null,
    DATE_FIN                   date                            null,
    DATE_MAJ                   datetime(6)                     null,
    NOM                        varchar(255)                    null,
    DUREE_FACTURABLE           int(3)      default 0           not null,
    STATUT_VALIDATION          varchar(255)                    null,
    USER_MAJ                   varchar(255)                    null,
    ID_CENTRE                  bigint                          null,
    ID_FOR                     bigint                          null,
    ID_NEXT                    bigint                          null,
    ID_PARENT                  bigint                          null,
    ID_PREVIOUS                bigint                          null,
    SANS_CONFLIT               tinyint(1)                      not null,
    COMMENTAIRES               varchar(500)                    null,
    RESPONSABLE_PEDAGOGIQUE    varchar(50)                     null,
    RESPONSABLE_ADMIN          varchar(50)                     null,
    ROOM_MASTER1               varchar(50)                     null,
    ROOM_MASTER2               varchar(50)                     null,
    LIEU_PASSAGE_CERTIFICATION varchar(255)                    null,
    NB_BILANS                  int(3)                          not null,
    SYNTHESE                   text                            null,
    STATUT_PREPARATION         varchar(25) default 'CONFIRMEE' not null,
    TYPE_EMARGEMENT            varchar(10) default 'DECO_RECO' not null,
    ID_SOCIETE_BDC             bigint                          null,
    constraint FK_SESSION2
        foreign key (ID_FOR) references FORMATION (ID),
    constraint FKiskaef2xqdgp1c8ik877kokev
        foreign key (ID_FOR) references FORMATION (ID)
);

create index FK_SESSION3
    on SESSION (ID_NEXT);

create index FK_SESSION4
    on SESSION (ID_PARENT);

create index FK_SESSION5
    on SESSION (ID_PREVIOUS);

create table if not exists sid.UTILISATEUR
(
    ID                                bigint auto_increment
        primary key,
    CERTIF_DGN                        varchar(3)       null,
    DATE_CREATION                     datetime(6)      null,
    DATE_DESACTIVATION                datetime(6)      null,
    DATE_LOCKED                       datetime(6)      null,
    DATE_MAJ                          datetime(6)      null,
    DEFAULTROLE                       varchar(30)      not null,
    EMAIL                             varchar(50)      not null,
    ENABLED                           bit              not null,
    CHECK_RGPD                        bit              not null,
    NB_ESSAIS                         int              null,
    NOM                               varchar(50)      not null,
    PASSWORD                          varchar(80)      null,
    PRENOM                            varchar(30)      not null,
    TELEPHONE                         varchar(15)      null,
    ADRESSE                           varchar(100)     null,
    DATE_NAISSANCE                    date             null,
    CODE_RESPONSABLE                  varchar(25)      null,
    TOKEN_INIT                        varchar(255)     null,
    USER_MAJ                          varchar(255)     null,
    ID_VALIDEUR                       bigint           null,
    ID_SESSION                        bigint           null,
    ID_SOCIETE                        bigint           null,
    ID_CENTRE                         bigint           null,
    RECEVOIR_NOTIFICATIONS            char default '1' not null,
    OFFICE_EMAIL                      varchar(100)     null,
    ds_abandon                        bit              null,
    ds_commentairesCV                 varchar(255)     null,
    ds_commentairesLettre             varchar(255)     null,
    ds_commentairesLinkedin           varchar(255)     null,
    ds_statusCv                       tinyint          null,
    ds_dateContractualisation         date             null,
    ds_dateDerniereActivite           date             null,
    ds_dateEcheanceContractualisation date             null,
    ds_datePrisePremierContact        date             null,
    ds_joignable                      bit              null,
    ds_isInscrit                      bit              null,
    ds_linkedin                       varchar(255)     null,
    ds_statusLinkedin                 tinyint          null,
    ds_mailMicrosoft                  varchar(255)     null,
    ds_motDePasse                     varchar(255)     null,
    ds_niveauTechnique                tinyint          null,
    ds_place                          tinyint          null,
    ds_pourcentageCompletion          int              null,
    ds_prioritaire                    bit              null,
    ds_rqth                           bit              null,
    ds_statusAse                      tinyint          null,
    ds_typeRecherche                  tinyint          null,
    ds_adresse_id                     bigint           null,
    ds_session_id                     bigint           null,
    ds_statusLettre                   tinyint          null,
    ds_commentaires_abandon           varchar(255)     null,
    ds_date_abandon                   int              null,
    constraint UK_UTILISATEUR1
        unique (EMAIL),
    constraint UK_daucih6sx9vn631ggv857ec5m
        unique (EMAIL),
    constraint FK7kg48qdvwwydp5u8rfq0dbqke
        foreign key (ds_session_id) references SESSION (ID),
    constraint FK_UTILISATEUR1
        foreign key (ID_VALIDEUR) references UTILISATEUR (ID),
    constraint FK5qeds3exckipc0b5e77jjtqnf
        foreign key (ID_VALIDEUR) references UTILISATEUR (ID)
);

create table if not exists sid.dl_conversation
(
    id            bigint auto_increment
        primary key,
    libelleGroupe varchar(255) null
);

create table if not exists sid.dl_message
(
    id              bigint auto_increment
        primary key,
    contenu         varchar(255) null,
    conversation_id bigint       null,
    emetteur_id     bigint       null,
    dateEmission    datetime     null,
    next_id         bigint       null,
    previous_id     bigint       null,
    constraint UK_4ls1rox5e57wivaem2drswy59
        unique (next_id),
    constraint UK_pub3mtlofrvy27c3x5xqwcwws
        unique (previous_id),
    constraint FK22nbs8gg0kiw0crn4y66avop1
        foreign key (conversation_id) references sid.dl_conversation (id),
    constraint FKh26t60c07vc8plu5b9jf0qhvo
        foreign key (previous_id) references sid.dl_message (id),
    constraint FKnbuyn9bigrthhabsov5nkpciq
        foreign key (emetteur_id) references UTILISATEUR (ID),
    constraint FKgwrkoy6a4tlf6c9yvtxhcbgk
        foreign key (emetteur_id) references UTILISATEUR (ID),
    constraint FKqwxcdmqpv6umffvcdl771jert
        foreign key (next_id) references sid.dl_message (id),
    constraint dl_message_ibfk_1
        foreign key (next_id) references sid.dl_message (id),
    constraint dl_message_ibfk_2
        foreign key (previous_id) references sid.dl_message (id)
);

create table if not exists sid.dl_module
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) null,
    photo   varchar(255) null
);

create table if not exists sid.dl_module_formation
(
    id_module    bigint not null,
    id_formation bigint not null,
    constraint FK1fm74nw6wf5e2kqdkwbwgbnch
        foreign key (id_formation) references FORMATION (ID),
    constraint FK7snjvvky9uljwv1ja952sgiwy
        foreign key (id_module) references sid.dl_module (id)
);

create table if not exists sid.dl_post
(
    id           bigint auto_increment
        primary key,
    contenu      varchar(255) null,
    dateEmission datetime(6)  null,
    emetteur_id  bigint       null,
    constraint FKimeqxtfmyxctslynfourp3x7r
        foreign key (emetteur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_post_session
(
    session_id bigint not null,
    post_id    bigint not null,
    constraint FKa76gtv4dw1o7wunx0o9cdsky6
        foreign key (post_id) references sid.dl_post (id),
    constraint FKs3k65ftt0g0u0oi30d81nu9kt
        foreign key (session_id) references SESSION (ID)
);

create table if not exists sid.dl_ressource_cours
(
    id             bigint auto_increment
        primary key,
    libelle        varchar(255) null,
    libelleAffiche varchar(255) null,
    typeRessource  varchar(255) null,
    coursRef_id    bigint       null
);

create table if not exists sid.dl_sous_module
(
    id    bigint auto_increment
        primary key,
    titre varchar(50) not null,
    photo varchar(50) not null
);

create table if not exists sid.dl_cours
(
    id             bigint auto_increment
        primary key,
    sous_module_id bigint       null,
    titre          varchar(255) not null,
    difficulte     int          null,
    ordre          int          null,
    description    mediumtext   null,
    dureeEstimee   int          null,
    constraint FKobqm6x4582q04wnakti9hy7ml
        foreign key (sous_module_id) references sid.dl_sous_module (id)
);

create table if not exists sid.dl_chapitre
(
    id             bigint auto_increment
        primary key,
    contenu        longtext      null,
    libelle        varchar(255)  null,
    statusChapitre tinyint       null,
    cours_id       bigint        null,
    ordre          int           null,
    lienVideo      varchar(1024) null,
    constraint FKqv4923y4wxkbcj9esuernp4l8
        foreign key (cours_id) references sid.dl_cours (id)
);

create table if not exists sid.dl_detail_cours
(
    id          bigint auto_increment
        primary key,
    libelle     varchar(255) null,
    chapitre_id bigint       null,
    constraint FK1v4vqnejnh1ylppkk4iyvh8sq
        foreign key (chapitre_id) references sid.dl_chapitre (id)
);

create table if not exists sid.dl_flag_cours
(
    id           bigint auto_increment
        primary key,
    boomarked    bit      null,
    finished     bit      null,
    liked        bit      null,
    cours_id     bigint   null,
    stagiaire_id bigint   null,
    datePrevue   datetime null,
    constraint FK6e19t2sf0xefd6lfhm4564e4p
        foreign key (stagiaire_id) references UTILISATEUR (ID),
    constraint FK8du4e1bgysgsilwptkafua0b5
        foreign key (cours_id) references sid.dl_cours (id)
);

create table if not exists sid.dl_module_smodule
(
    id_smodule bigint not null,
    id_module  bigint not null,
    constraint FKo4s0rbwin71gkdqcu30kcofm2
        foreign key (id_module) references sid.dl_module (id),
    constraint FKqjbjngdft52b5hmr473q3dgj2
        foreign key (id_smodule) references sid.dl_sous_module (id)
);

create table if not exists sid.dl_question
(
    id             bigint auto_increment
        primary key,
    contenu        varchar(255) not null,
    supprimee      bit          not null,
    utilisateur_id bigint       null,
    chapitre_id    bigint       null,
    constraint FKjq1l51wf4nshwwheglxjeqcdy
        foreign key (chapitre_id) references sid.dl_chapitre (id),
    constraint FKkxbjlay565h1lysk0sh0e4wbo
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_relation_question
(
    id             bigint auto_increment
        primary key,
    disliked       bit    null,
    liked          bit    null,
    question_id    bigint null,
    utilisateur_id bigint null,
    constraint FK963dsd05d9oe5y1f92kc7ds4h
        foreign key (utilisateur_id) references UTILISATEUR (ID),
    constraint FKnb3swcm9yediexax82erkrx0s
        foreign key (question_id) references sid.dl_question (id)
);

create table if not exists sid.dl_reponse
(
    id          bigint auto_increment
        primary key,
    contenu     varchar(1024) not null,
    question_id bigint        null,
    supprimee   bit           not null,
    auteur_id   bigint        not null,
    constraint FKpo05hpshnfd2j2aal883u9d2c
        foreign key (question_id) references sid.dl_question (id),
    constraint dl_reponse_UTILISATEUR__fk
        foreign key (auteur_id) references UTILISATEUR (ID),
    constraint FKf3fsvktg4chbdhhygoy7uv38p
        foreign key (auteur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_relation_reponse
(
    id             bigint auto_increment
        primary key,
    disliked       bit    null,
    liked          bit    null,
    reponse_id     bigint null,
    utilisateur_id bigint null,
    constraint FK6ag1pfv55ifvqeeu6k3ubfodi
        foreign key (reponse_id) references sid.dl_reponse (id),
    constraint FK8lvbsitmq1wvbcfgvusfi4uy1
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_sujet
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) null
);

create table if not exists sid.dl_salon
(
    id          bigint auto_increment
        primary key,
    statusForum tinyint      null,
    titre       varchar(255) null,
    sujet_id    bigint       null,
    constraint FKl5rv9oj3fcvx35dj8wk3x9n2s
        foreign key (sujet_id) references sid.dl_sujet (id)
);

create table if not exists sid.blacklist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK30k32u352ke79p1mrdvyj3wje
        foreign key (salon_id) references sid.dl_salon (id),
    constraint FKs8ovdvbebrxhgh3w4isly7e9y
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_fil_discussion
(
    id             bigint auto_increment
        primary key,
    titre          varchar(255)  null,
    salon_id       bigint        null,
    ferme          tinyint(1)    null,
    supprime       tinyint(1)    not null,
    utilisateur_id bigint        not null,
    epingle        tinyint(1)    not null,
    dateCreation   datetime      not null,
    description    varchar(2048) null,
    constraint FKcau15ftfywm879pkcvlg7bt5s
        foreign key (salon_id) references sid.dl_salon (id),
    constraint FKi2cuhkgsdgnpf2of88u972tyk
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_post_forum
(
    id                bigint auto_increment
        primary key,
    auteur_id         bigint        null,
    contenu           varchar(2048) null,
    dateEmission      datetime(6)   null,
    fil_discussion_id bigint        null,
    reponseA_id       bigint        null,
    constraint FK23xnww5xp6nveemdye1njiacd
        foreign key (reponseA_id) references sid.dl_post_forum (id),
    constraint FK9cxivt7g88aot86n077xrqb4t
        foreign key (auteur_id) references UTILISATEUR (ID),
    constraint FKtdccrmuv21n8iqf84rw4bqwxw
        foreign key (fil_discussion_id) references sid.dl_fil_discussion (id)
);

create table if not exists sid.dl_utilisateur_conversation
(
    utilisateur_id  bigint not null,
    conversation_id bigint not null,
    constraint FK6kvr5j6sm3q64fu9notdx49li
        foreign key (conversation_id) references sid.dl_conversation (id),
    constraint FKjkln4noag4pakv4f2f0oyy0sw
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.dl_utilisateur_cours
(
    id_utilisateur bigint not null,
    id_cours       bigint not null,
    constraint FK3s16tpc2cjf85lemgxmnm192h
        foreign key (id_utilisateur) references UTILISATEUR (ID),
    constraint FKgy4sqe8wdrbwoqyirc0ne6trl
        foreign key (id_cours) references sid.dl_cours (id)
);

create table if not exists sid.dl_utilisateur_session
(
    utilisateur_id bigint not null,
    session_id     bigint not null,
    constraint FK9vfa9pex4mr11tlbohsulvpfr
        foreign key (session_id) references SESSION (ID),
    constraint FKdnctllhhkk622eplncrn0phdw
        foreign key (utilisateur_id) references UTILISATEUR (ID)
);

create table if not exists sid.whitelist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK1jnrnh5pst18vw23xqj1sk13j
        foreign key (utilisateur_id) references UTILISATEUR (ID),
    constraint FKdf2x3s0i3iedvuf53d51394le
        foreign key (salon_id) references sid.dl_salon (id)
);

