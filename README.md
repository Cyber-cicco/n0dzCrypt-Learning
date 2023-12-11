# Digilearing

## Qu'est-ce que Digilearing ?

Digilearning est la pateforme de e-learning de Diginamic. C'est ici que les formateurs peuvent mettre en ligne des cours au format markdown, partager des pdfs, des exercices pour les journées de e-learning des stagiaires Diginamic, et des vidéos d'explication pour les cours.

Coté stagiaire, c'est ici qu'il va pouvoir avoir accès aux cours e-learning de Diginamic. Il pourra en avoir imposé par sa formation, mais il pourra également y avoir accès en dehors des cours obligatoires. 

Il pourra également sur ce site discuter avec d'autres membres de la session, mais également de la formation, pourra prendre contact avec son responsable pédagogique, pourra poser des questions spécifiquement sur certains et consulter les questions et réponses que d'autres apprenants ont pu apportés lors de la formation.

On peut également imaginer qu'une personne qui ne soit pas inscrite à Diginamic ait également accès à ce site pour consulter des cours d'introduction à la programmation.

## Pourquoi Digilearning ?

L'idée initiale vient d'un feedbakc que tout le monde m'a fait sur les journées de e-learning : linkedin learning, c'est vraiment très mauvais. Le fait que Diginamic soit dépendant de cela pour certaines journées de cours écorne également l'image du centre de formation, qui pourtant se démarque en temps normal de la concurrence par la qualité des cours dispensés.

Ainsi, si ce feedback est donné par les élèves, ce n'est pas un membre de Diginamic ou Tecken qui fera la demande de création de cette application. Raison pour laquelle j'ai décidé de proposer cette idée.

De plus, même les plateformes spécialisées dans le e-learning comme Studi, centre de formation auquel j'ai pu travaillé, souffrent d'une qualité de site assez mauvaise, avec un front en AngularJS qui doit être une horreur à maintenir, vu le nombre de bugs et d'inconsistances que l'on peut y rencontrer.

Permettre à Diginamic de pouvoir proposer un site internet de formation un niveau au dessus de la concurrence, et potentiellement pouvoir mettre en libre accès quelques cours d'introduction à la programmation pour des personnes extérieures au centre de formation permettrait à Diginamic de se démarquer de la concurrence. Diginamic dispensant des cours de qualité, mais ayant surtout du mal à se faire reconnaitre, proposer ce genre de vitrine permettrait au centre de se faire reconnaitre.

Enfin, on pourrait imaginer, si les feedbacks sur le site et les cours en e-learning sont bons, dispenser des formations mettant une plus grande emphase sur le e-learning, on pourrait vendre les cours à des entreprises souhaitant former des développeurs sur des technologies particulières, vendre des cours à l'unité pour des personnes souhaitant se former personnellement, etc.

Le but de digi-learning est donc de créer un site non pas pour faciliter la gestion de l'informatique interne de Diginamic, mais pour servir d'interface avec ses clients, et aider à la croissance de l'entreprise en augmentant sa visibilité, en augmentant la qualité du produit délivré, et en diversifiant les prestations proposées sans avoir besoin de nouvelles mains d'oeuvre ou de se former sur de nouvelles technologies.

## Comment Digilearning ?

Voici une démonstration du fonctionnement de Digilearning

### Le login

En tant qu'apprenant, je commence par me connecter à mon compte sur cette page :

![page de login](img/login.png)

### Ma page d'accueil

Une fois connecté, je tombe sur une page d'accueil, avec une brève présentation du site, un rappel de mes cours prévus aujourd'hui, des cours que j'ai ajouté à mes favoris, et un lien rapide vers d'autres sections du site, dont mon agenda, ma messagerie et le forum des apprenants. A partir de la liste des cours du jours ou de mes favoris, je peux directement aller consulter ces cours en cliquant sur leur nom. Je peux également les marquer comme terminé avec la petite checkbox verte pour les cours du jours, ou retirer les cours de mes favoris avec la petit icône de marque page.

![page d'accueil](img/accueil.png)

Mes cours du jours sont déterminés par l'un des points principaux de ce site: l'agenda

### Mon agenda

![agenda](img/agenda.png)

L'agenda me présente à gauche la liste des cours auxquelles j'ai accès. Pour l'instant ils sont triés d'une façon fixe et il est impossible de faire une recherche, mais un champ de recherche et la possibilité de faire différents tris sont à prévoir. 

Ces cours, je peux les glisser-déposer sur le calendrier à droite pour créer mon emploi du temps. Il est également prévu que des formateurs puissent glisser déposer certains cours sur l'emploi du temps des élèves. Ceux-ci ne seraient alors pas modifiable par les élèves.

Les cours ont tous une durée estimée, infulant sur la place qu'ils prennent sur le calendrier. Pour l'instant, pour des raisons de simplicité à produire un premier rendu, la durée est comptée en heures, et non en minutes.

Il est possible de marquer un cours comme terminé sur le calendrier grâce à la petite icone en haut à droit de chaque cours.

Il est possible de naviguer vers le cours en cliquant sur son nom.

Il est possible de changer la date prévue d'un cours, en le glissant d'une date à l'autre. Il est également possible de retirer un cours de son agenda en le passant du calendrier à la colonne de gauche.

### Mes cours

Mes cours sont organisés selon différents modules et sous modules.

