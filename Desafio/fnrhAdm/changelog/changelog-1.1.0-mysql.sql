--liquibase formatted sql

--changeset sabrina:1.1.0-alter-campos-telefone
ALTER TABLE fnrh.fnrh CHANGE COLUMN ddi_telefone ddi_telefone VARCHAR(4), CHANGE COLUMN ddd_telefone ddd_telefone VARCHAR(5), CHANGE COLUMN nr_telefone nr_telefone VARCHAR(20);
-- rollback ALTER TABLE fnrh.fnrh CHANGE COLUMN ddi_telefone ddi_telefone VARCHAR(3), CHANGE COLUMN ddd_telefone ddd_telefone VARCHAR(2), CHANGE COLUMN nr_telefone nr_telefone VARCHAR(10);

--changeset sabrina:1.1.0-alter-campos-celular
ALTER TABLE fnrh.fnrh CHANGE COLUMN ddi_celular ddi_celular VARCHAR(4), CHANGE COLUMN ddd_celular ddd_celular VARCHAR(5), CHANGE COLUMN nr_celular nr_celular VARCHAR(20);
-- rollback ALTER TABLE fnrh.fnrh CHANGE COLUMN ddi_celular ddi_celular VARCHAR(3), CHANGE COLUMN ddd_celular ddd_celular VARCHAR(2), CHANGE COLUMN nr_celular nr_celular VARCHAR(10);

--changeset sabrina:1.1.0-add-origem-cod-reserva
alter table fnrh.reserva add column origem_cod char(2) null comment 'Código da Origem da Reserva';
--rollback alter table fnrh.reserva drop column origem_cod; 




