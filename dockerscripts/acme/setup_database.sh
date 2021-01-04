#!/usr/bin/env bash
# Wait for database to startup 
sleep 70
./opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P catuser@123 -i setup.sql
