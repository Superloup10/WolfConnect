# 🐺 WolfConnect

WolfConnect est une plateforme de communication professionnelle entre les employés, clients et la direction d’une
entreprise.

> Planifiez des créneaux, contactez la direction, déléguez aux bonnes personnes en cas d’indisponibilité.
> Sécurisé, extensible, documenté et prêt pour la production ou le cloud.

![CI](https://github.com/superloup10/wolfconnect/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/github/Superloup10/WolfConnect/graph/badge.svg?token=4Y3SCUV35N)](https://codecov.io/github/Superloup10/WolfConnect)

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

### Variables requises dans `.env`

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=wolfconnect-dev
DB_NAME_TEST=wolfconnect-test
DB_POSTGRES_PASSWORD=postgres
DB_MASTER_USERNAME=master
DB_MASTER_PASSWORD=master
DB_APP_USERNAME=app
DB_APP_PASSWORD=app
JWT_SECRET=changeme
APP_PORT=8080
```

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
