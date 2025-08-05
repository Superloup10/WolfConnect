# 🐺 WolfConnect

WolfConnect est une plateforme de communication professionnelle entre les employés, clients et la direction d’une
entreprise.

> Planifiez des créneaux, contactez la direction, déléguez aux bonnes personnes en cas d’indisponibilité.
> Sécurisé, extensible, documenté et prêt pour la production ou le cloud.

![CI](https://github.com/superloup10/wolfconnect/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/github/Superloup10/WolfConnect/graph/badge.svg?token=4Y3SCUV35N)](https://codecov.io/github/Superloup10/WolfConnect)
[![SonarCloud Status](https://sonarcloud.io/api/project_badges/measure?project=Superloup10_WolfConnect&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Superloup10_WolfConnect)

---

## 🚀 Fonctionnalités

- 📅 Gestion des créneaux de disponibilité
- 💬 Messagerie interne dirigée (CEO / secrétaire / autre)
- 🛡️ Authentification sécurisée avec option d’anonymisation
- 📚 API REST documentée avec OpenAPI
- 🔍 Traçabilité et audit intégrés
- ⚙️ Stack reproductible (Devcontainer, Docker)

---

## 🧱 Technologies

| Composant         | Stack                                        |
|-------------------|----------------------------------------------|
| Backend           | [Ktor 3.2.1](https://ktor.io) (Kotlin 2.2.0) |
| Base de données   | PostgreSQL via R2DBC                         |
| CI/CD             | GitHub Actions, Codecov                      |
| Documentation API | Dockka, OpenAPI, Swagger UI                  |

---

## 🛠️ Prérequis

- JDK 21
- Docker & Docker Compose
- PostgreSQL (local ou conteneurisé)
- Fichier `.env` à la racine du projet

### Variables d'environnement

➡️ **Toutes les variables requises sont listées et expliquées dans [`./.env.example`](./.env.example).**

---

#### 🗄️ **Base de données (PostgreSQL)**

| Variable              | Rôle                             | Exemple              |
|-----------------------|----------------------------------|----------------------|
| DB_HOST               | Host Postgres (dans Docker)      | `postgres`           |
| DB_PORT               | Port Postgres (dans Docker)      | `5432`               |
| DB_NAME               | Nom de la base principale        | `wolfconnect-dev`    |
| DB_NAME_TEST          | Nom de la base de test           | `wolfconnect-test`   |
| DB_POSTGRES_PASSWORD  | Mot de passe superadmin Postgres | `postgres`           |
| DB_MASTER_USERNAME    | Utilisateur DB "maître"          | `master`             |
| DB_MASTER_PASSWORD    | Mot de passe utilisateur maître  | `master`             |
| DB_APP_USERNAME       | Utilisateur applicatif           | `app`                |
| DB_APP_PASSWORD       | Mot de passe utilisateur app     | `app`                |

---

#### 🌍 **Accès externe (hors Docker/CI/scripts locaux)**

| Variable     | Rôle                                     | Exemple     |
|--------------|------------------------------------------|-------------|
| DB_HOST_EXT  | Host Postgres vu depuis le host/local/CI | `localhost` |
| DB_PORT_EXT  | Port Postgres exposé localement/CI       | `5443`      |
| APP_PORT_EXT | Port appli exposé localement/forwards    | `8081`      |

---

#### 🚦 **Serveur application**

| Variable | Rôle                                       | Exemple |
|----------|--------------------------------------------|---------|
| APP_PORT | Port HTTP interne à l’application (Docker) | `8080`  |

---

#### 🔒 **Sécurité**

| Variable    | Rôle             | Exemple     |
|-------------|------------------|-------------|
| JWT_SECRET  | Clé secrète JWT  | changeme    |

---

> **Pensez à adapter les valeurs \*\_EXT si vos ports sont déjà utilisés sur votre machine.**
> **Ne commitez jamais de secrets “réels” dans ce fichier !**

---

## ⚡ Lancement rapide

### Avec Devcontainer (recommandé)

1. Installez Docker Desktop
2. Ouvrez le projet avec :
    - VS Code + extension Dev Containers
    - ou JetBrains Gateway
3. Cliquez sur “Ouvrir dans Dev Container”

Cela configure automatiquement :

- `.env` dans `.devcontainer/`
- Dépendances Node
- Hooks Git (Husky)
- Base PostgreSQL et config Kotlin/Gradle

### Sans Devcontainer

1. Copier `.env.example` → `.env` à la racine
2. Compléter les valeurs manquantes
3. Démarrer via :

```bash
docker-compose -f docker-compose.prod.yml up -d
```

---

## 🐳 Docker & Docker Compose

- `.devcontainer/docker-compose.dev.yml` → utilisé automatiquement en Devcontainer
- `docker-compose.prod.yml` → utilisé pour des démos, des tests ou un déploiement

---

## 🔬 Tests, analyse et documentation

- **Tests unitaires / intégration :**

```bash
./gradlew test
```

- **Lint / Analyse statique :**

```bash
./gradlew ktlintCheck detekt
```

- **Migration BDD :**

```bash
./gradlew flywayMigrate
```

- **Génération de couverture et documentation :**

```bash
./gradlew koverXmlReport dokkaHtml
```

Le rapport de couverture Kover est dans `build/reports/kover/`.
La documentation HTML générée par Dokka est dans `build/dokka/html/`.

---

## 🔁 GitFlow

| Branche     | Usage                         |
|-------------|-------------------------------|
| `main`      | Production                    |
| `develop`   | Intégration / Préproduction   |
| `feature/*` | Nouvelles fonctionnalités     |
| `hotfix/*`  | Correctifs urgents            |
| `release/*` | Préparation de version stable |

---

## 📚 Documentation

- 📄 Documentation HTML (Dokka) : artefact CI
- 📘 Spécification OpenAPI : artefact CI (`build/api-spec/openapi.yaml`)
- 🔗 Swagger UI (local) : http://localhost:8080/docs

---

## 🙌 Contribuer

Consultez [`CONTRIBUTING.md`](CONTRIBUTING.md) pour connaître les bonnes pratiques de contribution.

---

## 📜 Licence

Ce projet est distribué sous licence **EUPL v1.2**.
Voir aussi [`NOTICE.md`](NOTICE.md) pour les obligations spécifiques.
