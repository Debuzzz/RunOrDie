# Projet : Run or Die 🧟

Une association d'événementiel sportif et décalé organise des **zombie runs** : des courses où des coureurs doivent traverser (en général sur une nuit) un parcours infesté de zombies bénévoles. L'objectif est de développer le **backend** d'une application pour gérer les éditions de l'événement, les affectations de zombies et les inscriptions des coureurs.

---

## Rôles et fonctionnalités

### Organisateur

L'organisateur gère les **éditions** de l'événement.

Une **édition** possède :
- Un nom (ex : *Run or Die — Édition Automne*)
- Une date
- Une heure de début et une heure de fin
- Un lieu
- Une capacité maximale de coureurs
- Une capacité maximale de zombies

L'organisateur peut créer, visualiser et supprimer des éditions.

Contraintes :
- La date d'une édition doit être dans le futur
- L'heure de fin doit être après l'heure de début
- Deux éditions ne peuvent pas se chevaucher dans le temps
- Une édition ne peut pas être supprimée s'il y a des inscrits — il faut d'abord l'annuler, ce qui déclenche l'envoi d'un mail à tous les inscrits (bouchonné par un `System.out.println`)

---

### Utilisateurs

- Les utilisateurs s'identifient sur la plateforme avec un email et un mot de passe. Une fois identifiés, ils peuvent s'inscrire à des événements en tant que Zombie ou Coureur.  
- Pas besoin d'une double vérification de mot de passe, juste une simple API permettant de créer un utilisateur est suffisant.

---

### Zombie

Les zombies s'affectent eux-mêmes à une édition pour jouer les monstres sur le parcours.

Contraintes :
- Un zombie ne peut s'affecter qu'à des éditions futures
- Un zombie s'affecte sur des plages de 1h minimum, il ne peut pas s'affecter plusieurs fois sur la même plage horaire.
- Un zombie peut visualiser tous les créneaux/courses auxquels il s'est inscrit/peut s'inscrire
- Il doit être possible pour tout le monde d'afficher un histogramme (format JSON) heure par heure du nombre de zombies présents à l'événement
- A tout moment, une édition ne peut pas accueillir plus de zombies que sa capacité maximale
- Un zombie ne peut pas s'affecter s'il est déjà inscrit en tant que coureur

---

### Coureur

Les coureurs s'inscrivent aux éditions.

Ils peuvent :
- Visualiser la liste des éditions à venir avec le nombre de places restantes
- S'inscrire à une édition

Contraintes :
- Un coureur ne peut pas s'inscrire à une édition passée
- Un coureur ne peut pas s'inscrire deux fois à la même édition
- Un coureur peut visualiser toutes les courses auxquelles il s'est inscrit/peut s'inscrire
- Le nombre total de coureurs inscrits ne peut pas dépasser la capacité du lieu
- Un coureur ne peut pas s'inscrire à une course s'il y est déjà inscrit en tant que zombie
- Pour toute inscription, le coureur doit être **licencié** auprès de "la fédération des survivants". Une API de vérification de licence sera bouchonnée par un `System.out.println`. Il n'est pas possible de prendre une licence plus d'une fois.

---

## Jeu de données initial

L'application doit contenir au démarrage **2 éditions** avec quelques affectations et inscriptions déjà existantes, de façon à ce qu'une majorité de scénarios soient testables immédiatement.

---

## Utilisateurs

Voici deux comptes à utiliser pour accéder aux différentes APIs.

| Username        		  | Password   | Rôle                      			|
|-------------------------|------------|------------------------------------|
| `mastermind@epita.fr`	  | `brains`   | Organisateur              			|
| `forrest@epita.fr`      | `run`      | Utilisateur			   			|

NB : N'hésitez pas à ajouter plus de personnes inscrites aux courses pour rendre votre jeu de données plus "vivant". Ces comptes sont uniquement obligatoires afin que le correcteur puisse tester vos APIs.

---

## Technologies

- Java 21 + Spring Boot > 3.0
- Spring Web, Spring Data JPA, Spring Security
- Maven
- Base de données en mémoire H2
- Architecture monolithique en **4 couches distinctes** modélisés par des **modules maven**

---

## Rendu

- Lien vers un dépôt Git contenant le code source (accès lecture pour le correcteur obligatoire)
- Les participants listés dans le `pom.xml` à l'emplacement approprié
- Un fat-jar auto-exécutable dans un dossier `/jar` à la racine du projet
- L'URL du Swagger UI (ou une documentation d'API explicite)
- Un fichier `readme.md` à la racine avec toute information utile au correcteur

---

## Points d'attention

Le sujet est volontairement simple techniquement. L'accent est mis sur la **qualité du code et sa maintenabilité** :

- Nommage clair des classes, variables et méthodes
- Méthodes courtes, claires et auto-portantes
- Application des principes SOLID
- Peu de duplication (DRY)
- Pas de code mort (YAGNI)
- Bonnes pratiques de conception d'API REST

Les tests automatisés et la Javadoc ne sont pas requis. Le code doit être suffisamment lisible pour se passer d'explications.

N'hésitez pas à ajouter des API que vous jugez nécessaires et **qui ne sont pas explicitement dictées ci-dessus**.

Pour plus de simplicité, toutes les plages horaires commencent et terminent à des heures fixes.

**Tout projet suspicieusement similaire sera sanctionné.**
