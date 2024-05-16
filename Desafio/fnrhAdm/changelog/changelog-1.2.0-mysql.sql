--liquibase formatted sql

--changeset sabrina:1.2.0-create-table-fnrh_documento
CREATE TABLE fnrh.fnrh_documento(
  id INT NOT NULL AUTO_INCREMENT,
  tipodocumento_id INT(11) NOT NULL,
  numero_documento VARCHAR(30) NOT NULL,
  orgao_documento VARCHAR(20) NULL,
  fnrh_id INT(11) NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_doc_fnrh_idx (fnrh_id ASC),
  CONSTRAINT fk_doc_fnrh
    FOREIGN KEY (fnrh_id)
    REFERENCES fnrh.fnrh(id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
-- rollback drop table fnrh.fnrh_documento;

--changeset sabrina:1.2.0-insert-parametro-documento
INSERT INTO fnrhsr.parametrizacao (tipo, valor, descricao, inativo) VALUES ('OBRIGA_DOCUMENTO', '1', 'Indica se o documento é obrigatório ou não para consulta da reserva', '0');
-- rollback delete from fnrhsr.parametrizacao where tipo = 'OBRIGA_DOCUMENTO';


