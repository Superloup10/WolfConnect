# Dockerfile pour WolfConnect

# Étape 1: Build de l'application Kotlin
FROM gradle:8.14.2-jdk21 as builder

WORKDIR /app

# Copier les fichiers de build en premier pour bénéficier du cache
COPY build.gradle.kts ./
COPY settings.gradle.kts ./
COPY gradle.properties ./
COPY gradle ./gradle
COPY gradle/libs.versions.toml ./gradle/libs.versions.toml

# Télécharger les dépendances
RUN gradle dependencies || true

# Copier le reste du code
COPY . .

# Build en mode production
RUN gradle installDist --no-daemon

# Étape 2: Création de l'image exécutable minimale
FROM eclipse-temurin:21-JRE

ENV APP_HOME=/opt/wolfconnect
WORKDIR $APP_HOME

# Copier l'image compilée
COPY --from=builder /app/build/install/wolfconnect .

# Copier le fichier de configuration par défaut
COPY src/main/resources/application.yaml ./config/application.yaml

# PORT par défaut (modifiable)
EXPOSE 8080

# LANCEMENT de l'application
CMD ["./bin/wolfconnect"]
