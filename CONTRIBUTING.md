# 🤝 Contribuer à WolfConnect

Merci de l’intérêt que vous portez à WolfConnect ! 🎉
Ce guide vous aidera à contribuer efficacement et proprement au projet.

---

## 🧭 Principe général

Le projet utilise **GitFlow** pour gérer les branches et un pipeline CI/CD rigoureux basé sur GitHub Actions.

Les contributions doivent respecter :
- Une qualité de code irréprochable (formatage, lint, tests, couverture)
- Des commits propres et explicites (voir conventions plus bas)
- Une PR vers `develop` **jamais vers `main` directement**

---

## 🪜 Étapes de contribution

1. **Forkez** ce dépôt
2. Clonez votre fork localement
3. Créez une branche :
```bash
git checkout -b feature/ma-super-fonctionnalite
```

4. Développez votre fonctionnalité 🛠️
5. Vérifiez votre code :
```bash
./gradlew ktlintCheck detekt test koverXmlReport dokkaHtml openApiGenerate
```

6. Commitez avec un message clair :
```bash
git commit -m "✨ feat: ajout de la prise de rendez-vous anonymisée"
```

7. Poussez vos modifications :
```bash
git push origin feature/ma-super-fonctionnalite
```

8. Ouvrez une **Pull Request** vers `develop`

---

## 📝 Conventions de Commit

Nous utilisons [Conventional Commits](https://www.conventionalcommits.org/) pour normaliser nos messages de commit.

### Format

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

### Types de Commit

| Type       | Description                                                      |
|------------|------------------------------------------------------------------|
| `feat`     | Nouvelle fonctionnalité                                          |
| `fix`      | Correction de bug                                                |
| `docs`     | Modification de la documentation                                 |
| `style`    | Changements de formatage (espaces, points-virgules, etc.)        |
| `refactor` | Refactoring du code                                              |
| `perf`     | Améliorations de performance                                     |
| `test`     | Ajout ou modification de tests                                   |
| `build`    | Modifications du système de build ou des dépendances             |
| `ci`       | Modifications de la configuration CI                             |
| `chore`    | Autres changements qui ne modifient pas les fichiers src ou test |
| `revert`   | Annulation d'un commit précédent                                 |


### Exemples

```
feat(auth): ajouter l'authentification oauth2
fix(api): corriger le statut http des réponses d'erreur
docs(readme): mettre à jour les instructions d'installation
test(api): ajouter des tests d'intégration pour l'authentification
```

### Validation Locale

Les commits sont automatiquement vérifiés grâce à commitlint. Pour installer les outils :

```bash
npm install
```

---

## ✅ Critères de validation CI

Avant d’être mergée, chaque PR doit :

- Passer tous les jobs CI
- Respecter les conventions de formatage (ktlint, detekt)
- Générer la documentation (OpenAPI + Dokka)
- Fournir une couverture de tests acceptable (>90% sur le patch si possible)
- Ne pas faire baisser la couverture globale

---

## 📚 Liens utiles

- [README](./README.md)
- [Licence EUPL v1.2](./LICENSE)
- [Notice juridique](./NOTICE.md)

---

## 🙏 Merci !

Toute contribution est la bienvenue : bug, doc, suggestion, test, idée ou fonctionnalité.
Merci d’aider à faire de **WolfConnect** une plateforme fiable, moderne et humaine 🐺
