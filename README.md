# 🐺 WolfConnect

WolfConnect est une plateforme de communication professionnelle entre les employés, clients et la direction d’une entreprise.

> Planifiez des créneaux, contactez la direction, déléguez aux bonnes personnes en cas d’indisponibilité.
> Sécurisé, extensible, documenté et prêt pour la production.

![CI](https://github.com/superloup10/wolfconnect/actions/workflows/ci.yml/badge.svg)
[![codecov](https://codecov.io/github/Superloup10/WolfConnect/graph/badge.svg?token=4Y3SCUV35N)](https://codecov.io/github/Superloup10/WolfConnect)

---

## 🚀 Fonctionnalités

- 📅 Gestion des créneaux de disponibilité
- 💬 Messagerie interne dirigée (CEO / secrétaire / autre)
- 🛡️ Authentification sécurisée avec option d’anonymisation
- 📚 API REST documentée avec OpenAPI
- 🔍 Traçabilité et audit intégrés

---

## 🧱 Technologies

| Composant       | Stack                                        |
|-----------------|----------------------------------------------|
| Backend         | [Ktor 3.2.1](https://ktor.io) (Kotlin 2.2.0) |
| Base de données | PostgreSQL via HikariCP                      |
| CI/CD           | GitHub Actions + Codecov                     |
| Docs API        | OpenAPI + Swagger UI                         |
| Docs code       | Dokka                                        |

---

## 🛠️ Prérequis

- JDK 21
- Docker & Docker Compose (optionnel)
- PostgreSQL (local ou conteneur)
- Fichier `.env` à la racine du projet

### Variables requises dans `.env`

```env
DB_URL=jdbc:postgresql://localhost:5432/wolfconnect
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=changeme
PORT=8080
```

---

## 🧪 Lancer les tests

```bash
./gradlew test
```

Pour générer les rapports :

```bash
./gradlew koverXmlReport dokkaHtml
```

Les rapports sont disponibles dans le dossier `build/`.

---

## 🐳 Docker

```bash
docker build -t wolfconnect .
docker run -p 8080:8080 --env-file .env wolfconnect
```

---

## 🔁 GitFlow

- `main` : production
- `develop` : pré-prod / intégration
- `feature/*` : nouvelles fonctionnalités
- `hotfix/*` : correctifs urgents
- `release/*` : préparation de version

---

## 📚 Documentation

- 📄 Documentation HTML (Dokka) : artefact CI
- 📘 Spécification OpenAPI : artefact CI (`build/api-spec/openapi.yaml`)
- 🔗 Swagger UI (local) : [http://localhost:8080/docs](http://localhost:8080/docs)

---

## 🙌 Contribuer

Consultez le fichier [`CONTRIBUTING.md`](CONTRIBUTING.md) pour savoir comment contribuer efficacement au projet.

---

## 📜 Licence

Ce projet est sous licence **EUPL v1.2**. Voir le fichier [`LICENSE`](LICENSE).

> Voir également [`NOTICE.md`](NOTICE.md) pour les détails juridiques spécifiques au projet.
