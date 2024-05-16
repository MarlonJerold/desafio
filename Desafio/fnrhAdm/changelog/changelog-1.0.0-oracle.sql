--liquibase formatted sql



--changeset sabrina:1.0.0-grant-origem-bpreceptor
grant select on cm.origemreserva to bpreceptor with grant option;
-- rollback revoke select on cm.origemreserva from bpreceptor;

