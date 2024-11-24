# Citronix

Citronix est une application de gestion pour les fermes de citrons. Elle permet aux fermiers de suivre la production, la récolte, et la vente de leurs produits, tout en optimisant la productivité de chaque arbre en fonction de son âge.

## Fonctionnalités Principales

### Gestion des Fermes

- **CRUD complet** : Créer, modifier, consulter, et supprimer des informations sur les fermes (nom, localisation, superficie, date de création).
- **Recherche multicritère** : Permet de filtrer les fermes en fonction de plusieurs critères.

### Gestion des Champs

- Associer des champs à une ferme avec des superficies définies.
- Validation des superficies :
  - La somme des superficies des champs d'une ferme doit être strictement inférieure à celle de la ferme.
  - Superficie minimale : 0,1 hectare (1 000 m²).
  - Superficie maximale : 50 % de la superficie totale de la ferme.
- Limitation du nombre de champs à 10 par ferme.

### Gestion des Arbres

- Suivi des arbres avec leur date de plantation, leur âge, et le champ auquel ils appartiennent.
- Calcul de l'âge des arbres.
- Détermination de la productivité annuelle des arbres :
  - **Jeunes arbres (< 3 ans)** : 2,5 kg/saison.
  - **Arbres matures (3-10 ans)** : 12 kg/saison.
  - **Vieux arbres (> 10 ans)** : 20 kg/saison.
- Période de plantation : mars à mai uniquement.

### Gestion des Récoltes

- Suivi des récoltes par saison (hiver, printemps, été, automne), avec une récolte unique par saison.
- Enregistrement des détails par arbre pour chaque récolte.

### Gestion des Ventes

- Enregistrement des ventes avec la date, le prix unitaire, le client, et la récolte associée.
- Calcul automatique du revenu : `Revenu = quantité * prixUnitaire`.

## Contraintes Métier

- **Espacement entre les arbres** : Maximum 100 arbres par hectare.
- **Durée de vie des arbres** : Les arbres deviennent non productifs après 20 ans.
- **Récolte** : Un arbre ne peut être inclus dans plus d'une récolte par saison.
- **Gestion des données** : Validation stricte avec annotations Spring.

## Exigences Techniques

- **Framework** : Spring Boot pour l'API REST.
- **Architecture** : Architecture en couches (Controller, Service, Repository, Entity).
- **Validation des données** : Utilisation d'annotations Spring.
- **Gestion des exceptions** : Gestion centralisée avec des handlers dédiés.
- **Tests** :
  - Tests unitaires avec **JUnit** et **Mockito**.
  - Validation approfondie des fonctionnalités et des contraintes métier.
- **Outils supplémentaires** :
  - **Lombok** et **Builder Pattern** pour simplifier la gestion des entités.
  - **MapStruct** pour la conversion entre entités, DTO, et View Models.

## Installation et Utilisation

1. **Prérequis** :

   - Java 8 ou version supérieure.
   - Maven 3.x pour la gestion des dépendances.

2. **Cloner le projet** :

   ```bash
   git clone https://github.com/anwar-bouchehboun/farm_management_Citronix_Youcode.git
   cd citronix

   ```

3. **Construire le projet** :
   `mvn clean install`

4. **Lancer l'application** :

```
mvn spring-boot:run
mvn  spring-boot:run -Dspring-boot.run.profiles=prod
mvn  spring-boot:run -Dspring-boot.run.profiles=dev
```

## Architecture

- **Controller** : Gère les requêtes entrantes et les réponses sortantes.
- **Service** : Gère les logiques métier.
- **Repository** : Gère les interactions avec la base de données.
- **Entity** : Modèle de données.
- **Tests** : Tests unitaires et de fonctionnalités.
- **Outils** : Lombok, Builder Pattern, MapStruct.
- **Validation** : Annotations Spring.
- **Gestion des exceptions** : Handlers dédiés.
- **Configuration** : Profils de configuration (dev, prod).

- **Profile** : `prod` pour l'environnement de production, `dev`
  pour l'environnement de développement.
- **Utilisation** : Utiliser un outil comme Postman pour envoyer des requêtes
  à l'API.
- **Tests** : Exécuter les tests unitaires avec JUnit et Mockito.
- **Validation** : Valider les fonctionnalités et les contraintes métier
- **Gestion des exceptions** : Gérer les exceptions avec des handlers dédiés.
- **Gestion des données** : Valider les données avec annotations Spring.
- **Gestion des logs** : Utiliser un outil comme Logback pour la gestion des
  logs.

- **Exécuter le fichier JAR** : Une fois la construction terminée, le fichier JAR exécutable se trouvera dans le répertoire target :
  `java -jar out\artifacts\citronix_jar`

- **Planification** :

```
https://anouarab95.atlassian.net/jira/software/projects/CIT/boards/15
```

- **Schéma d'architecture** :

```src/
├── main/
│   ├── java/
│   │   └── com/projet/citronix/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
├           |── mapper/
│   │       ├── exception/
│   │       ├── repository/
│   │       ├── service/
│   │       │   ├── impl/
│   │       │   └── interfaces/
│   │       └── CitronixApplication.java
│   └── resources/
│       └── application.yaml
        └── application-dev.yaml
        └── application-prod.yaml
```
