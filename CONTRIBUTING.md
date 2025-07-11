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
