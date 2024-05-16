--liquibase formatted sql

--changeset sabrina:2018-03-13-alter-table-fnrh-uf
ALTER TABLE fnrh.fnrh 
CHANGE COLUMN cd_uf_res cd_uf_res VARCHAR(3) NULL DEFAULT NULL,
CHANGE COLUMN cd_uf_de cd_uf_de VARCHAR(3) NULL DEFAULT NULL,
CHANGE COLUMN cd_uf_para cd_uf_para VARCHAR(3) NULL DEFAULT NULL,
CHANGE COLUMN cd_pais_res cd_pais_res VARCHAR(3) NULL DEFAULT NULL,
CHANGE COLUMN cd_pais_de cd_pais_de VARCHAR(3) NULL DEFAULT NULL,
CHANGE COLUMN cd_pais_para cd_pais_para VARCHAR(3) NULL DEFAULT NULL;
-- rollback ALTER TABLE fnrh.fnrh CHANGE COLUMN cd_uf_res cd_uf_res VARCHAR(2) NULL DEFAULT NULL, CHANGE COLUMN cd_uf_de cd_uf_de VARCHAR(2) NULL DEFAULT NULL, CHANGE COLUMN cd_uf_para cd_uf_para VARCHAR(2) NULL DEFAULT NULL, CHANGE COLUMN cd_pais_res cd_pais_res VARCHAR(2) NULL DEFAULT NULL, CHANGE COLUMN cd_pais_de cd_pais_de VARCHAR(2) NULL DEFAULT NULL, CHANGE COLUMN cd_pais_para cd_pais_para VARCHAR(2) NULL DEFAULT NULL;


