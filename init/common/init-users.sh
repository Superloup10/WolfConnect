#!/bin/bash

set -e

CREATE_USERS_SQL=${INIT_SQL_PATH:-/docker-entrypoint-initdb.d/01_create_users.sql}

PG_HOST="${INIT_DB_HOST:-${DB_HOST_EXT:-${DB_HOST:-localhost}}}"
PG_PORT="${INIT_DB_PORT:-${DB_PORT_EXT:-${DB_PORT:-5432}}}"

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -f "$CREATE_USERS_SQL"

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_MASTER_USERNAME', '$DB_MASTER_PASSWORD');"

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_APP_USERNAME', '$DB_APP_PASSWORD');"

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "ALTER DATABASE \"$POSTGRES_DB\" OWNER TO \"$DB_MASTER_USERNAME\";"
