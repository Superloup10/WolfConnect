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

psql_cmd --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -f "$CREATE_USERS_SQL"

psql_cmd --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_MASTER_USERNAME', '$DB_MASTER_PASSWORD');"

psql_cmd --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "SELECT create_user('$DB_APP_USERNAME', '$DB_APP_PASSWORD');"

psql_cmd --username "$POSTGRES_USER" \
    --dbname "$POSTGRES_DB" \
    -c "ALTER DATABASE \"$POSTGRES_DB\" OWNER TO \"$DB_MASTER_USERNAME\";"
