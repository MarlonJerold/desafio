package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.TransactionRequiredException;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.CidadeCM;
import br.com.beachpark.fnrhAdm.model.EstadoCM;
import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.beachpark.fnrhAdm.model.FnrhDocumento;
import br.com.beachpark.fnrhAdm.model.MeioTransporteEnum;
import br.com.beachpark.fnrhAdm.model.MotivoViagemEnum;
import br.com.beachpark.fnrhAdm.model.PaisCM;
import br.com.beachpark.fnrhAdm.model.Parametrizacao;
import br.com.beachpark.fnrhAdm.model.RelacionaReservaFNRHCM;
import br.com.beachpark.fnrhAdm.model.ReservaCMFNRH;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.beachpark.fnrhAdm.model.TipoTelefone;
import br.com.padrao.base.EM;
import br.com.padrao.repo.TimeService;
import br.com.padrao.util.DateUtil;
import br.com.padrao.util.LastException;
import br.com.padrao.util.StringUtil;

/**
 * Repositório de operações a serem realizadas no schema CM
 * @author LesteTI
 */
@Name("cmRepo")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class CMRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3805212002254405886L;

	@In
	private EM em;

	@In
	private TimeService timeService;
	
	@In
	private PaisCMRepo paisCMRepo;
	
	@In
	private EstadoCMRepo estadoCMRepo;
	
	@In
	private CidadeCMRepo cidadeCMRepo;
	
	@In
	private ParametrizacaoRepo parametrizacaoRepo;

	/**
	 * Método que busca as reservas confirmadas aptas a serem importadas do cm para
	 * preenchimento pelo hóspede
	 *
	 * @return List<ReservaCMFNRH>
	 */
	@SuppressWarnings("unchecked")
	public List<ReservaCMFNRH> getReservasConfirmadasParaImportacao()
			throws Exception {
		String dataInicial =null;
		String dataFinal =null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT IDRESERVASFRONT AS reservaId,	  	\n");
			sql.append("       STATUSRESERVA AS reservaStatusId,	\n");
			sql.append("       PRINCIPAL AS principal,  				\n");
			sql.append("       DESIGNACAO AS designacao,	  			\n");
			sql.append("       NOME_HOSPEDE	AS hospedeNome,  		\n");
			sql.append("       SOBRENOME_HOSPEDE AS hospedeSobrenome,  		\n");
			sql.append("       RAZAOSOCIAL_PESSOA AS pessoaRazaoSocial,	  	\n");
			sql.append("       NOME_PESSOA	AS pessoaNome,  					\n");
			sql.append("       DATANASCIMENTO AS dataNascimento,	  			\n");
			sql.append("       EMAIL AS email,	  							\n");
			sql.append("       PROFISSAO AS profissao,						\n");
			sql.append("       IDPAIS_NACIONALIDADE	AS paisNacionalidadeId,  \n");
			sql.append("       CODISO_NACIONALIDADE AS paisNacionalidadeCod, \n");
			sql.append("       GENERO AS genero,  							\n");
			sql.append("       IDENDERECO_RES AS enderecoResidenciaId, 		\n");
			sql.append("       BAIRRO_RES AS enderecoResidenciaBairro, 	\n");
			sql.append("       LOGRADOURO_RES AS enderecoResidenciaLogradouro, 	\n");
			sql.append("       NUMERO_RES AS enderecoResidenciaNumero, 			\n");
			sql.append("       cast(COMPLEMENTO_RES as varchar(200)) AS enderecoResidenciaComplemento, \n");
			sql.append("       IDPAIS_RES AS paisResidenciaId,  					\n");
			sql.append("       CODISO_RES AS paisResidenciaCod, 					\n");			
			sql.append("       UF_RES AS estadoResidenciaCod, 					\n");
			//sql.append("       CAST(UF_RES AS VARCHAR2(3)) AS estadoResidenciaCod, 	\n");			
			sql.append("       IDCIDADE_RES AS cidadeResidenciaId,  				\n");
			sql.append("       CODIBGE_RES AS cidadeResidenciaCodIGGE, 			\n");
			sql.append("       CEP_RES AS enderecoResidenciaCEP, 				\n");
			sql.append("       IDHISTORICOESTADA AS historicoEstadaId, 			\n");
			sql.append("       CODISO_ORI AS paisOrigemCod, 						\n");
			sql.append("       UF_ORI AS estadoOrigemCod, 						\n");
			sql.append("       CIDADE_ORI AS cidadeOrigemCodIGGE, 				\n");
			sql.append("       CODISO_DES AS paisDestinoCod, 					\n");
			sql.append("       UF_DES AS estadoDestinoCod,						\n");
			sql.append("       CIDADE_DES AS cidadeDestinoCodIGGE, 				\n");
			sql.append("       MOTIVOVIAGEM AS motivoViagem, 					\n");
			sql.append("       MEIOTRANSPORTE AS meioTransporte, 				\n");
			sql.append("       IDHOSPEDE AS hospedeId, 							\n");
			sql.append("       IDCONTA AS contaId, 								\n");
			sql.append("       HOTELID AS hotelId, 								\n");
			sql.append("       DATACHEGPREVISTA AS dataPrevistaCheckIn,			\n");
			sql.append("       DATAPARTPREVISTA AS dataPrevistaCheckOut,		\n");
			sql.append("       NUMRESERVAGDS AS localizador,					\n");
			sql.append("       NUMRESERVA AS reservaNum,						\n");
			sql.append("       ORIGEM_DES as origem, 			    			\n");			
			sql.append("       CEL_DDD, 			    						\n");
			sql.append("       CEL_DDI, 			    						\n");
			sql.append("       CEL_NUM, 			    						\n");
			sql.append("       TEL_DDD, 			    						\n");
			sql.append("       TEL_DDI, 			    						\n");
			sql.append("       TEL_NUM,  			    						\n"); 
			sql.append("       ORIGEM_COD as origemCod			    			\n");
			sql.append("  FROM BPRECEPTOR.RESERVA_FNRH 	    					\n");
			sql.append(" WHERE STATUSRESERVA=:statusReserva   					\n");	
			sql.append("   AND DATACHEGPREVISTA BETWEEN TO_DATE(:dataInicial,'dd/MM/yyyy') and TO_DATE(:dataFinal,'dd/MM/yyyy')	\n");			
			sql.append("   AND PRINCIPAL=:principal							\n");
			sql.append("   AND NOT EXISTS (SELECT * FROM BPRECEPTOR.RELRESERVAFNRH RF	\n");
			sql.append("   						   WHERE RF.ID_RESERVA=IDRESERVASFRONT	\n");
			sql.append("   				  		     AND RF.TIPO=:tipo)					\n");
			sql.append(" ORDER BY reservaId 									        \n");

			Query query = em.createNativeQuery(sql.toString());
			
			Parametrizacao parametrizacaoAntEnvioEmail = parametrizacaoRepo
					.getPorTipo(Parametrizacao.ANTECEDENCIA_ENVIO_EMAIL);
			
			dataInicial =DateUtil.formataData(DateUtil.getDataSomada(timeService.getDatabaseTimestamp(),0));
			dataFinal =DateUtil.formataData(DateUtil.getDataSomada(timeService.getDatabaseTimestamp(),parametrizacaoAntEnvioEmail.getQuantidade().intValue()+1));			
			
			query.setParameter("statusReserva", ReservaCMFNRH.STATUS_CONFIRMADA);		
			query.setParameter("principal", ReservaCMFNRH.HOSPEDE_PRINCIPAL);
			query.setParameter("tipo", RelacionaReservaFNRHCM.TIPO_IMPORTACAO);
			query.setParameter("dataInicial",dataInicial);
			query.setParameter("dataFinal",dataFinal);

			List<Object[]> dadosReservas = query.getResultList();

			List<ReservaCMFNRH> reservasConfirmadas = new ArrayList<ReservaCMFNRH>();

			if (dadosReservas != null) {
				for (Object[] reserva : dadosReservas) {
					ReservaCMFNRH reservaCMFNRH = setaDadosReserva(reserva);
					
					reservasConfirmadas.add(reservaCMFNRH);
				}
			}

			return reservasConfirmadas;

		} catch (Exception e) {
			System.out.print(e.getStackTrace());
			throw new SQLException(
					"Problema na consulta <getReservasConfirmadasParaImportacao>. Detalhes :"
							+ e.getMessage() + "- Parâmetros - dataInicial=" +dataInicial + ", dataFinal="+dataFinal+
							",statusReserva= "+ ReservaCMFNRH.STATUS_CONFIRMADA+ ",principal="
							+ReservaCMFNRH.HOSPEDE_PRINCIPAL+",tipo="+RelacionaReservaFNRHCM.TIPO_IMPORTACAO );		
		}
	}
	
	/**
	 * Método que busca as reservas com checkin para serem atualizadas de acordo com dados das fnrhs
	 * preenchidas pelos hóspedes
	 *
	 * @return List<Long> Lista dos ids das reservas
	 */
	
	@SuppressWarnings("unchecked")
	public List<Long> getReservasCheckinParaAtualizacao() throws Exception {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT IDRESERVASFRONT AS reservaId	  					\n");
			sql.append("  FROM BPRECEPTOR.RESERVA_FNRH 	    					\n");
			sql.append(" WHERE STATUSRESERVA=:statusReserva   					\n");
			//sql.append("   AND IDRESERVASFRONT=:reservaId						\n");
			sql.append("   AND PRINCIPAL=:principal								\n");
			sql.append("   AND NOT EXISTS (SELECT * FROM BPRECEPTOR.RELRESERVAFNRH RF	\n");
			sql.append("   						   WHERE RF.ID_RESERVA=IDRESERVASFRONT	\n");
			sql.append("   				  		     AND RF.TIPO=:tipo)					\n");
			sql.append(" ORDER BY reservaId 									        \n");
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("statusReserva", ReservaCMFNRH.STATUS_CHECKIN);
			query.setParameter("principal", ReservaCMFNRH.HOSPEDE_PRINCIPAL);
			query.setParameter("tipo", RelacionaReservaFNRHCM.TIPO_CHECKIN);

			List<Object> dadosReservas = query.getResultList();
			List<Long> reservasCheckin = new ArrayList<Long>();
			if(dadosReservas.size()>0){
				for(Object o : dadosReservas){
					reservasCheckin.add(((BigDecimal) o).longValue());	
				}			
			}
						
			return reservasCheckin;
			
		} catch (Exception e) {
			throw new SQLException(
					"Problema na consulta <getReservasCheckinParaAtualizacao>. Detalhes :"
							+ e.getMessage());
		}
	}	

	/**
	 * Inserção na tabela que relaciona reservas CM e reservas da base FNRH
	 * de acordo com o tipo de operação:
	 * <ul> Opções <ul>
	 * <li>TIPO_IMPORTACAO - 0 </li>
	 * <li>TIPO_EXPORTACAO - 1 </li>
	 * <li>TIPO_CHECKIN - 2 </li>	 
	 * Este método possui controle de transação
	 * 
	 * @param rel RelacionaReservaFNRHCM
	 * @throws IllegalStateException
	 * @throws PersistenceException
	 * @throws Exception
	 */
	public void insereRelacionamentoReservaCMEFNRH(RelacionaReservaFNRHCM rel) throws IllegalStateException,PersistenceException,Exception{
		try{
			em.getTransaction().begin();
			insereRelacionaReservaCMFNRH(rel);
			em.getTransaction().commit();
			
		} catch (IllegalStateException  i) {
			em.getTransaction().rollback();
			throw new IllegalStateException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	em.getTransaction().rollback();
	            	throw new SQLSyntaxErrorException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro:" + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	em.getTransaction().rollback();
	            	throw new TransactionRequiredException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro:" + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	em.getTransaction().rollback();
	            	throw new SQLException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	em.getTransaction().rollback();
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			em.getTransaction().rollback();
			throw new TransactionRequiredException("[CMREPO]- [insereRelacionamentoReservaCMEFNRH] - Detalhe do Erro: " + p.getMessage());
		}	
		
	}
	
	/**
	 * Inserção na tabela que relaciona reservas CM e reservas da base FNRH <b>BPRECEPTOR.RELRESERVAFNRH<b>
	 * de acordo com o tipo de operação:
	 * <ul> Opções <ul>
	 * <li>TIPO_IMPORTACAO - 0 </li>
	 * <li>TIPO_EXPORTACAO - 1 </li>
	 * <li>TIPO_CHECKIN - 2 </li>	 
	 * Este método não possui controle de transação
	 * 
	 * @param rel RelacionaReservaFNRHCM
	 * @throws IllegalStateException
	 * @throws PersistenceException
	 * @throws Exception
	 */
	private void insereRelacionaReservaCMFNRH(RelacionaReservaFNRHCM rel)
			throws IllegalStateException,PersistenceException,Exception {
		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO BPRECEPTOR.RELRESERVAFNRH (ID_RESERVAFNRH,ID_RESERVA,ID_HOTEL,TIPO,DATA)			\n");
			sql.append(" VALUES	(BPRECEPTOR.RELRESERVAFNRH_SEQ.NEXTVAL,:reservaCm,:hotelId,:tipo,:data) \n");
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("reservaCm", rel.getReservaCM());
			query.setParameter("hotelId", rel.getHotelId());
			query.setParameter("tipo", rel.getTipo());
			query.setParameter("data", timeService.getDatabaseTimestamp());
			query.executeUpdate();		
			
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro:" + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro:" + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- insereRelacionaReservaCMFNRH - Problema ao inserir na tabela de relacionamento reserva X fnrh. Detalhe do erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereRelacionaReservaCMFNRH] - Detalhe do Erro: " + p.getMessage());
		}			
	}

	
	/**
	 * Realiza as seguintes operações no schema CM de acordo com os dados preenchidos na FNRH: <br>
	 * *-Atualiza hóspede (CM.HOSPEDE) ; <br>
	 * *-Insere endereço (CM.ENDPESS) ;  <br>
	 * *-Insere pessoa física (CM.PESSOAFISICA) ;  <br>
	 * *-Insere telefones (Particular e Celular) (CM.TELENDPESS) ;  <br>
	 * *-Atualiza pessoa  (CM.PESSOA) ;  <br>  
	 * *-Insere os documentos  (CM.DOCPESSOA) ;  <br>  
	 * *-Atualiza o tipo de hóspede da HospedeHotel  (CM.HOSPEDEXHOTEL) de acordo com a <br>
	 *  Movimentos Hósspedes (CM.MOVIMENTOHOSPEDES) ;  <br> 
	 *  
	 * @param  fnrh
	 * @throws Exception,IllegalStateException,PersistenceException	 
	 */
	public void atualizaTabelasCM(Fnrh fnrh) throws Exception,IllegalStateException,PersistenceException {
		try {						
			    atualizaHospedeCM(fnrh);	
				//Long enderecoId = buscaSequenceEndereco();
				//insereEnderecoPessoa(fnrh,enderecoId);
			    BigDecimal enderecoId = null;
			    if(existeEnderecoPessoa(fnrh.getHospedeId())){
					enderecoId = buscaEnderecoPrincipal(fnrh.getHospedeId());
					if (enderecoId != null) {
						updateEnderecoPessoa(fnrh, enderecoId);
					}
				}else{
					enderecoId = buscaSequenceEndereco();
					if (enderecoId != null) {
						insereEnderecoPessoa(fnrh,enderecoId);
					}
				}
			    				
				inserePessoaFisica(fnrh);	
				insereTelefones(fnrh, enderecoId);
				if(fnrh.getTipoDocumento()!=null || (fnrh.getFnrhDocumentoList()!= null && fnrh.getFnrhDocumentoList().size()>0)){
					insereDocumentoPessoa(fnrh);
				}	
				
				atualizaPessoaHospede(fnrh);
			//	atualizaHospedeHotel(fnrh);
						
			
		} catch (IllegalStateException  i) {			
			throw new IllegalStateException(i.getMessage());			
		}  catch (PersistenceException p) {			
			throw new PersistenceException(p.getMessage());	
		}catch (Exception e) {			
			throw new Exception(e.getMessage());	
		}		

	}

	private BigDecimal buscaEnderecoPrincipal(Long pessoaId) throws SQLException {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(" select end.idendereco  	  					\n");
			sql.append(" from CM.ENDPESS end inner join cm.pessoa  pess 	    					\n");
			sql.append(" on pess.idpessoa = end.idpessoa   					\n");
			
			sql.append("   where pess.IDPESSOA = :pessoaId 							\n");
			sql.append("  and end.idendereco = pess.idendresidencial	\n");
			Query query = em.createNativeQuery(sql.toString());
		
			query.setParameter("pessoaId", pessoaId);
			try {
				BigDecimal idEndereco = (BigDecimal) query.getSingleResult();
				return idEndereco;
			} catch (NoResultException e){
				return null;
			}
			
			
		} catch (Exception e) {
			throw new SQLException(
					"Problema na consulta <buscaEnderecoPrincipal>. Detalhes :"
							+ e.getMessage());
		}
	}

	/**
	 * Percorre a lista de fnrhs de uma determinada reserva e exporta os dados para a base CM. @see atualizaTabelasCM
	 * @param fnrhs
	 * @param relacionamentoReservaFNRH
	 * @param statusIntegracaoFNRH 
	 * @throws Exception,IllegalStateException,PersistenceException
	 * 
	 */
	public void exportaFnrhsPorReservaParaCM(List<Fnrh> fnrhs,RelacionaReservaFNRHCM relacionamentoReservaFNRH, StatusIntegracaoFNRH statusIntegracaoFNRH) throws Exception,IllegalStateException,PersistenceException {
		try {
			em.getTransaction().begin();	
			for(Fnrh fnrh : fnrhs){
				atualizaTabelasCM(fnrh);
			}
			if (statusIntegracaoFNRH.equals(StatusIntegracaoFNRH.DIG)){
				insereRelacionaReservaCMFNRH(relacionamentoReservaFNRH);	
			}
			em.getTransaction().commit();
			
		} catch (IllegalStateException  i) {
			em.getTransaction().rollback();
			throw new IllegalStateException(i.getMessage());			
		}  catch (PersistenceException p) {
			em.getTransaction().rollback();
			throw new PersistenceException(p.getMessage());	
		}catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e.getMessage());	
		}		

	}
	
	/**
	 * Atualiza as reservas cm com checkin realizado de acordo com os dados da fnrh.
	 * Atualiza a tabela CM.HISTORICOESTADA e atualiza a tabela CM.HOSPEDEXHOTEL <BR>
	 * além disso insere registro na tabela <b>BPRECEPTOR.RELRESERVAFNRH<b> do tipo checkin
	 * 
	 * @param fnrhs
	 * @param relacionamentoReservaFNRH
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws PersistenceException
	 */
	
	public void atualizaFnrhsCheckinCM(List<Fnrh> fnrhs,RelacionaReservaFNRHCM relacionamentoReservaFNRH) throws Exception,IllegalStateException,PersistenceException {
		try {
			em.getTransaction().begin();	
			for(Fnrh fnrh : fnrhs){
				/*if(DateUtil.calculaIdade(fnrh.getDataNascimento())>Fnrh.IDADE_OBRIGATORIEDADE_FNRH){
					atualizaHistorioEstada(fnrh);	
				}*/
				
				if(fnrh.getMeioTransporte()!=null || fnrh.getMotivoViagem()!=null || fnrh.getCidadeCodDestino() != null || fnrh.getCidadeCodOrigem() != null){
					atualizaHistorioEstada(fnrh);	
				}
				
				atualizaHospedeHotel(fnrh);
				
			}			
			insereRelacionaReservaCMFNRH(relacionamentoReservaFNRH);				
			em.getTransaction().commit();
			
		} catch (IllegalStateException  i) {
			em.getTransaction().rollback();
			throw new IllegalStateException(i.getMessage());			
		}  catch (PersistenceException p) {
			em.getTransaction().rollback();
			throw new PersistenceException(p.getMessage());	
		}catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception(e.getMessage());	
		}		

	}


	/**
	 * Atualiza a tabela <b>CM.HOSPEDE<b> com dos dados de nome, sobreome e data de nascimento da FNRH
	 * @param FNRH fnrh
	 * @throws Exception,IllegalStateException,PersistenceException
	 * 
	 */
	private void atualizaHospedeCM(Fnrh fnrh) throws IllegalStateException,PersistenceException,Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE CM.HOSPEDE H SET  				\n");
			sql.append(" H.NOME=:nome, 							\n");
			sql.append(" H.SOBRENOME=:sobreNome 				\n");
			if (fnrh.getDataNascimento() != null) {
				sql.append(" ,H.DATANASCIMENTO=:dataNascimento 		\n");
			}
			sql.append("WHERE H.IDHOSPEDE=:hospedeId 	\n");
			//sql.append("  AND H.IDHOTEL=:hotelId 	\n");
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("nome", StringUtil.obtemString(fnrh.getNome().toUpperCase(),null,60));
			query.setParameter("sobreNome",  StringUtil.obtemString(fnrh.getSobrenome().toUpperCase(),null,60));
			if (fnrh.getDataNascimento() != null) {
				query.setParameter("dataNascimento", fnrh.getDataNascimento());
			}
			query.setParameter("hospedeId", fnrh.getHospedeId());
			
		//	query.setParameter("hotelId", fnrh.getReserva().getHotel().getCodigoErp());

			query.executeUpdate();		
		
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + p.getMessage());
		}	
	}
	
	/**
	 * Atualiza a tabela <b>CM.HISTORICOESTADA<b> com dados de motivo de viagem, meio de transporte, cidade de origem e destino
	 * informadas na fnrh
	 * @param FNRH fnrh
	 * @throws Exception,IllegalStateException,PersistenceException
	 * 
	 */
	private void atualizaHistorioEstada(Fnrh fnrh) throws IllegalStateException,PersistenceException,Exception {
		try {
			StringBuffer sql = new StringBuffer();
			
			BigDecimal idHistoricoEstada = getHistoricoEstada(fnrh.getHospedeId(), Long.parseLong(fnrh.getReserva().getHotel().getCodigoErp()));
			if (idHistoricoEstada != null){
				sql.append("UPDATE CM.HISTORICOESTADA HE SET  		\n");
				sql.append("HE.MOTIVOVIAGEM=:motivoViagem,			\n");
				sql.append("HE.TRANSPORTE=:transporte				\n");
				if(fnrh.getCidadeCodOrigem()!=null){
					sql.append(",HE.IDCIDADEORIGEM=:cidadeOrigem	\n");
				}
				
				if(fnrh.getCidadeCodDestino()!=null){
					sql.append(",HE.IDCIDADEDESTINO=:cidadeDestino	\n");
				}
				sql.append("WHERE HE.IDHISTORICOESTADA=:idHistoricoEstada 	\n");
				//sql.append("WHERE HE.IDHOSPEDE=:hospedeId 	\n");
				//sql.append("  AND HE.IDHOTEL=:hotelId 	\n");
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("motivoViagem", fnrh.getMotivoViagem().getCodigoErp());
				query.setParameter("transporte", fnrh.getMeioTransporte().getCodigoErp());
				if(fnrh.getCidadeCodOrigem()!=null){
					query.setParameter("cidadeOrigem", cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodOrigem().toString()).getId());
				}
				if(fnrh.getCidadeCodDestino()!=null){
					query.setParameter("cidadeDestino", cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodDestino().toString()).getId());
				}
				query.setParameter("idHistoricoEstada",idHistoricoEstada);
				
				//query.setParameter("hospedeId", fnrh.getHospedeId());
				//query.setParameter("hotelId", Long.parseLong(fnrh.getReserva().getHotel().getCodigoErp()));
				query.executeUpdate();	
			} else {
				sql.append(" insert into CM.HISTORICOESTADA ");
				sql.append(" (IDHISTORICOESTADA, ");
				sql.append(" IDHOSPEDE, ");
				sql.append(" IDHOTEL, ");
				//sql.append(" IDCLIENTE, ");
				if(fnrh.getCidadeCodDestino()!=null){
					sql.append(" IDCIDADEDESTINO, ");
				}
				if(fnrh.getCidadeCodOrigem()!=null){
					sql.append(" IDCIDADEORIGEM, ");
				}
				sql.append(" DATACHEGPREVISTA, ");
				sql.append(" DATAPARTPREVISTA, ");
				if ( fnrh.getMotivoViagem() != null) {
					sql.append(" MOTIVOVIAGEM, ");
				}
				if (fnrh.getMeioTransporte() !=  null) {
				sql.append(" TRANSPORTE, ");
				}
				sql.append(" TRGDTINCLUSAO, ");
				sql.append(" TRGUSERINCLUSAO) ");
				sql.append("  VALUES ");
				sql.append("  (CM.SEQHISTORICOESTADA.NEXTVAL, ");
				sql.append("  :idHospede, ");
				sql.append("  :idHotel, ");
				//sql.append("  :idCliente, ");
				if(fnrh.getCidadeCodDestino()!=null){
					sql.append("  :idCidadeDestino, ");
				}
				if(fnrh.getCidadeCodOrigem()!=null){
					sql.append("  :idCidadeOrigem, ");
				}
				sql.append("  to_date('"+fnrh.getReserva().getDataCheckinPrevisto()+"','yyyy-MM-dd'), ");
				sql.append("  to_date('"+fnrh.getReserva().getDataCheckoutPrevisto()+"','yyyy-MM-dd'), ");
				if ( fnrh.getMotivoViagem() != null) {
					sql.append("'"+ fnrh.getMotivoViagem().getCodigoErp()+"', ");
				}
				if (fnrh.getMeioTransporte() !=  null) {
					sql.append("'"+fnrh.getMeioTransporte().getCodigoErp()+"', ");
				}
				sql.append("  SYSDATE, ");
				sql.append("  'FNRH') ");
				
				Query query = em.createNativeQuery(sql.toString());
				query.setParameter("idHospede", fnrh.getHospedeId());
				query.setParameter("idHotel", Long.parseLong(fnrh.getReserva().getHotel().getCodigoErp()));
				//query.setParameter("idCliente", fnrh.getReserva().get);
				if(fnrh.getCidadeCodDestino()!=null){
					query.setParameter("idCidadeDestino", cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodDestino().toString()).getId());
				}
				if(fnrh.getCidadeCodOrigem()!=null){
					query.setParameter("idCidadeOrigem", cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodOrigem().toString()).getId());
				}
				//query.setParameter("dataChegadaPrev", fnrh.getReserva().getDataCheckinPrevisto());
				//query.setParameter("dataPartPrev", fnrh.getReserva().getDataCheckoutPrevisto());
				//query.setParameter("motivo", fnrh.getMotivoViagem().getCodigoErp());
				//query.setParameter("transporte", fnrh.getMeioTransporte().getCodigoErp());
				query.executeUpdate();
			}
				
		
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [atualizaHistorioEstada] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [atualizaHistorioEstada] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [atualizaHistorioEstada] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [atualizaHistorioEstada] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [atualizaHospedeCM] - Detalhe do Erro: " + p.getMessage());
		}	
	}

	
	
	@SuppressWarnings("unchecked")
	private BigDecimal getHistoricoEstada(Long hospedeId, long hotelId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT H.Idhistoricoestada ");
		sql.append(" FROM PESSOA          PHOT, ");
		sql.append(" PESSOA          P, ");
		sql.append(" STATUSRESERVA   S, ");
		sql.append(" CIDADES         CIDORG, ");
		sql.append(" CIDADES         CIDDEST, ");
		sql.append(" HISTORICOESTADA H ");
		sql.append(" WHERE H.IDHOSPEDE = :hospedeId ");
		sql.append(" AND PHOT.IDPESSOA = H.IDHOTEL ");
		sql.append(" AND H.IDCIDADEORIGEM = CIDORG.IDCIDADES(+) ");
		sql.append(" AND H.IDCIDADEDESTINO = CIDDEST.IDCIDADES(+) ");
		sql.append(" AND H.IDCLIENTE = P.IDPESSOA(+) ");
		sql.append(" AND H.STATUSESTADA = S.STATUSRESERVA(+) ");
		sql.append(" ORDER BY H.DATACHEGADA DESC ");
       
        Query query = em.createNativeQuery(sql.toString());
		query.setParameter("hospedeId", hospedeId);
		//Fnrh não é por hotel
		//query.setParameter("hotelId", hotelId);
		
		List<BigDecimal> heList = (List<BigDecimal>) query.getResultList();
		if (heList != null && heList.size()>0) {
			return heList.get(0);
		}
		return null;
	}

	/**
	 * Atualiza a tabela <b>CM.PESSOA</b> com dos dados de nome,email e endereço da FNRH
	 * @param FNRH fnrh
	 * @throws Exception,IllegalStateException,PersistenceException
	 * 
	 */
	private void atualizaPessoaHospede(Fnrh fnrh) throws IllegalStateException,PersistenceException,Exception{
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE CM.PESSOA P SET  				\n");
			sql.append(" P.RAZAOSOCIAL=:razaoSocial, 							\n");
			sql.append(" P.NOME=:nome, 				\n");
			sql.append(" P.EMAIL=:email, 		\n");
			sql.append(" P.IDENDRESIDENCIAL=:enderecoId 		\n");
			//Não altera documento da pessoa, ele é usado para validar se existe a reserva
			/*if(fnrh.getTipoDocumento()!=null){
				sql.append(", P.IDDOCUMENTO=:tipoDocumentoId 		\n");
			}
			if(StringHelper.isNotEmpty(fnrh.getDocumentoNumero())){
				sql.append(", P.NUMDOCUMENTO=:numeroDocumentoId 		\n");
			}*/
			sql.append("WHERE P.IDPESSOA=:pessoaId	\n");
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("razaoSocial",StringUtil.obtemString(fnrh.getNome().toUpperCase(),null,70));
			query.setParameter("nome", StringUtil.obtemString(fnrh.getNome().toUpperCase(),null,70));
			query.setParameter("email",StringUtil.obtemString(fnrh.getEmail(),null,200));
			query.setParameter("enderecoId", buscaUltimoEnderecoPorPessoa(fnrh.getHospedeId()));
			query.setParameter("pessoaId", fnrh.getHospedeId());
			/*if(fnrh.getTipoDocumento()!=null){
				query.setParameter("tipoDocumentoId", fnrh.getTipoDocumento().getTipoDocumentoErp());
			}
			if(StringHelper.isNotEmpty(fnrh.getDocumentoNumero())){
				query.setParameter("numeroDocumentoId", fnrh.getDocumentoNumero());				
			}*/
			

			query.executeUpdate();
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [atualizaPessoaHospede] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [atualizaPessoaHospede] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [atualizaPessoaHospede] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [atualizaPessoaHospede] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [atualizaPessoaHospede] - Detalhe do Erro: " + p.getMessage());
		}		
		
	}
	
	/**
	 * Atualiza a tabela <b>CM.HOSPEDEXHOTEL<b> com dos dados de tipo de hóspede da tabela <b>CM.MOVIMENTOHOSPEDES<b>
	 * @param FNRH fnrh
	 * @throws Exception,IllegalStateException,PersistenceException
	 * 
	 */
	private void atualizaHospedeHotel(Fnrh fnrh) throws IllegalStateException,PersistenceException,Exception{
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE HOSPEDEXHOTEL HH 			\n");
			sql.append("SET IDTIPOHOSPEDE=					\n");
			sql.append("(SELECT M.IDTIPOHOSPEDE  			\n");
			sql.append("   FROM CM.MOVIMENTOHOSPEDES M  	\n");
			sql.append("  WHERE M.IDRESERVASFRONT=:reservaId \n");
			sql.append("    AND M.IDHOTEL=:hotelId 			\n");
			sql.append("    AND M.IDHOSPEDE=:hospedeId) 	\n");
			sql.append("  WHERE HH.IDHOSPEDE=:hospedeId 	\n");
			sql.append("    AND HH.IDHOTEL=:hotelId 		\n");			     
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("reservaId",fnrh.getReserva().getIdReservaErp());
			query.setParameter("hotelId", Long.parseLong(fnrh.getReserva().getHotel().getCodigoErp()));
			query.setParameter("hospedeId",fnrh.getHospedeId());					

			query.executeUpdate();
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [atualizaHospedeHotel] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [atualizaHospedeHotel] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [atualizaHospedeHotel] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [atualizaHospedeHotel] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [atualizaHospedeHotel] - Detalhe do Erro: " + p.getMessage());
		}		
		
	}
	
	
	/**
	 * Verifica a existência de registro de pessoa física pelo id pessoa
	 * @param pessoaId
	 * @return boolean
	 */
	
	@SuppressWarnings("unchecked")
	public boolean existePessoaFisica(Long pessoaId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IDPESSOA					\n");
		sql.append("   FROM CM.PESSOAFISICA PF 			\n");
		sql.append("  WHERE PF.IDPESSOA=:pessoaId	\n");


		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);

		List<Object> pessoaList = (List<Object>) query.getResultList();
		
		return pessoaList.size()>0;

	}
	
	
	/**
	 * Verifica a existência de registro de documento pelo id pessoa e id documento
	 * @param pessoaId
	 * @param tipoDocumento
	 * @return boolean
	 */	
	@SuppressWarnings("unchecked")
	public boolean existeDocumentoPessoa(Long pessoaId,Long tipoDocumento){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IDPESSOA					\n");
		sql.append("   FROM CM.DOCPESSOA DP 			\n");
		sql.append("  WHERE DP.IDPESSOA=:pessoaId	\n");
		sql.append("    AND DP.IDDOCUMENTO=:tipoDocumentoId	\n");


		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);
		query.setParameter("tipoDocumentoId", tipoDocumento);

		List<Object> documentoList = (List<Object>) query.getResultList();
		
		return documentoList.size()>0;

	}
	
	/**
	 * Verifica a existência de registro de documento pelo id pessoa e id documento na tabela pessoa
	 * @param pessoaId
	 * @param tipoDocumento
	 * @return boolean
	 */	
	@SuppressWarnings("unchecked")
	public boolean existeNumDocPessoa(Long pessoaId,Long tipoDocumento){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IDDOCUMENTO, NUMDOCUMENTO					\n");
		sql.append("   FROM CM.PESSOA P 			\n");
		sql.append("  WHERE P.IDPESSOA=:pessoaId	\n");
		sql.append("    AND (P.IDDOCUMENTO=:tipoDocumentoId or P.IDDOCUMENTO is null)	\n");


		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);
		query.setParameter("tipoDocumentoId", tipoDocumento);

		List<Object> documentoList = (List<Object>) query.getResultList();
		
		return documentoList.size()>0;

	}
	
	/**
	 * Verifica a existência de registro de endereco pelo id pessoa (cm.endpess)
	 * @param pessoaId
	 * @return boolean
	 */	
	@SuppressWarnings("unchecked")
	public boolean existeEnderecoPessoa(Long pessoaId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IDENDERECO					\n");
		sql.append("   FROM CM.ENDPESS EP 			\n");
		sql.append("  WHERE EP.IDPESSOA=:pessoaId	\n");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);	

		List<Object> enderecoList = (List<Object>) query.getResultList();
		
		return enderecoList.size()>0;
	}
	
	
	
	/**
	 * Verifica a existência de registro de telefone pelo id pessoa (cm.telendpess)
	 * @param pessoaId
	 * @param tipoTelefone
	 * @return Long - id do endereço
	 */
	@SuppressWarnings("unchecked")
	public Long buscaEnderecoTelefonePessoa(Long pessoaId,String tipoTelefone){
		StringBuffer sql = new StringBuffer();		
		sql.append(" SELECT T.IDTELEFONE 				\n");  
		sql.append("   FROM CM.TELENDPESS T,				\n");
		sql.append("        CM.ENDPESS E					\n");
		sql.append("  WHERE T.IDENDERECO=E.IDENDERECO	\n");
		sql.append("    AND E.IDPESSOA=:pessoaId	\n");
		sql.append("    AND TRIM(T.TIPO)=:tipo			\n");		

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);	
		query.setParameter("tipo",tipoTelefone);		
		
		Long telefoneId = null;		

		List<Object> telefonesList = (List<Object>) query.getResultList();
		if(telefonesList.size()>0){
			telefoneId = ((BigDecimal) telefonesList.get(0)).longValue();
		}
		
		return telefoneId;
	}
	
	@SuppressWarnings("unchecked")
	public Long buscaEnderecoPessoa(Long pessoaId){
		StringBuffer sql = new StringBuffer();		
		sql.append(" SELECT E.IDENDERECO 			\n");
		sql.append("   FROM CM.ENDPESS E				\n");		
		sql.append("  WHERE E.IDPESSOA=:pessoaId	\n");	
		sql.append(" ORDER BY E.IDENDERECO DESC		\n");	

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);	
		
		
		Long enderecoId = null;		

		List<Object> enderecosList = (List<Object>) query.getResultList();
		if(enderecosList.size()>0){
			enderecoId = ((BigDecimal) enderecosList.get(0)).longValue();
		}
		
		return enderecoId;
	}

	
	/**
	 * Verifica a existência de registro de documento pelo id pessoa e id documento
	 * @param pessoaId	 
	 * @return boolean
	 */	
	@SuppressWarnings("unchecked")
	public boolean existeDocumentoPessoa(Long pessoaId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT IDPESSOA					\n");
		sql.append("   FROM CM.DOCPESSOA DP 			\n");
		sql.append("  WHERE DP.IDPESSOA=:pessoaId	\n");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);		

		List<Object> documentoList = (List<Object>) query.getResultList();
		
		return documentoList.size()>0;

	}
	
	/**
	 * Verifica a existência de registro de documento pelo id pessoa 
	 * @param pessoaId	 
	 * @return Object[] 
	 */	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDocumentoPessoa(Long pessoaId){
		StringBuffer sql = new StringBuffer();
	
		sql.append(" SELECT TPD.IDDOCUMENTO as \"id\",			\n");
		sql.append("        DOC.NUMDOCUMENTO as \"numero\",		\n");
		sql.append("        DOC.ORGAO as \"orgao\"				\n");
		sql.append("   FROM CM.DOCPESSOA DOC 					\n");
		sql.append("   JOIN CM.TIPODOCPESSOA TPD ON DOC.IDDOCUMENTO = TPD.IDDOCUMENTO			\n");
		sql.append("  WHERE DOC.IDPESSOA=:pessoaId				\n");
		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("pessoaId", pessoaId);		

		List<Object[]> documentoList = (List<Object[]>) query.getResultList();
		/*if(documentoList!=null && documentoList.size()>0){
			return (Object[]) documentoList.get(0);
		}*/
		return documentoList;
	}
	
	/**
	 * Insere registro na tabela pessoa física <b>CM.PESSOAFISICA<b> com campos pais,estado,cidade e sexo advindos da FNRH.
	 * @param Fnrh fnrh
	 * @throws IllegalStateException,PersistenceException,Exception
	 */
	private void inserePessoaFisica(Fnrh fnrh)  throws IllegalStateException,PersistenceException,Exception {
		try {
			PaisCM paisResidencia = null;
			PaisCM paisNacionalidade = null;
			EstadoCM estadoResidencia= null;
			CidadeCM cidadeResidencia= null;
			String condicaoInsert ="";
			String condicaoInsertValue ="";
			String condicaoUpdate ="";
			
			if(StringHelper.isNotEmpty(fnrh.getPaisResidencia())){
				paisResidencia = paisCMRepo.getPorCodigoISO(fnrh.getPaisResidencia());
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoUpdate+="IDPAIS=:paisId";
				condicaoInsert+="IDPAIS";
				condicaoInsertValue+=":paisId";
			}			
			
			if(StringHelper.isNotEmpty(fnrh.getPaisNacionalidade())){
				paisNacionalidade = paisCMRepo.getPorCodigoISO(fnrh.getPaisNacionalidade());
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoUpdate+="IDNACIONALIDADE=:paisNacionalidade";
				condicaoInsert+="IDNACIONALIDADE";
				condicaoInsertValue+=":paisNacionalidade";
			}	   
			
			if(StringHelper.isNotEmpty(fnrh.getPaisResidencia()) && StringHelper.isNotEmpty(fnrh.getEstadoResidencia())){
				estadoResidencia =estadoCMRepo.getPorPaisCodigo(paisResidencia, fnrh.getEstadoResidencia());		
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				//condicaoUpdate+="IDESTADO=:estadoId,CODESTADO=:estadoCod";
				//condicaoInsert+="IDESTADO,CODESTADO";
				condicaoUpdate+="CODESTADO=:estadoCod";
				condicaoInsert+="CODESTADO";
				//condicaoInsertValue+=":estadoId,:estadoCod";
				condicaoInsertValue+=":estadoCod";
			}	
			
			if(fnrh.getCidadeCodResidencia()!=null){
				cidadeResidencia = cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodResidencia().toString());
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoUpdate+="IDCIDADES=:cidadeId";
				condicaoInsert+="IDCIDADES";
				condicaoInsertValue+=":cidadeId";
			}
			
			if(fnrh.getGenero()!=null){
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoUpdate+="SEXO=:sexo";
				condicaoInsert+="SEXO";
				condicaoInsertValue+=":sexo";
			}
			
			if(fnrh.getDataNascimento()!=null){
				if(StringHelper.isNotEmpty(condicaoUpdate)){
					condicaoUpdate+=",";
				}
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoUpdate+="DATANASC=:dataNascimento";
				condicaoInsert+="DATANASC";
				condicaoInsertValue+=":dataNascimento";
			}	
			
			if (!condicaoInsert.equals("") || !condicaoUpdate.equals("")) {
			
						
				condicaoUpdate+=" WHERE IDPESSOA=:pessoaId";
				
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoInsert+="IDPESSOA";
				condicaoInsertValue+=":pessoaId";
				
				StringBuffer sql = new StringBuffer();
				if(existePessoaFisica(fnrh.getHospedeId())){
					sql.append("UPDATE CM.PESSOAFISICA PF SET   \n");
					sql.append(condicaoUpdate);
					
				}else{
					sql.append("INSERT INTO CM.PESSOAFISICA PF (  				\n");
					sql.append(condicaoInsert);
					sql.append(") VALUES (");
					sql.append(condicaoInsertValue);
					sql.append(")");							
				}			
			
				
				Query query = em.createNativeQuery(sql.toString());	
				
				if(paisResidencia!=null){
					query.setParameter("paisId", paisResidencia.getId());	
				}
				
				//if(estadoResidencia !=null){
				//	query.setParameter("estadoId", estadoResidencia.getId());
				//}
				
				if(estadoResidencia !=null){
					//query.setParameter("estadoId", estadoResidencia.getId());
					query.setParameter("estadoCod", estadoResidencia.getCodigo());
				}	
							
				if(cidadeResidencia !=null){
					query.setParameter("cidadeId", cidadeResidencia.getId());
				}
				
				if(fnrh.getGenero() !=null){
					query.setParameter("sexo", fnrh.getGenero().getCodigo());
				}
				
				if(paisNacionalidade !=null){
					query.setParameter("paisNacionalidade", paisNacionalidade.getId());
				}
				
				if(fnrh.getDataNascimento() !=null){
					query.setParameter("dataNascimento", fnrh.getDataNascimento());
				}
				
				query.setParameter("pessoaId",fnrh.getHospedeId());
					
				query.executeUpdate();
			}
			

	} catch (IllegalStateException  i) {
		throw new IllegalStateException("[CMREPO]- [atualizaPessoaFisica] - Detalhe do Erro: " + i.getMessage());		
	}  catch (PersistenceException p) {
		  LastException lastException = new LastException();
          Throwable th = lastException.findLastException(p);
            if (th instanceof SQLSyntaxErrorException) {
            	throw new SQLSyntaxErrorException("[CMREPO]- [atualizaPessoaFisica] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
            }else if (th instanceof TransactionRequiredException) {
            	throw new TransactionRequiredException("[CMREPO]- [atualizaPessoaFisica] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
            }else if (th instanceof SQLException) {
            	throw new SQLException("[CMREPO]- [atualizaPessoaFisica] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
            }else{
            	throw new PersistenceException(p.getMessage());
            }			 
		
	}catch (Exception p) {
		throw new TransactionRequiredException("[CMREPO]- [atualizaPessoaFisica] - Detalhe do Erro: " + p.getMessage());
	}	
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Busca próximo sequencial (CM.SEQENDPESS) da tabela <b>CM.ENDPESS<b>
	 * @throws IllegalStateException,PersistenceException,Exception
	 */
	private BigDecimal buscaSequenceEndereco() throws IllegalStateException,PersistenceException,Exception {
		try{
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT CM.SEQENDPESS.NEXTVAL AS ID FROM DUAL	\n");
			Query query = em.createNativeQuery(sql.toString());
			BigDecimal sequence = null;
			List<BigDecimal> sequenceList = (List<BigDecimal>) query.getResultList();
			
			sequence =(BigDecimal) sequenceList.iterator().next();

			return sequence;
		
	} catch (IllegalStateException  i) {
		throw new IllegalStateException("[CMREPO]- [buscaSequenceEndereco] - Detalhe do Erro: " + i.getMessage());		
	}  catch (PersistenceException p) {
		  LastException lastException = new LastException();
          Throwable th = lastException.findLastException(p);
            if (th instanceof SQLSyntaxErrorException) {
            	throw new SQLSyntaxErrorException("[CMREPO]- [buscaSequenceEndereco] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
            }else if (th instanceof TransactionRequiredException) {
            	throw new TransactionRequiredException("[CMREPO]- [buscaSequenceEndereco] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
            }else if (th instanceof SQLException) {
            	throw new SQLException("[CMREPO]- [buscaSequenceEndereco] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
            }else{
            	throw new PersistenceException(p.getMessage());
            }			 
		
	}catch (Exception p) {
		throw new TransactionRequiredException("[CMREPO]- [buscaSequenceEndereco] - Detalhe do Erro: " + p.getMessage());
	}
		
	}
	

	/**
	 * Insere registro na tabela <b> CM.ENDPESS<b> com informações de pais, estado, cidade, logradour, numero, complemento e cep preenchidos na FNRH
	 * @param  Fnrh fnrh,Long enderecoId - Identificador do endereço (CM.ENDPESS)
	 * @throws IllegalStateException,PersistenceException,Exception
	 */
	
	private void insereEnderecoPessoa(Fnrh fnrh,BigDecimal enderecoId) throws IllegalStateException,PersistenceException,Exception {
		try {
			/*	StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO CM.ENDPESS   																		\n");
			sql.append("(IDENDERECO,IDPESSOA,IDPAIS,CODESTADO,IDCIDADES,CIDADE,LOGRADOURO,NUMERO,COMPLEMENTO,CEP,TIPOENDERECO)		\n");
			sql.append("VALUES (:enderecoId,:pessoaId,:paisId,:estadoCod,:cidadeId,:cidadeNome,:logradouro,:numero,:complemento,:cep,'R') 	\n");
			*/	
			PaisCM pais = null;
			EstadoCM estado= null;
			CidadeCM cidade = null;
			
			if(StringHelper.isNotEmpty(fnrh.getPaisResidencia())){
				pais = paisCMRepo.getPorCodigoISO(fnrh.getPaisResidencia());
				
				if(StringHelper.isNotEmpty(fnrh.getEstadoResidencia())){
					estado= estadoCMRepo.getPorPaisCodigo(pais, fnrh.getEstadoResidencia());					
				}
			}
			
			if(fnrh.getCidadeCodResidencia()!=null){
				cidade = cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodResidencia().toString());
			}
			
			String condicaoInsert ="";
			String condicaoInsertValue ="";
			
			condicaoInsert+="IDENDERECO,IDPESSOA";
			condicaoInsertValue+=":enderecoId,:pessoaId";
			
			if(pais!=null){				
				
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="IDPAIS";
				condicaoInsertValue+=":paisId";
			}
				
			
			if(estado!=null){	
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoInsert+="CODESTADO";
				condicaoInsertValue+=":estadoCod";
								
			}
			
			if(cidade!=null){		
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				condicaoInsert+="IDCIDADES";
				condicaoInsertValue+=":cidadeId";
				
				condicaoInsert+=",CIDADE";
				condicaoInsertValue+=",:cidadeNome";				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getBairroResidencia())){
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="BAIRRO";
				condicaoInsertValue+=":bairro";				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getLogradouroResidencia())){
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="LOGRADOURO";
				condicaoInsertValue+=":logradouro";				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getNumeroResidencia())){
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="NUMERO";
				condicaoInsertValue+=":numero";						
			}
			
			if(StringHelper.isNotEmpty(fnrh.getComplementoResidencia())){
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="COMPLEMENTO";
				condicaoInsertValue+=":complemento";							
			}
			
			if(StringHelper.isNotEmpty(fnrh.getCepResidencia())){
				if(StringHelper.isNotEmpty(condicaoInsert)){
					condicaoInsert+=",";
					condicaoInsertValue+=",";
				}
				
				condicaoInsert+="CEP";
				condicaoInsertValue+=":cep";				
			}
			
			if(StringHelper.isNotEmpty(condicaoInsert)){
				condicaoInsert+=",";
				condicaoInsertValue+=",";
			}
			
			condicaoInsert+="TIPOENDERECO";
			condicaoInsertValue+="'R'";	
			
			StringBuffer sql = new StringBuffer();			
			sql.append("INSERT INTO CM.ENDPESS   		\n");
			sql.append("(" +condicaoInsert+ ") 		\n");
			sql.append("VALUES (" +condicaoInsertValue+ ") 		\n");				
			
			
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("enderecoId",enderecoId);
			query.setParameter("pessoaId",fnrh.getHospedeId());
			if(pais!=null){
				query.setParameter("paisId",(Long) pais.getId());
			}
			
			if(estado!=null){
				query.setParameter("estadoCod",estado.getCodigo());
			}
			
			if(cidade!=null){
				query.setParameter("cidadeId", cidade.getId());
	            query.setParameter("cidadeNome",StringUtil.obtemString(cidade.getNome(), null,60));
			}
			
			if(StringHelper.isNotEmpty(fnrh.getBairroResidencia())){
				query.setParameter("bairro",StringUtil.obtemString(fnrh.getBairroResidencia(), null,20));				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getLogradouroResidencia())){
				query.setParameter("logradouro",StringUtil.obtemString(fnrh.getLogradouroResidencia(), null,60));				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getNumeroResidencia())){
				query.setParameter("numero", StringUtil.obtemString(fnrh.getNumeroResidencia(), null,8));					
			}
			
			if(StringHelper.isNotEmpty(fnrh.getComplementoResidencia())){
				query.setParameter("complemento",StringUtil.obtemString(fnrh.getComplementoResidencia(), null,100));			
			}
			
			if(StringHelper.isNotEmpty(fnrh.getCepResidencia())){
				query.setParameter("cep", StringUtil.obtemString(fnrh.getCepResidencia(), null,8));				
			}			

			query.executeUpdate();	
			
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + p.getMessage());
		}
	}
	
	
	/**
	 * Insere registro na tabela <b> CM.ENDPESS<b> com informações de pais, estado, cidade, logradour, numero, complemento e cep preenchidos na FNRH
	 * @param  Fnrh fnrh,Long enderecoId - Identificador do endereço (CM.ENDPESS)
	 * @throws IllegalStateException,PersistenceException,Exception
	 */	
	private void updateEnderecoPessoa(Fnrh fnrh, BigDecimal enderecoId) throws IllegalStateException,PersistenceException,Exception {
		try {
			
			PaisCM pais = null;
			EstadoCM estado= null;
			CidadeCM cidade = null;
			
			if(StringHelper.isNotEmpty(fnrh.getPaisResidencia())){
				pais = paisCMRepo.getPorCodigoISO(fnrh.getPaisResidencia());
				
				if(StringHelper.isNotEmpty(fnrh.getEstadoResidencia())){
					estado= estadoCMRepo.getPorPaisCodigo(pais, fnrh.getEstadoResidencia());					
				}
			}
			
			if(fnrh.getCidadeCodResidencia()!=null){
				cidade = cidadeCMRepo.getPorCodigoIBGE(fnrh.getCidadeCodResidencia().toString());
			}
			
			String condicaoUpdate ="";
			
			if(pais!=null){										
				condicaoUpdate+="IDPAIS=:paisId,";				
			}else{
				condicaoUpdate+="IDPAIS=null,";
			}
			
			if(estado!=null){	
				condicaoUpdate+="CODESTADO=:estadoCod,	";				
			}else{
				condicaoUpdate+="CODESTADO=null,	";		
			}
			
			if(cidade!=null){							
				condicaoUpdate+="IDCIDADES=:cidadeId,	";	
				condicaoUpdate+="CIDADE=:cidadeNome,	";	
			}else{
				condicaoUpdate+="IDCIDADES=null,	";	
				condicaoUpdate+="CIDADE=null,	";	
			}
			
			if(StringHelper.isNotEmpty(fnrh.getBairroResidencia())){
				condicaoUpdate+="BAIRRO=:bairro,	";					
			}else{
				condicaoUpdate+="BAIRRO=null,	";
			}
			
			if(StringHelper.isNotEmpty(fnrh.getLogradouroResidencia())){
				condicaoUpdate+="LOGRADOURO=:logradouro,	";					
			}else{
				condicaoUpdate+="LOGRADOURO=null,	";
			}
			
			if(StringHelper.isNotEmpty(fnrh.getNumeroResidencia())){
				condicaoUpdate+="NUMERO=:numero,		";					
			}else{
				condicaoUpdate+="NUMERO=null,	";
			}
			
			if(StringHelper.isNotEmpty(fnrh.getComplementoResidencia())){
				condicaoUpdate+="COMPLEMENTO=:complemento,			";					
			}else{
				condicaoUpdate+="COMPLEMENTO=null,	";
			}
			
			if(StringHelper.isNotEmpty(fnrh.getCepResidencia())){
				condicaoUpdate+="CEP=:cep,			";					
			}else{
				condicaoUpdate+="CEP=null,	";
			}
			
			condicaoUpdate+="TIPOENDERECO='R' 			";		
			
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE CM.ENDPESS SET 									\n");
			sql.append(condicaoUpdate);						
			sql.append("WHERE IDENDERECO=:enderecoId 							\n");			
						
			
			Query query = em.createNativeQuery(sql.toString());	
			query.setParameter("enderecoId", enderecoId);
			//query.setParameter("pessoaId",fnrh.getHospedeId());
			if(pais!=null){
				query.setParameter("paisId",(Long) pais.getId());
			}
			
			if(estado!=null){
				query.setParameter("estadoCod",estado.getCodigo());
			}
			
			if(cidade!=null){
				query.setParameter("cidadeId", cidade.getId());
	            query.setParameter("cidadeNome",StringUtil.obtemString(cidade.getNome(), null,60));
			}
			
			if(StringHelper.isNotEmpty(fnrh.getBairroResidencia())){
				query.setParameter("bairro",StringUtil.obtemString(fnrh.getBairroResidencia(), null,20));				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getLogradouroResidencia())){
				query.setParameter("logradouro",StringUtil.obtemString(fnrh.getLogradouroResidencia(), null,60));				
			}
			
			if(StringHelper.isNotEmpty(fnrh.getNumeroResidencia())){
				query.setParameter("numero", StringUtil.obtemString(fnrh.getNumeroResidencia(), null,8));					
			}
			
			if(StringHelper.isNotEmpty(fnrh.getComplementoResidencia())){
				query.setParameter("complemento",StringUtil.obtemString(fnrh.getComplementoResidencia(), null,100));			
			}
			
			if(StringHelper.isNotEmpty(fnrh.getCepResidencia())){
				query.setParameter("cep", StringUtil.obtemString(fnrh.getCepResidencia(), null,8));				
			}			

			query.executeUpdate();	
			
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + p.getMessage());
		}
	}
	
	/**
	 * Insere registro na tabela <b> CM.DOCPESSOA<b> com informações de tipo de documento, número e órgão fornecidos na  FNRH
	 * 
	 * @param  fnrh
	 * @throws IllegalStateException,PersistenceException,Exception
	 */	
	private void insereDocumentoPessoa(Fnrh fnrh) throws IllegalStateException,PersistenceException,Exception {
		try {
			StringBuffer sql = new StringBuffer();
			if (fnrh.getTipoDocumento() != null) {	
				//if (existeNumDocPessoa(fnrh.getHospedeId(),fnrh.getTipoDocumento().getTipoDocumentoErp())) {
					//atualiza documento na tabela pessoa
					sql.append("UPDATE CM.PESSOA SET   \n");
					sql.append("NUMDOCUMENTO=:documentoNumero,   \n");
					sql.append("IDDOCUMENTO=:documentoId  \n");
					sql.append("WHERE IDPESSOA=:pessoaId  \n");
					
					Query query = em.createNativeQuery(sql.toString());			
					query.setParameter("pessoaId",fnrh.getHospedeId());
					query.setParameter("documentoId",fnrh.getTipoDocumento().getTipoDocumentoErp());
					query.setParameter("documentoNumero",StringUtil.obtemString(fnrh.getDocumentoNumero(), null,200));
					
					query.executeUpdate();	
					
				//} 
				//atualiza na docpessoa
				
				sql = new StringBuffer();
				if(existeDocumentoPessoa(fnrh.getHospedeId(),fnrh.getTipoDocumento().getTipoDocumentoErp())){
					sql.append("UPDATE CM.DOCPESSOA SET   \n");
					sql.append("NUMDOCUMENTO=:documentoNumero   \n");
					if(StringHelper.isNotEmpty(fnrh.getDocumentoOrgao())){
						sql.append(",ORGAO=:documentoOrgao \n");
					}
					sql.append("WHERE IDDOCUMENTO=:documentoId AND IDPESSOA=:pessoaId  \n");
					
				}else{
					sql.append("INSERT INTO CM.DOCPESSOA (IDDOCUMENTO,IDPESSOA,NUMDOCUMENTO 	\n");				
					if(StringHelper.isNotEmpty(fnrh.getDocumentoOrgao())){
						sql.append(",ORGAO \n");
					}
					sql.append(") VALUES (:documentoId,:pessoaId,:documentoNumero  \n");
					if(StringHelper.isNotEmpty(fnrh.getDocumentoOrgao())){
						sql.append(",:documentoOrgao  \n");
					}
					sql.append(")  \n");									
				}		
					
					query = em.createNativeQuery(sql.toString());			
					query.setParameter("pessoaId",fnrh.getHospedeId());
					query.setParameter("documentoId",fnrh.getTipoDocumento().getTipoDocumentoErp());
					query.setParameter("documentoNumero",StringUtil.obtemString(fnrh.getDocumentoNumero(), null,200));
					if(StringHelper.isNotEmpty(fnrh.getDocumentoOrgao())){
						query.setParameter("documentoOrgao",StringUtil.obtemString(fnrh.getDocumentoOrgao(), null,30));
					}
				
					query.executeUpdate();	
				/*
				
				if (fnrh.getFnrhDocumentoList() != null && fnrh.getFnrhDocumentoList().size()>0) {
					for (FnrhDocumento fnrhDocumento : fnrh.getFnrhDocumentoList()) {
						if(existeDocumentoPessoa(fnrh.getHospedeId(),fnrhDocumento.getTipoDocumento().getTipoDocumentoErp())){
							sql.append("UPDATE CM.DOCPESSOA SET   \n");
							sql.append("NUMDOCUMENTO=:documentoNumero   \n");
							if(StringHelper.isNotEmpty(fnrhDocumento.getOrgao())){
								sql.append(",ORGAO=:documentoOrgao \n");
							}
							sql.append("WHERE IDDOCUMENTO=:documentoId AND IDPESSOA=:pessoaId  \n");
							
						}else{
							sql.append("INSERT INTO CM.DOCPESSOA (IDDOCUMENTO,IDPESSOA,NUMDOCUMENTO 	\n");				
							if(StringHelper.isNotEmpty(fnrhDocumento.getOrgao())){
								sql.append(",ORGAO \n");
							}
							sql.append(") VALUES (:documentoId,:pessoaId,:documentoNumero  \n");
							if(StringHelper.isNotEmpty(fnrhDocumento.getOrgao())){
								sql.append(",:documentoOrgao  \n");
							}
							sql.append(")  \n");									
						}		
						
						Query query = em.createNativeQuery(sql.toString());			
						query.setParameter("pessoaId",fnrh.getHospedeId());
						query.setParameter("documentoId",fnrhDocumento.getTipoDocumento().getTipoDocumentoErp());
						query.setParameter("documentoNumero",StringUtil.obtemString(fnrhDocumento.getNumero(), null,200));
						if(StringHelper.isNotEmpty(fnrhDocumento.getOrgao())){
							query.setParameter("documentoOrgao",StringUtil.obtemString(fnrhDocumento.getOrgao(), null,30));
						}
						
						query.executeUpdate();	
					
					}
				}
			*/
				
			
			
			}
			
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [insereDocumentoPessoa] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [insereDocumentoPessoa] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [insereDocumentoPessoa] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [insereDocumentoPessoa] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereEnderecoPessoa] - Detalhe do Erro: " + p.getMessage());
		}
	}

	
	
	/**
	 * Verifica se os dados informados são de Celular ou Particular e insere os dados de telefone referentes a cada tipo.
	 * @param  Fnrh fnrh,Long enderecoId - Identificador do endereço (CM.ENDPESS)
	 * 
	 */
	private void insereTelefones(Fnrh fnrh,BigDecimal enderecoId) throws Exception {	
	  try{
		if(StringHelper.isNotEmpty(fnrh.getCelularNumero())){
			insereTelefone(fnrh,TipoTelefone.CELULAR,enderecoId);
		}
		
		if(StringHelper.isNotEmpty(fnrh.getTelefoneNumero())){
			insereTelefone(fnrh,TipoTelefone.PARTICULAR,enderecoId);
		}		
		  
	  }catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereTelefones] - Detalhe do Erro: " + p.getMessage());
	  }	
		
	}
	
	
	/**
	 * Insere registron na tabela de telefone <b>CM.TELENDPESS<b>
	 * @param FNRH fnrh, TipoTelefone tipo @see TipoTelefone,Long enderecoId - Identificador do endereço (CM.ENDPESS)	 * 
	 * @throws Exception
	 */
	private void insereTelefone(Fnrh fnrh,TipoTelefone tipo,BigDecimal enderecoId) throws IllegalStateException,PersistenceException,Exception {
		try {
			Long idTelefone = buscaEnderecoTelefonePessoa(fnrh.getHospedeId(), tipo.getDescricao());
			Long enderecoExistente = buscaEnderecoPessoa(fnrh.getHospedeId());
			
			StringBuffer sql = new StringBuffer();
			if(idTelefone!=null){				
				sql.append("UPDATE CM.TELENDPESS 											  		\n");
				sql.append("   SET DDI=:ddi,DDD=:ddd,TIPO=:tipo,NUMERO=:numero 						\n");				
				sql.append(" WHERE IDTELEFONE=:telefoneId  											\n");
			}else{				
				sql.append("INSERT INTO CM.TELENDPESS (IDTELEFONE,IDENDERECO,DDI,DDD,TIPO,NUMERO)  						\n");
				sql.append("                   VALUES (CM.SEQTELENDPESS.NEXTVAL,:enderecoId,:ddi,:ddd,:tipo,:numero)  	\n");	
			}
				
				
			Query query = em.createNativeQuery(sql.toString());
			if(idTelefone!=null){
				query.setParameter("telefoneId",idTelefone);
			}else if(enderecoExistente!=null){
				query.setParameter("enderecoId",enderecoExistente);	
			}else{
				query.setParameter("enderecoId",enderecoId);	
			}
			
			
			if(tipo.equals(TipoTelefone.CELULAR)){
				query.setParameter("ddi", StringUtil.obtemString(fnrh.getCelularDDI(), "    ", 4));
				query.setParameter("ddd", StringUtil.obtemString(fnrh.getCelularDDD(), "   ", 3));
				query.setParameter("tipo",TipoTelefone.CELULAR.toString());
				query.setParameter("numero", StringUtil.obtemString(fnrh.getCelularNumero(), null, 20));
			}
			
			if(tipo.equals(TipoTelefone.PARTICULAR)){
				query.setParameter("ddi", StringUtil.obtemString(fnrh.getTelefoneDDI(), "    ", 4));
				query.setParameter("ddd", StringUtil.obtemString(fnrh.getTelefoneDDD(), "   ", 3));
				query.setParameter("tipo",TipoTelefone.PARTICULAR.toString());
				query.setParameter("numero", StringUtil.obtemString(fnrh.getTelefoneNumero(), null, 20));
			}			

			query.executeUpdate();	
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [insereTelefone] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [insereTelefone] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [insereTelefone] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [insereTelefone] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [insereTelefone] - Detalhe do Erro: " + p.getMessage());
		}	
		
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Busca o endereço na tabela  <b>CM.ENDPESS</b>
	 * @param Long pessoaId - Identificador da pessoa (CM.PESSOA)	 
	 * @throws IllegalStateException,PersistenceException,Exception
	 */
	private Long buscaUltimoEnderecoPorPessoa(Long pessoaId) throws IllegalStateException,PersistenceException,Exception {
		try {
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT MAX(IDENDERECO) AS ENDERECOID   	\n");
			sql.append(" FROM  CM.ENDPESS 							\n");
			sql.append("WHERE  IDPESSOA=:pessoaId 					\n");		
			Query query = em.createNativeQuery(sql.toString());
			query.setParameter("pessoaId",pessoaId);
			
			Long enderecoId=null;
			List<BigDecimal> endereco = query.getResultList();
			if(endereco!=null){
				BigDecimal dado =(BigDecimal) endereco.get(0);
				if(dado!=null){
					enderecoId = Long.parseLong(dado.toString());
				}
			}
			
			return enderecoId;					
		} catch (IllegalStateException  i) {
			throw new IllegalStateException("[CMREPO]- [buscaUltimoEnderecoPorPessoa] - Detalhe do Erro: " + i.getMessage());		
		}  catch (PersistenceException p) {
			  LastException lastException = new LastException();
	          Throwable th = lastException.findLastException(p);
	            if (th instanceof SQLSyntaxErrorException) {
	            	throw new SQLSyntaxErrorException("[CMREPO]- [buscaUltimoEnderecoPorPessoa] - Detalhe do Erro: " + ((SQLSyntaxErrorException) th).getMessage());	               
	            }else if (th instanceof TransactionRequiredException) {
	            	throw new TransactionRequiredException("[CMREPO]- [buscaUltimoEnderecoPorPessoa] - Detalhe do Erro: " + ((TransactionRequiredException) th).getMessage());	               
	            }else if (th instanceof SQLException) {
	            	throw new SQLException("[CMREPO]- [buscaUltimoEnderecoPorPessoa] - Detalhe do Erro: " + ((SQLException) th).getMessage());	               	
	            }else{
	            	throw new PersistenceException(p.getMessage());
	            }			 
			
		}catch (Exception p) {
			throw new TransactionRequiredException("[CMREPO]- [buscaUltimoEnderecoPorPessoa] - Detalhe do Erro: " + p.getMessage());
		}	
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * Método que busca os hóspedes por reserva  <b>BPRECEPTOR.RESERVA_FNRH </b>
	 * @param Long reservaId, Long hotelId	
	 * @return  List<ReservaCMFNRH>
	 * @throws IllegalStateException,PersistenceException,Exception
	 */
	public List<ReservaCMFNRH> getHospedesPorReserva(Long reservaId,
			Long hotelId) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IDRESERVASFRONT AS reservaId,	  	\n");
		sql.append("       STATUSRESERVA AS reservaStatusId,	\n");
		sql.append("       PRINCIPAL AS principal,  				\n");
		sql.append("       DESIGNACAO AS designacao,	  			\n");
		sql.append("       NOME_HOSPEDE	AS hospedeNome,  		\n");
		sql.append("       SOBRENOME_HOSPEDE AS hospedeSobrenome,  		\n");
		sql.append("       RAZAOSOCIAL_PESSOA AS pessoaRazaoSocial,	  	\n");
		sql.append("       NOME_PESSOA	AS pessoaNome,  					\n");
		sql.append("       DATANASCIMENTO AS dataNascimento,	  			\n");
		sql.append("       EMAIL AS email,	  							\n");
		sql.append("       PROFISSAO AS profissao,						\n");
		sql.append("       IDPAIS_NACIONALIDADE	AS paisNacionalidadeId,  \n");
		sql.append("       CODISO_NACIONALIDADE AS paisNacionalidadeCod, \n");
		sql.append("       GENERO AS genero,  							\n");
		sql.append("       IDENDERECO_RES AS enderecoResidenciaId, 		\n");
		sql.append("       BAIRRO_RES AS enderecoResidenciaBairro, 	\n");
		sql.append("       LOGRADOURO_RES AS enderecoResidenciaLogradouro, 	\n");
		sql.append("       NUMERO_RES AS enderecoResidenciaNumero, 			\n");
		sql.append("       COMPLEMENTO_RES AS enderecoResidenciaComplemento, \n");
		sql.append("       IDPAIS_RES AS paisResidenciaId,  					\n");
		sql.append("       CODISO_RES AS paisResidenciaCod, 					\n");
		sql.append("       UF_RES AS estadoResidenciaCod, 					\n");
		sql.append("       IDCIDADE_RES AS cidadeResidenciaId,  				\n");
		sql.append("       CODIBGE_RES AS cidadeResidenciaCodIGGE, 			\n");
		sql.append("       CEP_RES AS enderecoResidenciaCEP, 				\n");
		sql.append("       IDHISTORICOESTADA AS historicoEstadaId, 			\n");
		sql.append("       CODISO_ORI AS paisOrigemCod, 						\n");
		sql.append("       UF_ORI AS estadoOrigemCod, 						\n");
		sql.append("       CIDADE_ORI AS cidadeOrigemCodIGGE, 				\n");
		sql.append("       CODISO_DES AS paisDestinoCod, 					\n");
		sql.append("       UF_DES AS estadoDestinoCod,						\n");
		sql.append("       CIDADE_DES AS cidadeDestinoCodIGGE, 				\n");
		sql.append("       MOTIVOVIAGEM AS motivoViagem, 					\n");
		sql.append("       MEIOTRANSPORTE AS meioTransporte, 				\n");
		sql.append("       IDHOSPEDE AS hospedeId, 							\n");
		sql.append("       IDCONTA AS contaId, 								\n");
		sql.append("       HOTELID AS hotelId, 								\n");
		sql.append("       DATACHEGPREVISTA AS dataPrevistaCheckIn,			\n");
		sql.append("       DATAPARTPREVISTA AS dataPrevistaCheckOut,		\n");
		sql.append("       NUMRESERVAGDS AS localizador,					\n");
		sql.append("       NUMRESERVA AS reservaNum,						\n");
		sql.append("       ORIGEM_DES AS origem, 			    			\n");
		sql.append("       CEL_DDD, 			    						\n");
		sql.append("       CEL_DDI, 			    						\n");
		sql.append("       CEL_NUM, 			    						\n");
		sql.append("       TEL_DDD, 			    						\n");
		sql.append("       TEL_DDI, 			    						\n");
		sql.append("       TEL_NUM,  			    						\n"); 
		sql.append("       ORIGEM_COD AS origemCod 			    			\n");
		sql.append("  FROM BPRECEPTOR.RESERVA_FNRH 	    					\n");
		sql.append(" WHERE STATUSRESERVA=:statusReserva   					\n");
		sql.append("   AND IDRESERVASFRONT=:reserva   						\n");
		sql.append("   AND HOTELID=:hotel   								\n");
		sql.append(" ORDER BY hospedeNome									\n");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("statusReserva", ReservaCMFNRH.STATUS_CONFIRMADA);
		query.setParameter("reserva", reservaId);
		query.setParameter("hotel", hotelId);

		List<Object[]> dadosReservas = query.getResultList();

		List<ReservaCMFNRH> reservasConfirmadas = new ArrayList<ReservaCMFNRH>();

		if (dadosReservas != null) {
			for (Object[] reserva : dadosReservas) {
				ReservaCMFNRH reservaCMFNRH = setaDadosReserva(reserva);
				reservasConfirmadas.add(reservaCMFNRH);
			}
		}

		return reservasConfirmadas;
		
	}

	/**
	 * Método que extrai dados do resultset e converte em ReservaCMFNRH
	 * @param  dadosReserva Array de objetos com dados da view 
	 * @return ReservaCMFNRH
	 */
	public ReservaCMFNRH setaDadosReserva(Object[] dadosReserva) {
		ReservaCMFNRH reserva = new ReservaCMFNRH();
		reserva.setReservaId(Long.valueOf(dadosReserva[0].toString()));
		reserva.setReservaStatusId(Long.valueOf(dadosReserva[1].toString()));
		reserva.setPrincipal(dadosReserva[2].toString());
		reserva.setDesignacao(dadosReserva[3].toString());
		reserva.setHospedeNome(dadosReserva[4].toString());
		reserva.setHospedeSobrenome(dadosReserva[5].toString());
		reserva.setPessoaRazaoSocial(dadosReserva[6].toString());
		reserva.setPessoaNome(dadosReserva[7].toString());

		if (dadosReserva[8] != null) {
			reserva.setDataNascimento(DateUtil
					.converteTimestampToDate((Timestamp) dadosReserva[8]));
		} else {
			reserva.setDataNascimento(null);
		}

		if (dadosReserva[9] != null) {
			reserva.setEmail(dadosReserva[9].toString().trim());
		} else {
			reserva.setEmail(null);
		}

		if (dadosReserva[10] != null) {
			reserva.setProfissao(dadosReserva[10].toString());
		} else {
			reserva.setProfissao(null);
		}

		if (dadosReserva[11] != null) {
			reserva.setPaisNacionalidadeId(((BigDecimal) dadosReserva[11]).longValue());
		} else {
			reserva.setPaisNacionalidadeId(null);
		}
		
		if (dadosReserva[12] != null) {
			reserva.setPaisNacionalidadeCod(dadosReserva[12].toString());
		} else {
			reserva.setPaisNacionalidadeCod(null);
		}

		/*if (dadosReserva[12] != null) {
			reserva.setPaisResidenciaCod(dadosReserva[12].toString());
		} else {
			reserva.setPaisResidenciaCod(null);
		}*/

		if (dadosReserva[13] != null) {
			reserva.setGenero(dadosReserva[13].toString());
		} else {
			reserva.setGenero(null);
		}

		if (dadosReserva[14] != null) {
			reserva.setEnderecoResidenciaId(((BigDecimal) dadosReserva[14]).longValue());
		} else {
			reserva.setEnderecoResidenciaId(null);
		}
		
		if (dadosReserva[15] != null) {
			reserva.setEnderecoResidenciaBairro(dadosReserva[15].toString());
		} else {
			reserva.setEnderecoResidenciaBairro(null);
		}

		if (dadosReserva[16] != null) {
			reserva.setEnderecoResidenciaLogradouro(dadosReserva[16].toString());
		} else {
			reserva.setEnderecoResidenciaLogradouro(null);
		}

		if (dadosReserva[17] != null) {
			reserva.setEnderecoResidenciaNumero(dadosReserva[17].toString());
		} else {
			reserva.setEnderecoResidenciaNumero(null);
		}

		if (dadosReserva[18] != null) {
			reserva.setEnderecoResidenciaComplemento(dadosReserva[18]
					.toString());
		} else {
			reserva.setEnderecoResidenciaComplemento(null);
		}

		if (dadosReserva[19] != null) {
			reserva.setPaisResidenciaId(((BigDecimal) dadosReserva[19]).longValue());
		} else {
			reserva.setPaisResidenciaId(null);
		}

		if (dadosReserva[20] != null) {
			reserva.setPaisResidenciaCod(dadosReserva[20].toString());
		} else {
			reserva.setPaisResidenciaCod(null);
		}

		if (dadosReserva[21] != null) {
			reserva.setEstadoResidenciaCod(dadosReserva[21].toString().trim());
		} else {
			reserva.setEstadoResidenciaCod(null);
		}

		if (dadosReserva[22] != null) {
			reserva.setCidadeResidenciaId((((BigDecimal) dadosReserva[22]).longValue()));
			CidadeCM cidadeRes = cidadeCMRepo.getPorId((((BigDecimal) dadosReserva[22]).longValue())) ;
			if(reserva.getEstadoResidenciaCod() == null) {
				reserva.setEstadoResidenciaCod(cidadeRes.getEstadoCM().getCodigo());
				EstadoCM estadoRes = estadoCMRepo.getPorId(cidadeRes.getEstadoCM().getId());				
				if (reserva.getPaisResidenciaId() == null) {
					reserva.setPaisResidenciaId(estadoRes.getPaisCM().getId());					
				}
				if(reserva.getPaisResidenciaCod() == null) {
					reserva.setPaisResidenciaCod(estadoRes.getPaisCM().getCodigoISO());
				}
			}
		} else {
			reserva.setCidadeResidenciaId(null);
		}

		if (dadosReserva[23] != null) {
			reserva.setCidadeResidenciaCodIGGE(dadosReserva[23].toString());
		} else {
			reserva.setCidadeResidenciaCodIGGE(null);
		}

		if (dadosReserva[24] != null) {
			reserva.setEnderecoResidenciaCEP(dadosReserva[24].toString());
		} else {
			reserva.setEnderecoResidenciaCEP(null);
		}

		if (dadosReserva[25] != null) {
			reserva.setHistoricoEstadaId(((BigDecimal) dadosReserva[25]).longValue());
		} else {
			reserva.setHistoricoEstadaId(null);
		}

		if (dadosReserva[34] != null) {
			reserva.setHospedeId(((BigDecimal) dadosReserva[34]).longValue());
		} else {
			reserva.setHospedeId(null);
		}

		if (dadosReserva[35] != null) {
			reserva.setContaId(((BigDecimal) dadosReserva[35]).longValue());
		} else {
			reserva.setContaId(null);
		}

		if (dadosReserva[36] != null) {
			reserva.setHotelId(((BigDecimal) dadosReserva[36]).longValue());
		} else {
			reserva.setHotelId(null);
		}

		if (dadosReserva[37] != null) {
			reserva.setDataPrevistaCheckIn(DateUtil
					.converteTimestampToDate((Timestamp) dadosReserva[37]));
		} else {
			reserva.setHotelId(null);
		}

		if (dadosReserva[38] != null) {
			reserva.setDataPrevistaCheckOut(DateUtil
					.converteTimestampToDate((Timestamp) dadosReserva[38]));
		} else {
			reserva.setDataPrevistaCheckOut(null);
		}
		
		if (dadosReserva[39] != null) {
			reserva.setLocalizador(((BigDecimal) dadosReserva[39]).longValue());
		} else {
			reserva.setLocalizador(null);
		}
		
		if(dadosReserva[40]!=null){
			reserva.setReservaNum(((BigDecimal) dadosReserva[40]).longValue());
		}else{
			reserva.setReservaNum(null);
		}		
		
		if(dadosReserva[41]!=null){
			reserva.setOrigem(((String) dadosReserva[41]));
		}else{
			reserva.setOrigem(null);
		}
		
		if(dadosReserva[42]!=null){
			reserva.setCelularDDD(((String) dadosReserva[42]));
		}else{
			reserva.setCelularDDD(null);
		}
		
		if(dadosReserva[43]!=null){
			reserva.setCelularDDI(((String) dadosReserva[43]).trim());
		}else{
			reserva.setCelularDDI(null);
		}
		
		if(dadosReserva[44]!=null){
			reserva.setCelularNum(((String) dadosReserva[44]));
		}else{
			reserva.setCelularNum(null);
		}
		
		if(dadosReserva[45]!=null){
			reserva.setTelefoneDDD(((String) dadosReserva[45]));
		}else{
			reserva.setTelefoneDDD(null);
		}
		if(dadosReserva[46]!=null){
			reserva.setTelefoneDDI(((String) dadosReserva[46]).trim());
		}else{
			reserva.setTelefoneDDI(null);
		}
		if(dadosReserva[47]!=null){
			reserva.setTelefoneNum(((String) dadosReserva[47]));
		}else{
			reserva.setTelefoneNum(null);
		}
		
		if(dadosReserva[48]!=null){
			reserva.setOrigemCod((String) dadosReserva[48]);
		}else{
			reserva.setOrigemCod(null);
		}
		
		//Default origem(residencia do cliente), destino (aquiraz), motivo lazer, meio avião
		reserva.setCidadeOrigemCodIGGE(reserva.getCidadeResidenciaCodIGGE());
		reserva.setEstadoOrigemCod(reserva.getEstadoResidenciaCod());
		reserva.setPaisOrigemCod(reserva.getPaisResidenciaCod());
		
		reserva.setCidadeDestinoCodIGGE("2301000");
		reserva.setEstadoDestinoCod("CE");
		reserva.setPaisDestinoCod("BR");
		
		reserva.setMotivoViagem(MotivoViagemEnum.get("L"));
		reserva.setMeioTransporte(MeioTransporteEnum.get("A"));
		
		return reserva;
	}
	
	public Long getStatusReserva(Long numReservaErp) {
		StringBuffer sql = new StringBuffer();
		sql.append("select statusreserva from cm.reservasfront where numreserva = :numreserva \n");

		Query query = em.createNativeQuery(sql.toString());
		query.setParameter("numreserva", numReservaErp);

		List<BigDecimal> dadosReservas = query.getResultList();
		
		if (dadosReservas != null && dadosReservas.size()>0) {
			return dadosReservas.get(0).longValue();
		}
		return null;
	}

}
