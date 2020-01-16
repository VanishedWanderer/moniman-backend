#!/bin/bash
set -e
echo "Updating User: moniman with new password"

psql -v ON_ERROR_STOP=1 --username "moniman" --dbname "moniman" <<-EOSQL
  ALTER USER moniman WITH PASSWORD '$MONIMAN_PASSWORD'
EOSQL
