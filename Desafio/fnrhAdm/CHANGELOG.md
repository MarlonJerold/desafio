
# Changelog

## [1.6.2] 2023-08-10

Alterando regra de integração de fnrh, quando estiver com status em preenchimento na base interna, só será feita integração se no vfh não estiver com status check-in e nem check-out

## [1.6.1] 2023-08-08

Merge

## [1.6.0] 2023-07-03

Alterando regra de integração de fnrh, quando estiver com status em preenchimento na base externa, só será feita integração se no vfh não estiver com status check-in e nem check-out

## [1.5.2] 2023-03-20

Criação de painel contendo resumos dos status na pesquisa

## [1.5.1] 2023-03-15

Correção do pacote

## [1.5.0] 2023-03-09

Alteração do servidor de envio de e-mail para usar o mailer

## [1.4.9] 2020-10-07

Corrigir erro na importação da FNRH quando o cliente possui CNPJ;
Considerar preenchimento parcial da FNRH;
Copiar automaticamente e-mail do titular para os dependentes no preenchimento da FNRH;
Tornar obrigatório somente os dados abaixo:
 Se adulto: Primeiro nome, último nome, data de nascimento, CPF e telefone.
 Se criança: Primeiro nome, último nome e data de nascimento.
Corrigir exportação de documento, tipo de documento, última procedência e próximo destino.

## [1.4.8] 2020-08-14

- Correção exportação FNRH p/ o CM.

## [1.4.7] 2020-07-31

- Alteração da regra de importação para importar da data atual até o parametro. Correção da view para evitar duplicação.

## [1.4.6] 2020-07-28

- Correção envio e-mail; Inclusão do link na listagem da consulta de reserva.

## [1.4.5] 2020-01-10

- Troca de e-mail do marketing pelo e-mail "naoresponda";

## [1.4.4] 2019-01-10

- Customização do e-mail de pre-checkin; Inclusão de regras e download da ficha;

## [1.4.3] 2018-12-27

- Customização do e-mail de pre-checkin;

## [1.4.2] 2018-10-01

- Customização do e-mail de pre-checkin;
- Troca de e-mail "naoresponda"  pelo e-mail do marketing;
- Inclusão do google analytics

## [1.4.1] 2018-03-13

- Aumento do campo UF e País(cod), no oracle são 3 caracteres e no mysql estava 2.
- Inclusão da parametrização de cópia oculta no e-mail.

## [1.4.0] 2018-03-07

- Correção: Imagens e-mail


## [1.3.2] 2018-02-02

- Verificação de erro: Não está exportando quando a origem não é VC;
- Inclusão de CCO no envio do e-mail;

## [1.3.1] 2017-12-04

- Alteração de campo UF FNRH para tamanho 3;

## [1.3.0] 2017-11-20

-Retirada da conexão base externa;

## [1.2.0] 2017-11-01

Help-1678005
-Importar todos os documentos do hóspede; Parametrização da obrigatoriedade do documento na consulta da reserva;

## [1.1.0] 2017-10-26

Help-1629517
-Caso tenham dados da Reserva no VHF, como por exemplo Endereço, Telefone, etc deverão já vir preenchidos na ficha;
-Caso a Reserva seja do Vacation, proceder com o preenchimento automático com os dados do TS;

## [1.0.0] 2017-10-16

Help-1629517
Alteração do envio de e-mail de 7 para 15 dias. Alteração da parametrização no banco e da importação de acordo com a parametrização do envio;
Inclusão do campo origem da reserva na importação e na consulta;

Início do controle por changelog e [versionamento semântico](http://semver.org/).

Todas as mudanças realizadas no projeto devem ser documentadas aqui. 
Este formato baseado em [Keep a Changelog](http://keepachangelog.com).
