create table sid.ADMINISTRATION_SESSION (
    ID BIGINT(20) PRIMARY KEY NOT NULL,
    ID_SESSION BIGINT(20),
    ID_UTILISATEUR BIGINT(20),
    STATUS_RESPONSABLE_SESSION TINYINT(1),
    FOREIGN KEY (ID_SESSION) REFERENCES SESSION(ID),
    FOREIGN KEY (ID_UTILISATEUR) REFERENCES UTILISATEUR(ID)
);

create table sid.dl_conversation
(
    id            bigint auto_increment
        primary key,
    libelleGroupe varchar(255) null
);

create table sid.dl_message
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
        foreign key (emetteur_id) references sid.UTILISATEUR (ID),
    constraint FKgwrkoy6a4tlf6c9yvtxhcbgk
        foreign key (emetteur_id) references sid.UTILISATEUR (ID),
    constraint FKqwxcdmqpv6umffvcdl771jert
        foreign key (next_id) references sid.dl_message (id),
    constraint dl_message_ibfk_1
        foreign key (next_id) references sid.dl_message (id),
    constraint dl_message_ibfk_2
        foreign key (previous_id) references sid.dl_message (id)
);

create table sid.dl_module
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) null,
    photo   varchar(255) null
);

create table sid.dl_module_formation
(
    id_module    bigint not null,
    id_formation bigint not null,
    constraint FK1fm74nw6wf5e2kqdkwbwgbnch
        foreign key (id_formation) references sid.FORMATION (ID),
    constraint FK7snjvvky9uljwv1ja952sgiwy
        foreign key (id_module) references sid.dl_module (id)
);

create table sid.dl_post
(
    id           bigint auto_increment
        primary key,
    contenu      varchar(255) null,
    dateEmission datetime(6)  null,
    emetteur_id  bigint       null,
    constraint FKimeqxtfmyxctslynfourp3x7r
        foreign key (emetteur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_post_session
(
    session_id bigint not null,
    post_id    bigint not null,
    constraint FKa76gtv4dw1o7wunx0o9cdsky6
        foreign key (post_id) references sid.dl_post (id),
    constraint FKs3k65ftt0g0u0oi30d81nu9kt
        foreign key (session_id) references sid.SESSION (ID)
);

create table sid.dl_ressource_cours
(
    id             bigint auto_increment
        primary key,
    libelle        varchar(255) null,
    libelleAffiche varchar(255) null,
    typeRessource  varchar(255) null,
    coursRef_id    bigint       null,
    constraint FKl6b10gkxstrr4mf25thpmvdbt
        foreign key (coursRef_id) references sid.COURS_REF (ID)
);

create table sid.dl_sous_module
(
    id    bigint auto_increment
        primary key,
    titre varchar(50) not null,
    photo varchar(50) not null
);

create table sid.dl_cours
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

create table sid.dl_chapitre
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

create table sid.dl_detail_cours
(
    id          bigint auto_increment
        primary key,
    libelle     varchar(255) null,
    chapitre_id bigint       null,
    constraint FK1v4vqnejnh1ylppkk4iyvh8sq
        foreign key (chapitre_id) references sid.dl_chapitre (id)
);

create table sid.dl_flag_cours
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
        foreign key (stagiaire_id) references sid.UTILISATEUR (ID),
    constraint FK8du4e1bgysgsilwptkafua0b5
        foreign key (cours_id) references sid.dl_cours (id)
);

create table sid.dl_module_smodule
(
    id_smodule bigint not null,
    id_module  bigint not null,
    constraint FKo4s0rbwin71gkdqcu30kcofm2
        foreign key (id_module) references sid.dl_module (id),
    constraint FKqjbjngdft52b5hmr473q3dgj2
        foreign key (id_smodule) references sid.dl_sous_module (id)
);

create table sid.dl_question
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
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_relation_question
(
    id             bigint auto_increment
        primary key,
    disliked       bit    null,
    liked          bit    null,
    question_id    bigint null,
    utilisateur_id bigint null,
    constraint FK963dsd05d9oe5y1f92kc7ds4h
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID),
    constraint FKnb3swcm9yediexax82erkrx0s
        foreign key (question_id) references sid.dl_question (id)
);

create table sid.dl_reponse
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
        foreign key (auteur_id) references sid.UTILISATEUR (ID),
    constraint FKf3fsvktg4chbdhhygoy7uv38p
        foreign key (auteur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_relation_reponse
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
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_sujet
(
    id      bigint auto_increment
        primary key,
    libelle varchar(255) null
);

create table sid.dl_salon
(
    id          bigint auto_increment
        primary key,
    statusForum tinyint      null,
    titre       varchar(255) null,
    sujet_id    bigint       null,
    constraint FKl5rv9oj3fcvx35dj8wk3x9n2s
        foreign key (sujet_id) references sid.dl_sujet (id)
);

create table sid.dl_fil_discussion
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
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_post_forum
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
        foreign key (auteur_id) references sid.UTILISATEUR (ID),
    constraint FKtdccrmuv21n8iqf84rw4bqwxw
        foreign key (fil_discussion_id) references sid.dl_fil_discussion (id)
);

create table sid.dl_utilisateur_conversation
(
    utilisateur_id  bigint not null,
    conversation_id bigint not null,
    constraint FK6kvr5j6sm3q64fu9notdx49li
        foreign key (conversation_id) references sid.dl_conversation (id),
    constraint FKjkln4noag4pakv4f2f0oyy0sw
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);

create table sid.dl_utilisateur_cours
(
    id_utilisateur bigint not null,
    id_cours       bigint not null,
    constraint FK3s16tpc2cjf85lemgxmnm192h
        foreign key (id_utilisateur) references sid.UTILISATEUR (ID),
    constraint FKgy4sqe8wdrbwoqyirc0ne6trl
        foreign key (id_cours) references sid.dl_cours (id)
);

create table sid.dl_utilisateur_session
(
    utilisateur_id bigint not null,
    session_id     bigint not null,
    constraint FK9vfa9pex4mr11tlbohsulvpfr
        foreign key (session_id) references sid.SESSION (ID),
    constraint FKdnctllhhkk622eplncrn0phdw
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);

create table sid.whitelist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK1jnrnh5pst18vw23xqj1sk13j
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID),
    constraint FKdf2x3s0i3iedvuf53d51394le
        foreign key (salon_id) references sid.dl_salon (id)
);
create table sid.blacklist
(
    utilisateur_id bigint not null,
    salon_id       bigint not null,
    constraint FK30k32u352ke79p1mrdvyj3wje
        foreign key (salon_id) references sid.dl_salon (id),
    constraint FKs8ovdvbebrxhgh3w4isly7e9y
        foreign key (utilisateur_id) references sid.UTILISATEUR (ID)
);


