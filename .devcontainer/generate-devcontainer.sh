#!/bin/bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/.. && pwd)"
ENV_FILE="$PROJECT_DIR/.devcontainer/.env"
ENV_EXAMPLE="$PROJECT_DIR/.env.example"
TEMPLATE_FILE="$PROJECT_DIR/.devcontainer/devcontainer.template.json"
OUTPUT_FILE="$PROJECT_DIR/.devcontainer/devcontainer.json"

if [ ! -f "$ENV_FILE" ]; then
    if [ -f "$ENV_EXAMPLE" ]; then
        cp "$ENV_EXAMPLE" "$ENV_FILE"
        echo "🟢 Fichier .env créé à partir de .env.example. Pensez à adapter vos variables si besoin."
    else
        echo "❌ ERREUR : Aucun .env ni .env.example trouvé !"
        exit 1
    fi
fi

# Lecture des ports depuis .env, valeurs par défaut si absent
APP_PORT=$(grep -E '^APP_PORT=' "$ENV_FILE" | cut -d '=' -f2- | xargs || echo "8080")
DB_PORT=$(grep -E '^DB_PORT=' "$ENV_FILE" | cut -d '=' -f2- | xargs || echo "5432")

# Génération du devcontainer.json
sed -e "s/\"{{APP_PORT}}\"/$APP_PORT/g" -e "s/\"{{DB_PORT}}\"/$DB_PORT/g" \
    "$TEMPLATE_FILE" > "$OUTPUT_FILE"

echo "✅ devcontainer.json généré avec APP_PORT=$APP_PORT et DB_PORT=$DB_PORT"
