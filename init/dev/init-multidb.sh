#!/bin/bash

set -e

CREATE_USERS_SQL=${INIT_SQL_PATH:-/docker-entrypoint-initdb.d/01_create_users.sql}

PG_HOST="${INIT_DB_HOST:-${DB_HOST_EXT:-${DB_HOST:-localhost}}}"
PG_PORT="${INIT_DB_PORT:-${DB_PORT_EXT:-${DB_PORT:-5432}}}"

if ! psql -h "$PG_HOST" -p "$PG_PORT" -U "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw "$DB_NAME_TEST"; then
    psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
        --username "$POSTGRES_USER" \
        -c "CREATE DATABASE \"$DB_NAME_TEST\" WITH ENCODING = 'UTF8';"
fi

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$DB_NAME_TEST" \
    -f "$CREATE_USERS_SQL"

psql -h "$PG_HOST" -p "$PG_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    -c "ALTER DATABASE \"$DB_NAME_TEST\" OWNER TO \"$DB_MASTER_USERNAME\";"
