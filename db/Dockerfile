FROM postgres:12-alpine

COPY ./scripts/database/ /docker-entrypoint-initdb.d/
COPY ./scripts/shared/ /docker-entrypoint-initdb.d/lib


COPY ./.env /docker-entrypoint-initdb.d/.env
