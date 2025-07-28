#!/bin/bash

set -e

CREATE_USERS_SQL=${INIT_SQL_PATH:-/docker-entrypoint-initdb.d/01_create_users.sql}

psql_cmd() {
    if [ -n "$INIT_DB_HOST" ] && [ -n "$INIT_DB_PORT" ]; then
        psql -h "$INIT_DB_HOST" -p "$INIT_DB_PORT" -v ON_ERROR_STOP=1 "$@"
    else
        psql -v ON_ERROR_STOP=1 "$@"
    fi
}

if ! psql_cmd -U "$POSTGRES_USER" -lqt | cut -d \| -f 1 | grep -qw "$DB_NAME_TEST"; then
    psql_cmd --username "$POSTGRES_USER" \
        -c "CREATE DATABASE \"$DB_NAME_TEST\" WITH ENCODING = 'UTF8';"
fi

psql_cmd --username "$POSTGRES_USER" \
    --dbname "$DB_NAME_TEST" \
    -f "$CREATE_USERS_SQL"

psql_cmd --username "$POSTGRES_USER" \
    -c "ALTER DATABASE \"$DB_NAME_TEST\" OWNER TO \"$DB_MASTER_USERNAME\";"
