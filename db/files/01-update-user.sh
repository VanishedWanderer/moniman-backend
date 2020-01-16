#!/bin/bash
set -e
echo "Updating User: quotesc with new password"

psql -v ON_ERROR_STOP=1 --username "quotesc" --dbname "quotesc" <<-EOSQL
  ALTER USER quotesc WITH PASSWORD '$QUOTESC_PASSWORD'
EOSQL
