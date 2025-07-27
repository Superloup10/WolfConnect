#!/bin/bash

set -e

CREATE_USERS_SQL=${INIT_SQL_PATH:-/docker-entrypoint-initdb.d/01_create_users.sql}

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -f "$CREATE_USERS_SQL"

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_MASTER_USERNAME', '$DB_MASTER_PASSWORD');"

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_APP_USERNAME', '$DB_APP_PASSWORD');"

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "ALTER DATABASE \"$POSTGRES_DB\" OWNER TO \"$DB_MASTER_USERNAME\";"
