#!/usr/bin/env bash


wdir=$(readlink -f $(dirname $BASH_SOURCE))
cd $wdir
source $wdir/lib/shellib.sh
source $wdir/.env
#rm $wdir/.env

eech "${Yel}Welcome to Quotesc Database creator"

eech "${DB_SUPERUSER} ${DB_SUPERUSER_PASSWORD}"

eech "${BBlu} == Creating User"
execsql ${DB_SUPERUSER} ${DB_SUPERUSER_PASSWORD} ./creates/00-create-user.sql
eech "${BBlu} == Changing Users Password to value specified in .env"
source ./creates/01-update-user.sh
