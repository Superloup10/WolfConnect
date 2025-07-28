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

# Récupération des port et host externe
APP_PORT_EXT=$(grep -E '^APP_PORT_EXT=' "$ENV_FILE" | cut -d '=' -f2- | cut -d '#' -f1 | xargs || echo "8080")
DB_PORT_EXT=$(grep -E '^DB_PORT_EXT=' "$ENV_FILE" | cut -d '=' -f2- | cut -d '#' -f1 | xargs || echo "5432")

# Génération du devcontainer.json
sed \
    -e "s/\"{{APP_PORT_EXT}}\"/$APP_PORT_EXT/g" \
    -e "s/\"{{DB_PORT_EXT}}\"/$DB_PORT_EXT/g" \
    "$TEMPLATE_FILE" > "$OUTPUT_FILE"

echo "✅ devcontainer.json généré avec APP_PORT_EXT=$APP_PORT_EXT et DB_PORT_EXT=$DB_PORT_EXT"
