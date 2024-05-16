=======================DESAFIO FNRH=========================================================
Temos um sistema hoje rodando chamado FNRHAdm, esse sistema consiste 
em pegar as reservas de nossos hoteis em um banco de dados e enviar 
um email para os clientes 15 dias antes de sua hospedagem. O cliente 
recebe um link para preencher sua ficha de FNRH(Ficha Nacional de Registro de Hospede).

Esse sistema possui as seguintes tecnologias:

- Java 7
- Tomcat 6
- Struts
- Conexão com Banco Oracle e Mysql
- JSP


O desafio consiste em duas etapas:

1) A aplicação atualmente esta apresentando um bug, onde com um tempo a aplicação para 
de se comunicar com o banco de dados, estou enviando os logs da aplicação para analise 
(Log1.txt e Log2.txt). O desafio é corrgir esse bug

2) Como a aplicação envia os emails para os hospedes, necessitamos de uma melhoria, onde 
será necessário adicionar no arquivo de logs(catalina.out) as reserva que foram disparadas 
os emails:

Ex: Email enviado: Reserva 1234

No final do desafio será necessario gerar o arquivo de deploy(.war)

Adicionei os arquivos context.xnl e server.xml para consulta

=========================DESAFIO TERÁ UMA DURAÇÃO DE 3 DIAS===============================