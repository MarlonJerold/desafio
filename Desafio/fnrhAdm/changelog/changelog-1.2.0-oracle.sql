--liquibase formatted sql

--changeset sabrina:1.2.0-grant-historicoestada-vendas
 GRANT  INSERT ON CM.HISTORICOESTADA TO vendas with grant option;
-- rollback revoke insert on CM.HISTORICOESTADA from vendas;
 
