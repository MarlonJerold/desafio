--liquibase formatted sql

/** Altera quantidade de dias para o envio do e-mail  **/

--changeset sabrina:1.0.0-update-quantidade-envio-email
update fnrhsr.parametrizacao set quantidade = 15 where tipo = 'ANTECEDENCIA_ENVEMAIL';
-- rollback update fnrhsr.parametrizacao set quantidade = 7 where tipo = 'ANTECEDENCIA_ENVEMAIL';

--changeset sabrina:1.0.0-add-origem-reserva
alter table fnrh.reserva add column origem varchar(200) null comment 'Origem da Reserva';
--rollback alter table fnrh.reserva drop column origem; 



