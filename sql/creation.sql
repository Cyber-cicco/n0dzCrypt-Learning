create database digilearning;
create table digilearning.FORMATION
(
    ID                     bigint auto_increment
        primary key,
    NOM                    varchar(255)            null,
    NOM_COURT              varchar(50)             null,
    REFERENCE              varchar(10)             null,
    USER_MAJ               varchar(255)            null
);

create table digilearning.ROLE
(
    ID      bigint auto_increment
        primary key,
    LIBELLE varchar(255) null,
    TYPE    varchar(255) null
);

create table digilearning.SESSION
(
    ID                         bigint auto_increment
        primary key,
    DATE_DEB                   date                            null,
    DATE_FIN                   date                            null,
    NOM                        varchar(255)                    null,
    DUREE_FACTURABLE           int(3)      default 0           not null,
    ID_FOR                     bigint                          null,
    constraint FK_SESSION2
        foreign key (ID_FOR) references digilearning.FORMATION (ID)
);

create table digilearning.dl_conversation
(
    id            bigint auto_increment
        primary key,
    libelleGroupe varchar(255) null
);

create table digilearning.dl_module
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) not null,
    photo   varchar(255) null,
    constraint libelle
        unique (libelle)
);

create table digilearning.dl_module_formation
(
    id_module    bigint not null,
    id_formation bigint not null,
    constraint FK1fm74nw6wf5e2kqdkwbwgbnch
        foreign key (id_formation) references digilearning.FORMATION (ID),
    constraint FK7snjvvky9uljwv1ja952sgiwy
        foreign key (id_module) references digilearning.dl_module (id)
);

create table digilearning.dl_sous_module
(
    id    bigint auto_increment
        primary key,
    titre varchar(50) not null,
    photo varchar(50) null,
    ordre int         null
);

create table digilearning.dl_cours
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
        foreign key (sous_module_id) references digilearning.dl_sous_module (id)
);

create table digilearning.dl_chapitre
(
    id                bigint auto_increment
        primary key,
    contenu           longtext      null,
    libelle           varchar(255)  null,
    statusChapitre    tinyint       not null,
    cours_id          bigint        null,
    ordre             int           null,
    lienVideo         varchar(1024) null,
    contenuNonPublie  longtext      null,
    statusPublication tinyint(2)    null,
    constraint FKqv4923y4wxkbcj9esuernp4l8
        foreign key (cours_id) references digilearning.dl_cours (id)
);

create table digilearning.dl_cours_session
(
    id         bigint auto_increment
        primary key,
    datePrevue datetime(6) null,
    cours_id   bigint      null,
    session_id bigint      null,
    constraint FK58lq65ojqjajkbqdv8fe4qibs
        foreign key (cours_id) references digilearning.dl_cours (id),
    constraint FKjdqptvgg30q9msn4ouki0js5p
        foreign key (session_id) references digilearning.SESSION (ID)
);

create table digilearning.dl_detail_cours
(
    id          bigint auto_increment
        primary key,
    libelle     varchar(255) null,
    chapitre_id bigint       null,
    constraint FK1v4vqnejnh1ylppkk4iyvh8sq
        foreign key (chapitre_id) references digilearning.dl_chapitre (id)
);

create table digilearning.dl_module_smodule
(
    id_smodule bigint not null,
    id_module  bigint not null,
    constraint FKo4s0rbwin71gkdqcu30kcofm2
        foreign key (id_module) references digilearning.dl_module (id),
    constraint FKqjbjngdft52b5hmr473q3dgj2
        foreign key (id_smodule) references digilearning.dl_sous_module (id)
);

create table digilearning.dl_qcm_publication
(
    id       bigint auto_increment
        primary key,
    derniere bit    null,
    qcm_id   bigint null,
    constraint FKo3991kji7vd48d22mehx5if3j
        foreign key (qcm_id) references digilearning.dl_chapitre (id)
);

create table digilearning.dl_qcm_question
(
    id            bigint auto_increment
        primary key,
    libelle       varchar(255)  null,
    qcm_id        bigint        null,
    ordre         int           not null,
    qcm_publie_id bigint        null,
    illustration  varchar(255)  null,
    commentaire   varchar(1024) null,
    constraint FK9tc6v2535iceav5g8leo07m1m
        foreign key (qcm_id) references digilearning.dl_chapitre (id),
    constraint dl_qcm_question_dl_chapitre_id_fk
        foreign key (qcm_publie_id) references digilearning.dl_chapitre (id),
    constraint FKeuak6op8a47c2ok6e3ue1ugjx
        foreign key (qcm_publie_id) references digilearning.dl_chapitre (id)
);

create table digilearning.dl_publication_question
(
    question_id    bigint not null,
    publication_id bigint not null,
    constraint FKb3u7leveoh0lh2nn8iv01lejh
        foreign key (publication_id) references digilearning.dl_qcm_publication (id),
    constraint FKkokfe8p8u7trohw4j2s04o5ll
        foreign key (publication_id) references digilearning.dl_qcm_publication (id),
    constraint FKmibwf5gfo20orrphnp48r4h5b
        foreign key (question_id) references digilearning.dl_qcm_question (id),
    constraint FKqgu3ip22puemmedgwq3pf5yt
        foreign key (question_id) references digilearning.dl_qcm_question (id)
);

create table digilearning.dl_qcm_choix
(
    id          bigint auto_increment
        primary key,
    libelle     varchar(255) null,
    valid       bit          null,
    question_id bigint       null,
    constraint FKl2mb27x6yy3tvcu91oow3r4fb
        foreign key (question_id) references digilearning.dl_qcm_question (id)
);

create table digilearning.dl_qcm_question_choix
(
    QCMQuestion_id bigint not null,
    choix_id       bigint not null,
    constraint UK_gwo2bfodt47h6ab3l6tmfin5l
        unique (choix_id),
    constraint FK2jv55f7xd4pr3asslgpe2xuhw
        foreign key (QCMQuestion_id) references digilearning.dl_qcm_question (id),
    constraint FKna7kifi65ovq8tqe6mmcr5yvl
        foreign key (choix_id) references digilearning.dl_qcm_choix (id)
);

create table digilearning.dl_sujet
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) null
);

create table digilearning.dl_salon
(
    id          bigint auto_increment
        primary key,
    statusForum tinyint      null,
    titre       varchar(255) null,
    sujet_id    bigint       null,
    constraint FKl5rv9oj3fcvx35dj8wk3x9n2s
        foreign key (sujet_id) references digilearning.dl_sujet (id)
);

create table digilearning.dl_salon_session
(
    session_id bigint not null,
    salon_id   bigint not null,
    constraint FKomq1hf63wro4os29uw44dvtp1
        foreign key (salon_id) references digilearning.dl_salon (id),
    constraint FKqro5omtaruckcb4ybhkh0w12f
        foreign key (session_id) references digilearning.SESSION (ID)
);

create table digilearning.dl_travaux_pratique
(
    id           bigint auto_increment
        primary key,
    correction   longtext null,
    instructions longtext null
);

create table digilearning.UTILISATEUR
(
    ID                                bigint auto_increment
        primary key,
    EMAIL                             varchar(50)      not null,
    NOM                               varchar(50)      not null,
    PASSWORD                          varchar(80)      null,
    PRENOM                            varchar(30)      not null,
    TELEPHONE                         varchar(15)      null,
    ADRESSE                           varchar(100)     null,
    DATE_NAISSANCE                    date             null,
    TOKEN_INIT                        varchar(255)     null,
    USER_MAJ                          varchar(255)     null,
    ID_SESSION                        bigint           null,
    OFFICE_EMAIL                      varchar(100)     null,
    dl_banned                         tinyint(1)       null,
    constraint UK_UTILISATEUR1
        unique (EMAIL),
    constraint UK_daucih6sx9vn631ggv857ec5m
        unique (EMAIL)
);

create table digilearning.ADMINISTRATION_SESSION
(
    ID                         bigint auto_increment
        primary key,
    ID_SESSION                 bigint     null,
    ID_UTILISATEUR             bigint     null,
    STATUS_RESPONSABLE_SESSION tinyint(1) null,
    constraint ADMINISTRATION_SESSION_ibfk_1
        foreign key (ID_SESSION) references digilearning.SESSION (ID),
    constraint ADMINISTRATION_SESSION_ibfk_2
        foreign key (ID_UTILISATEUR) references digilearning.UTILISATEUR (ID),
    constraint FK3rck7fwxampt7apc541lnvo4d
        foreign key (ID_UTILISATEUR) references digilearning.UTILISATEUR (ID),
    constraint FK4ehfn60tm0fnku1e74cn6bvsq
        foreign key (ID_SESSION) references digilearning.SESSION (ID)
);

create table digilearning.ROLE_UTILISATEUR
(
    ID_UTILISATEUR bigint not null,
    ID_ROLE        bigint not null,
    constraint FK_ROLE_UTILISATEUR1
        foreign key (ID_ROLE) references digilearning.ROLE (ID),
    constraint FK_ROLE_UTILISATEUR2
        foreign key (ID_UTILISATEUR) references digilearning.UTILISATEUR (ID),
    constraint FKc4sx3xk0sftvon2jpj4ukc68d
        foreign key (ID_UTILISATEUR) references digilearning.UTILISATEUR (ID),
    constraint FKkp8he55mabr635hbw3aqc2hmf
        foreign key (ID_ROLE) references digilearning.ROLE (ID)
);

create table digilearning.SESSION_STAGIAIRE
(
    ID_SES  bigint not null,
    ID_STAG bigint not null,
    constraint UX_SESSION_STAGIAIRE
        unique (ID_SES, ID_STAG),
    constraint FK_SESSION_STAGIAIRE1
        foreign key (ID_SES) references digilearning.SESSION (ID),
    constraint FK_SESSION_STAGIAIRE2
        foreign key (ID_STAG) references digilearning.UTILISATEUR (ID),
    constraint FKbo113uq3orpsbefnxf6kirb8o
        foreign key (ID_STAG) references digilearning.UTILISATEUR (ID),
    constraint FKqss4vrnmwoh2itrf9f3hr0o3x
        foreign key (ID_SES) references digilearning.SESSION (ID)
);

create table digilearning.blacklist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK30k32u352ke79p1mrdvyj3wje
        foreign key (salon_id) references digilearning.dl_salon (id),
    constraint FKs8ovdvbebrxhgh3w4isly7e9y
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_fil_discussion
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
        foreign key (salon_id) references digilearning.dl_salon (id),
    constraint FKi2cuhkgsdgnpf2of88u972tyk
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_flag_cours
(
    id           bigint auto_increment
        primary key,
    boomarked    bit        null,
    finished     bit        null,
    liked        bit        null,
    cours_id     bigint     null,
    stagiaire_id bigint     null,
    datePrevue   datetime   null,
    mandatory    tinyint(1) null,
    constraint FK6e19t2sf0xefd6lfhm4564e4p
        foreign key (stagiaire_id) references digilearning.UTILISATEUR (ID),
    constraint FK8du4e1bgysgsilwptkafua0b5
        foreign key (cours_id) references digilearning.dl_cours (id)
);

create table digilearning.dl_message
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
        foreign key (conversation_id) references digilearning.dl_conversation (id),
    constraint FKh26t60c07vc8plu5b9jf0qhvo
        foreign key (previous_id) references digilearning.dl_message (id),
    constraint FKnbuyn9bigrthhabsov5nkpciq
        foreign key (emetteur_id) references digilearning.UTILISATEUR (ID),
    constraint FKgwrkoy6a4tlf6c9yvtxhcbgk
        foreign key (emetteur_id) references digilearning.UTILISATEUR (ID),
    constraint FKqwxcdmqpv6umffvcdl771jert
        foreign key (next_id) references digilearning.dl_message (id),
    constraint dl_message_ibfk_1
        foreign key (next_id) references digilearning.dl_message (id),
    constraint dl_message_ibfk_2
        foreign key (previous_id) references digilearning.dl_message (id)
);

create table digilearning.dl_post
(
    id           bigint auto_increment
        primary key,
    contenu      varchar(255) null,
    dateEmission datetime(6)  null,
    emetteur_id  bigint       null,
    constraint FKimeqxtfmyxctslynfourp3x7r
        foreign key (emetteur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_post_forum
(
    id                bigint auto_increment
        primary key,
    auteur_id         bigint        null,
    contenu           varchar(2048) null,
    dateEmission      datetime(6)   null,
    fil_discussion_id bigint        null,
    reponseA_id       bigint        null,
    constraint FK23xnww5xp6nveemdye1njiacd
        foreign key (reponseA_id) references digilearning.dl_post_forum (id),
    constraint FK9cxivt7g88aot86n077xrqb4t
        foreign key (auteur_id) references digilearning.UTILISATEUR (ID),
    constraint FKtdccrmuv21n8iqf84rw4bqwxw
        foreign key (fil_discussion_id) references digilearning.dl_fil_discussion (id)
);

create table digilearning.dl_post_session
(
    session_id bigint not null,
    post_id    bigint not null,
    constraint FKa76gtv4dw1o7wunx0o9cdsky6
        foreign key (post_id) references digilearning.dl_post (id),
    constraint FKs3k65ftt0g0u0oi30d81nu9kt
        foreign key (session_id) references digilearning.SESSION (ID)
);

create table digilearning.dl_qcm_passe
(
    id             bigint auto_increment
        primary key,
    qcm_id         bigint     null,
    utilisateur_id bigint     null,
    archived       tinyint(1) not null,
    datePassage    datetime   not null,
    id_publication bigint     null,
    constraint FKjoog9b2jn9j0oydw2gfgdmd1m
        foreign key (qcm_id) references digilearning.dl_chapitre (id),
    constraint FKnga6an8bb45qjiu9nu2g3prii
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID),
    constraint fk_qcm_publication
        foreign key (id_publication) references digilearning.dl_qcm_publication (id),
    constraint FKpwoo5jqpp51f3gjrh15nrp66t
        foreign key (id_publication) references digilearning.dl_qcm_publication (id)
);

create table digilearning.dl_question
(
    id             bigint auto_increment
        primary key,
    contenu        varchar(255) not null,
    supprimee      bit          not null,
    utilisateur_id bigint       null,
    chapitre_id    bigint       null,
    constraint FKjq1l51wf4nshwwheglxjeqcdy
        foreign key (chapitre_id) references digilearning.dl_chapitre (id),
    constraint FKkxbjlay565h1lysk0sh0e4wbo
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_relation_question
(
    id             bigint auto_increment
        primary key,
    disliked       bit    null,
    liked          bit    null,
    question_id    bigint null,
    utilisateur_id bigint null,
    constraint FK963dsd05d9oe5y1f92kc7ds4h
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID),
    constraint FKnb3swcm9yediexax82erkrx0s
        foreign key (question_id) references digilearning.dl_question (id)
);

create table digilearning.dl_reponse
(
    id          bigint auto_increment
        primary key,
    contenu     varchar(1024) not null,
    question_id bigint        null,
    supprimee   bit           not null,
    auteur_id   bigint        not null,
    constraint FKpo05hpshnfd2j2aal883u9d2c
        foreign key (question_id) references digilearning.dl_question (id),
    constraint dl_reponse_UTILISATEUR__fk
        foreign key (auteur_id) references digilearning.UTILISATEUR (ID),
    constraint FKf3fsvktg4chbdhhygoy7uv38p
        foreign key (auteur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_relation_reponse
(
    id             bigint auto_increment
        primary key,
    disliked       bit    null,
    liked          bit    null,
    reponse_id     bigint null,
    utilisateur_id bigint null,
    constraint FK6ag1pfv55ifvqeeu6k3ubfodi
        foreign key (reponse_id) references digilearning.dl_reponse (id),
    constraint FK8lvbsitmq1wvbcfgvusfi4uy1
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_resultat_question
(
    id          bigint auto_increment
        primary key,
    qcmPasse_id bigint null,
    question_id bigint null,
    constraint FKgkviaxonlo2r5j3jvurp5woyw
        foreign key (qcmPasse_id) references digilearning.dl_qcm_passe (id),
    constraint dl_resultat_question_dl_qcm_question_id_fk
        foreign key (question_id) references digilearning.dl_qcm_question (id),
    constraint FKgjennmtjtkcqvmq7re9b5w1jv
        foreign key (question_id) references digilearning.dl_qcm_question (id)
);

create table digilearning.dl_resultat_choix
(
    resultat_id bigint not null,
    choix_id    bigint not null,
    constraint FK2h28var9qy1lbqv30crmeu64p
        foreign key (resultat_id) references digilearning.dl_resultat_question (id),
    constraint FKk7vwofy4r02udmqv9xyq12iao
        foreign key (choix_id) references digilearning.dl_qcm_choix (id)
);

create table digilearning.dl_utilisateur_conversation
(
    utilisateur_id  bigint not null,
    conversation_id bigint not null,
    constraint FK6kvr5j6sm3q64fu9notdx49li
        foreign key (conversation_id) references digilearning.dl_conversation (id),
    constraint FKjkln4noag4pakv4f2f0oyy0sw
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.dl_utilisateur_cours
(
    id_utilisateur bigint not null,
    id_cours       bigint not null,
    constraint FK3s16tpc2cjf85lemgxmnm192h
        foreign key (id_utilisateur) references digilearning.UTILISATEUR (ID),
    constraint FKgy4sqe8wdrbwoqyirc0ne6trl
        foreign key (id_cours) references digilearning.dl_cours (id)
);

create table digilearning.dl_utilisateur_session
(
    utilisateur_id bigint not null,
    session_id     bigint not null,
    constraint FK9vfa9pex4mr11tlbohsulvpfr
        foreign key (session_id) references digilearning.SESSION (ID),
    constraint FKdnctllhhkk622eplncrn0phdw
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.salon_moderateurs
(
    moderateur_id bigint not null,
    salon_id      bigint not null,
    constraint FK69ewod7rsilq7l3i2eyswuxh7
        foreign key (salon_id) references digilearning.dl_salon (id),
    constraint FK6qg3chjwchaj06cwab4g3j2a3
        foreign key (moderateur_id) references digilearning.UTILISATEUR (ID)
);

create table digilearning.whitelist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK1jnrnh5pst18vw23xqj1sk13j
        foreign key (utilisateur_id) references digilearning.UTILISATEUR (ID),
    constraint FKdf2x3s0i3iedvuf53d51394le
        foreign key (salon_id) references digilearning.dl_salon (id)
);


