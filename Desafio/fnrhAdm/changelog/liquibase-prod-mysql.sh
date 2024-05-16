#!/usr/bin/env bash

java -jar liquibase.jar \
    --changeLogFile=changelog-mysql.xml \
    --driver=com.mysql.jdbc.Driver \
    --classpath=mysql-connector-java-5.0.8.jar\
    --url=jdbc:mysql://srvmysql:3306/fnrh \
    --defaultSchemaName=fnrh \
    --databaseChangeLogTableName=changelog_fnrhadm \
    $@
