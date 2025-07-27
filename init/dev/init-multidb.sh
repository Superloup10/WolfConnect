#!/bin/bash

set -e

CREATE_USERS_SQL=${INIT_SQL_PATH:-/docker-entrypoint-initdb.d/01_create_users.sql}

if ! psql -h "$DB_HOST" -p "$DB_PORT" -U "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw "$DB_NAME_TEST"; then
    psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
        --username "$POSTGRES_USER" \
        -c "CREATE DATABASE \"$DB_NAME_TEST\" WITH ENCODING = 'UTF8';"
fi

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    --dbname "$DB_NAME_TEST" \
    -f "$CREATE_USERS_SQL"

psql -h "$DB_HOST" -p "$DB_PORT" -v ON_ERROR_STOP=1 \
    --username "$POSTGRES_USER" \
    -c "ALTER DATABASE \"$DB_NAME_TEST\" OWNER TO \"$DB_MASTER_USERNAME\";"
