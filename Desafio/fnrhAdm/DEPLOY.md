# Deploy

- Disponiblizar war na porta 8080 do tomcat


## [1.6.2] 2023-08-10

Aplicação .war;	

## [1.6.1] 2023-08-08

Aplicação .war;	

## [1.6.0] 2023-07-03

Aplicação .war;	

## [1.5.2] 2023-03-20

Aplicação .war;	

## [1.5.1] 2023-03-15

Aplicação .war;	

## [1.5.0] 2023-03-09

Aplicação .war;	

## [1.4.9] 2020-10-07

Aplicação .war;	

## [1.4.8] 2020-08-14

Aplicação .war;	

## [1.4.7] 2020-07-31

Aplicação .war;	
Executar os scripts via liquibase(oracle);

## [1.4.6] 2020-07-28

Aplicação .war;	

## [1.4.5] 2020-01-10

Aplicação .war;	

## [1.4.4] 2019-01-10

Aplicação .war;	

## [1.4.3] 2018-12-27

Aplicação .war;	

## [1.4.2] 2018-10-01

Aplicação .war;	

## [1.4.1] 2018-03-13

Executar os scripts via liquibase(SRVMYSQL e mysqlweb2);Aplicação .war;	

## [1.4.0]

Executar os scripts via liquibase(somente no SRVMYSQL); Aplicação .war;	

## [1.3.2] 2018-03-02

Executar os scripts via liquibase(somente no SRVMYSQL); Aplicação .war;	

## [1.3.1] 2017-12-04

Executar os scripts via liquibase(Oracle e MySql); Aplicação .war;	

## [1.3.0] 2017-11-20

Executar os scripts via liquibase(Oracle);

## [1.2.0] 2017-11-01

Executar os scripts via liquibase(Mysql);
Verificar permissão do username fnrh, deve conseguir alterar o schema fnrh e fnrhsr;

## [1.1.0] 2017-10-26

Executar os scripts via liquibase(Oracle e Mysql);


## [1.0.0] 2017-10-16

Executar os scripts via liquibase(Oracle e Mysql);

No ambiente de homologação deve ser incluído na tabela de parametrização(mysql) o parâmetro "EMAIL_TESTES" para que os envios não sejam feitos para os clientes BP.

Ex.:
insert into fnrhsr.parametrizacao(tipo,descricao,inativo) 
values ('EMAIL_TESTES', 'sabrinalanny@gmail.com',0);

Alterar para o e-mail do responsável pelo teste.
 
Início do controle por changelog.

