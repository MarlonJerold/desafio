--liquibase formatted sql

--changeset sabrina:1.1.0-grant-telendpess-bpreceptor
 grant select on cm.telendpess to bpreceptor with grant option;
-- rollback revoke select on cm.telendpess from bpreceptor;
 
