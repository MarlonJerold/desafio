#!/usr/bin/env bash

java -jar liquibase.jar \
    --changeLogFile=changelog-oracle.xml \
    --driver=oracle.jdbc.OracleDriver \
    --classpath=ojdbc6.jar \
    --url=jdbc:oracle:thin:@bparkto-cluster.bpark.com.br:1521:bdbp \
    --defaultSchemaName=cm \
    --databaseChangeLogTableName=CHANGELOG_FNRHADM \
    $@
