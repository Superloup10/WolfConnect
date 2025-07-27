#!/bin/bash

set -euo pipefail

echo "🚀 Initialisation du Dev Container..."

PROJECT_DIR="/workspace/WolfConnect"

if [ -f "$PROJECT_DIR/package.json" ]; then
    echo "📦 Installation des dépendances Node.js..."
    cd "$PROJECT_DIR/"
    npm install

    if [ -f "$PROJECT_DIR/.husky/pre-commit" ]; then
        npm run prepare
        echo "✅ Husky prêt"
    fi
else
    echo "⚠️ Aucun package.json trouvé, skip npm install"
fi

cd "$PROJECT_DIR/"
if [ -f "gradlew" ] && ([ -f "build.gradle.kts" ] || [ -f "settings.gradle.kts" ]); then
    echo "☕ Préparation du projet Gradle (résolution des dépendances)..."
    ./gradlew tasks --quiet
    echo "✅ Dépendances Gradle prêtes"
else
    echo "⚠️ Aucun projet Gradle trouvé, skip initialisation Gradle"
fi

if [ "${APPLY_FLYWAY_MIGRATIONS:-0}" = "1" ]; then
    if [ -f "gradlew" ]; then
        if ./gradlew tasks --all | grep -q "flywayMigrate"; then
            echo "🦅 Application des migrations Flyway (à la demande)..."
            ./gradlew flywayMigrate
            echo "✅ Migrations Flyway appliquées"
        else
            echo "ℹ️ Tâche Gradle 'flywayMigrate' introuvable : Flyway n'est probablement pas activé dans ce projet."
            echo "   Aucune migration BDD n'a été lancée."
        fi
    else
        echo "⚠️ Aucun wrapper Gradle détecté, migration ignorée"
    fi
else
    echo "ℹ️ Les migrations Flyway ne sont PAS appliquées automatiquement."
    echo "   Pour les lancer, modifiez la variable APPLY_FLYWAY_MIGRATIONS à 1 dans votre DevContainer et reconstruisez,"
    echo "   ou lancez ./gradlew flywayMigrate manuellement dans un terminal si la tâche existe."
fi

echo "🎉 Dev Container prêt à l'emploi !"
