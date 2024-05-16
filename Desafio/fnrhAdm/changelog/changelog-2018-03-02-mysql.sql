--liquibase formatted sql

--changeset sabrina:2018-03-02-insert-parametrizacao-cco
insert into fnrhsr.parametrizacao(tipo,descricao,inativo) 
values ('EMAIL_CCO', 'fnrhadm@beachpark.com.br',0);
-- rollback delete from fnrhsr.parametrizacao where tipo='EMAIL_CCO'


