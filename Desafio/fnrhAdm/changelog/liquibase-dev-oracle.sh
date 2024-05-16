#!/usr/bin/env bash

java -jar liquibase.jar \
    --changeLogFile=changelog-oracle.xml \
    --driver=oracle.jdbc.OracleDriver \
    --classpath=ojdbc6.jar \
    --url=jdbc:oracle:thin:@dbdesenv.bpark.com.br:1521:bdbp \
    --username=cm \
    --password=cm \
    --defaultSchemaName=cm \
    --databaseChangeLogTableName=CHANGELOG_FNRHADM \
    $@
