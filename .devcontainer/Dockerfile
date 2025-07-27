FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

ENV SSH_AUTH_SOCK=/ssh-agent

RUN apt update && apt install -y --no-install-recommends curl git gnupg2 jq nano unzip wget

EXPOSE 8080
