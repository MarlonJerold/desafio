package br.com.beachpark.fnrhAdm.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.mail.AuthenticationFailedException;
import javax.persistence.PersistenceException;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;

import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.beachpark.fnrhAdm.model.FnrhDocumento;
import br.com.beachpark.fnrhAdm.model.Hotel;
import br.com.beachpark.fnrhAdm.model.PaisCM;
import br.com.beachpark.fnrhAdm.model.Parametrizacao;
import br.com.beachpark.fnrhAdm.model.RelacionaReservaFNRHCM;
import br.com.beachpark.fnrhAdm.model.Reserva;
import br.com.beachpark.fnrhAdm.model.ReservaCMFNRH;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.beachpark.fnrhAdm.model.TipoDocumento;
import br.com.beachpark.fnrhAdm.model.TipoLog;
import br.com.beachpark.fnrhAdm.repo.BaseFnrhExternaRepo;
import br.com.beachpark.fnrhAdm.repo.CMRepo;
import br.com.beachpark.fnrhAdm.repo.FnrhRepo;
import br.com.beachpark.fnrhAdm.repo.GeneroRepo;
import br.com.beachpark.fnrhAdm.repo.HotelRepo;
import br.com.beachpark.fnrhAdm.repo.MeioTransporteRepo;
import br.com.beachpark.fnrhAdm.repo.MotivoViagemRepo;
import br.com.beachpark.fnrhAdm.repo.PaisCMRepo;
import br.com.beachpark.fnrhAdm.repo.ParametrizacaoRepo;
import br.com.beachpark.fnrhAdm.repo.ReservaRepo;
import br.com.beachpark.fnrhAdm.repo.TipoDocumentoRepo;
import br.com.padrao.exception.BusinessRuleException;
import br.com.padrao.repo.TimeService;
import br.com.padrao.util.EmailUtil;
import br.com.padrao.util.GeradorUtil;
import br.com.padrao.util.LastException;
import br.com.padrao.util.TomcatUtil;

@Name("reservaService")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class ReservaService implements Serializable {

	private static final long serialVersionUID = -3989126794560493783L;

	@In
	private TimeService timeService;

	@In
	private CMRepo cmRepo;
	
	@In
	private BaseFnrhExternaRepo baseFnrhExternaRepo;

	@In
	private ReservaRepo reservaRepo;

	@In
	private HotelRepo hotelRepo;

	@In
	private MotivoViagemRepo motivoViagemRepo;

	@In
	private MeioTransporteRepo meioTransporteRepo;

	@In
	private GeneroRepo generoRepo;

	@In
	private FnrhRepo fnrhRepo;

	@In
	private FacesMessages facesMessages;

	@In
	private ParametrizacaoRepo parametrizacaoRepo;

	@In
	private PaisCMRepo paisCMRepo;
	
	
	@In
	private TipoDocumentoRepo tipoDocumentoRepo;

	/*
	 * @In EmailUtil emailUtil;
	 */

	@In(create = true) 	
	private Renderer renderer;
	
/*	@In(create = true, required = true)
	private FacesContext facesContext;*/

	@In
	LogService logService;

	public String emailTo;
	public String emailFrom;
	public String emailCco;
	public String emailAssunto;
	public String emailCorpo;
	public String emailLink;
	public String emailCaminhoImg;
	public List<String> emailDestinatarios;
	public String content;

	public String getContent() {
		return content;
	}

	public List<String> getEmailDestinatarios() {
		return emailDestinatarios;
	}

	public void setEmailDestinatarios(List<String> emailDestinatarios) {
		this.emailDestinatarios = emailDestinatarios;
	}
	
	public String getEmailCco() {
		return emailCco;
	}

	public void setEmailCco(String emailCco) {
		this.emailCco = emailCco;
	}

	public String getEmailCaminhoImg() {
		return emailCaminhoImg;
	}

	public void setEmailCaminhoImg(String emailCaminhoImg) {
		this.emailCaminhoImg = emailCaminhoImg;
	}

	public String getEmailLink() {
		return emailLink;
	}

	public void setEmailLink(String emailLink) {
		this.emailLink = emailLink;
	}

	/**
	 * Método que localiza as reservas confirmadas no CM e insere as Reservas e
	 * RNRHs na base FNRH. *
	 * 
	 * @throws BusinessRuleException
	 */
	public void importarReservasViaAgendadorTarefa()
			throws BusinessRuleException {
		String keyLog = GeradorUtil.geraCodidoUUID();
		try {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Inicio da importação de reservas do cm", null, null);
			List<ReservaCMFNRH> reservasCM = getReservasConfirmadasParaImportacao();
			if (reservasCM.size() > 0) {
				insereReservas(reservasCM);	
			} else {
				logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
						"Sem reservas a serem importadas do o CM", null, null);
			}

			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Término da importação de reservas do cm", null, null);
		} catch (Exception e) {
			logService.adicionaLog(TipoLog.FALHA, keyLog,
					"Falha na importação das reservas do CM  ", e.getMessage(),
					null);
			//throw new BusinessRuleException(e.getMessage());
		}
	}
	
	
	public void importarReservasBaseExternaViaAgendadorTarefa()
			throws BusinessRuleException {
		String keyLog = GeradorUtil.geraCodidoUUID();
		try {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Inicio da importação de reservas da base externa", null, null);
			List<Reserva> reservas = getReservasExternasParaImportacao();
			if (reservas.size() > 0) {
				atualizaReservasImportadasBaseExterna(reservas);
			} else {
				logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
						"Sem reservas a serem importadas da base externa", null, null);
			}

			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Término da importação de reservas  da base externa", null, null);
		} catch (Exception e) {
			logService.adicionaLog(TipoLog.FALHA, keyLog,
					"Falha na importação das reservas da base externa", e.getMessage(),
					null);
			//throw new BusinessRuleException(e.getMessage());
		}

	}


	/**
	 * Método que localiza as reservas que realizaram checkin no CM, captura os
	 * dados preenchidos nas fnhrs, atualiza os dados no CM e marca a reserva
	 * (FNRH) com status CAT(Checkin atualizado) @see StatusIntegracaoFNRH
	 * 
	 * 
	 * throws IllegalStateException, PersistenceException, Exception
	 */
	public void atualizarReservasCheckinViaAgendadorTarefa()
			throws IllegalStateException, PersistenceException, Exception {

		String keyLog = GeradorUtil.geraCodidoUUID();

		logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
				"Inicio da atualização de reservas checkin do cm", null, null);
		List<Long> reservasCM = getReservasCheckinParaAtualizacao();
		if (reservasCM.size() > 0) {

			for (Long id : reservasCM) {
				Reserva reserva = reservaRepo.getPorIdErp(id);
				try {
					if (reserva != null
							&& reserva.getStatusIntegracao().equals(
									StatusIntegracaoFNRH.EXP)) {
						logService
								.adicionaLog(
										TipoLog.INFORMACAO,
										keyLog,
										"Reserva : "
												+ reserva.getId()
												+ " Inicio da atualização das tabelas CM",
										"", reserva.getId().toString());
						List<Fnrh> fnrhs = fnrhRepo.getFnrhsPorReserva(reserva
								.getId());
						if (fnrhs.size() > 0) {

							RelacionaReservaFNRHCM relacionamentoReservaFNRH = setDadosRelacionaReservaFNRHCM(reserva);
							relacionamentoReservaFNRH
									.setTipo(RelacionaReservaFNRHCM.TIPO_CHECKIN);

							cmRepo.atualizaFnrhsCheckinCM(fnrhs,
									relacionamentoReservaFNRH);

							reserva.setStatusIntegracao(StatusIntegracaoFNRH.CAT);
							reservaRepo.persist(reserva);

						}

					}

				} catch (IllegalStateException i) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.EAC);
					reservaRepo.persist(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na atualização da reserva",
							i.getMessage(), reserva.getId().toString());
					//throw new IllegalStateException(i.getMessage());
				} catch (PersistenceException p) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.EAC);
					reservaRepo.persist(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na atualização da reserva",
							p.getMessage(), reserva.getId().toString());
					//throw new PersistenceException(p.getMessage());
				} catch (Exception e) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.EAC);
					reservaRepo.persist(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na atualização da reserva",
							e.getMessage(), reserva.getId().toString());
					//throw new Exception(e.getMessage());
				}

			}

		} else {
			logService
					.adicionaLog(
							TipoLog.INFORMACAO,
							keyLog,
							"Sem reservas com checkin realizado a serem atualizadas no CM",
							null, null);
		}

		logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
				"Término da da atualização de reservas checkin do cm", null,
				null);
	}

	/**
	 * Método que importa as fnrhs preenchidas pelo hóspede para a base CM.
	 * 
	 * @throws BusinessRuleException
	 */
	public void exportarFnrhsCMViaAgendadorTarefa()
			throws IllegalStateException, PersistenceException, Exception {

		String keyLog = GeradorUtil.geraCodidoUUID();
		logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
				"Inicio na exportação das fnrhs para o cm", null, null);

		List<Reserva> reservas = reservaRepo.getReservasParaExportacaoCM();
		if (reservas.size() > 0) {
			for (Reserva reserva : reservas) {
				if (statusCheckinCheckout(reserva)) {
					continue;
				}
				
				try {
					logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
							"Reserva : " + reserva.getId()
									+ " Inicio da exportação das fnrhs", "",
							reserva.getId().toString());
					List<Fnrh> fnrhs = fnrhRepo.getFnrhsPorReserva(reserva
							.getId());

					if (fnrhs.size() > 0) {
						RelacionaReservaFNRHCM relacionamentoReservaFNRH = setDadosRelacionaReservaFNRHCM(reserva);
						relacionamentoReservaFNRH
								.setTipo(RelacionaReservaFNRHCM.TIPO_EXPORTACAO);

						cmRepo.exportaFnrhsPorReservaParaCM(fnrhs,
								relacionamentoReservaFNRH, reserva.getStatusIntegracao());
							if (reserva.getStatusIntegracao().equals(StatusIntegracaoFNRH.DIG)) {
								reserva.setStatusIntegracao(StatusIntegracaoFNRH.EXP);
							}
							reservaRepo.merge(reserva);
						
					} else {
						reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERE);
						reservaRepo.merge(reserva);
						logService
								.adicionaLog(
										TipoLog.INFORMACAO,
										keyLog,
										"Reserva : "
												+ reserva.getId()
												+ " Sem fnrhs a serem exportadas para o CM",
										"Reserva : "
												+ reserva.getId()
												+ " Sem fnrhs a serem exportadas para o CM",
										null);
					}

				} catch (IllegalStateException i) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERE);
					reservaRepo.merge(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na exportação das Fnrhs",
							i.getMessage(), reserva.getId().toString());
					//throw new IllegalStateException(i.getMessage());
				} catch (PersistenceException p) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERE);
					reservaRepo.merge(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na exportação das Fnrhs",
							p.getMessage(), reserva.getId().toString());
					//throw new PersistenceException(p.getMessage());
				} catch (Exception e) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERE);
					reservaRepo.merge(reserva);
					logService.adicionaLog(TipoLog.FALHA, keyLog, "Reserva : "
							+ reserva.getId()
							+ "- Problema na exportação das Fnrhs",
							e.getMessage(), reserva.getId().toString());
					//throw new Exception(e.getMessage());
				}
			}
		} else {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Sem reservas a serem exportadas para o CM", null, null);
		}

		logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
				"Término da exportação das fnrhs para o cm", null, null);

	}
	
	private boolean statusCheckinCheckout(Reserva reserva) {
		if (reserva.getStatusIntegracao().equals(StatusIntegracaoFNRH.PRE)){
			Long status = verificaStatusVHF(reserva.getNumReservaErp());
			if (status.equals(1L) || status.equals(2L)) {//check-in e check-out{
				return true;				
			}
			
		}
		return false;
	}

	/**
	 * Método que envia emails para as reservas com status "Exportado" na base
	 * FNRH para os hóspedes. Caso a reserva não possua email o status será
	 * atualizado para "Hospede sem email" Caso a reserva possua email inválido
	 * "Email inválido" Caso o email seja enviado o status será "Email enviado"
	 * Caso ocorra falha no envio de email "Erro no envio do e-mail"
	 * 
	 * @throws BusinessRuleException
	 */

	public void enviaEmailReservasImportadas() throws BusinessRuleException {
		// Insere log no inicio do envio do email
		String logKey = GeradorUtil.geraCodidoUUID();
		logService.adicionaLog(TipoLog.INFORMACAO, logKey,
				"Inicio do envio do email",
				"Envio de email de reservas confirmadas no CM", null);
		Parametrizacao parametrizacaoAntEnvioEmail = parametrizacaoRepo
				.getPorTipo(Parametrizacao.ANTECEDENCIA_ENVIO_EMAIL);

		if (parametrizacaoAntEnvioEmail == null) {
			logService.adicionaLog(TipoLog.INFORMACAO, logKey,
					"Parametrizacao",
					"Não existe parametrização de envio de mail cadastrado",
					null);
			return;
		} else if (parametrizacaoAntEnvioEmail.getQuantidade() == null) {
			logService
					.adicionaLog(
							TipoLog.INFORMACAO,
							logKey,
							"Parametrizacao Envio Email",
							"Número de dias de antecedência não definido (campo Quantidade)",
							null);
			return;
		}

		List<Reserva> reservasEnvioEmail = reservaRepo
				.getReservasAptasEnvioEmail(listaStatusEnvioEmail(),
						parametrizacaoAntEnvioEmail.getQuantidade());
		if (reservasEnvioEmail.size() <= 0) {
			logService.adicionaLog(TipoLog.FALHA, logKey,
					"Não há emails a serem enviados", null, null);

			logService.adicionaLog(TipoLog.INFORMACAO, logKey,
					"Término do envio do email", null, null);
			return;
		}
		for (Reserva reserva : reservasEnvioEmail) {
			try {
			
				// Quando não há email definido a reserva é a atualizada pra
				// Hóspede sem email
				if (StringHelper.isEmpty(reserva.getEmail())) {
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.HSE);
					atualizaReserva(reserva);

					// Quando o email definido é inválido a reserva é a
					// atualizada pra Email Inváido
				//} else if (!EmailUtil.emailValido(reserva.getEmail().trim())) {
				} else if (!emailValido(reserva.getEmail().trim())) {						
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.EIV);
					atualizaReserva(reserva);
				} else {
					try {
						// Caso o envio do email ocorra a contento o status da
						// reserva passa para Email enviado
						if(enviaEmail(reserva)){
							reserva.setStatusIntegracao(StatusIntegracaoFNRH.MAI);
						}else{
							reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERM);
						}
						
						atualizaReserva(reserva);

					} catch (FacesException fe) {
						// Caso ocorra falha no envio do email o status da
						// reserva passa Erro no envio do e-mail
						reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERM);
						atualizaReserva(reserva);

						LastException lastException = new LastException();
						Throwable th = lastException.findLastException(fe);
						if (th instanceof AuthenticationFailedException) {
							System.out
									.println("Caiu na AuthenticationFailedException"
											+ th.getMessage());
							logService.adicionaLog(TipoLog.FALHA, logKey,
									"Falha ao enviar email da reserva", th
											.getMessage(), reserva
											.getIdReservaErp().toString());

						}

						System.out.println("Caiu na FacesException"
								+ fe.getMessage());
						logService.adicionaLog(TipoLog.FALHA, logKey,
								"Falha ao enviar email da reserva", th
										.getMessage(), reserva
										.getIdReservaErp().toString());

					} catch (Exception e) {
						// Caso ocorra falha no envio do email o status da
						// reserva passa Erro no envio do e-mail
						LastException lastException = new LastException();
						Throwable th = lastException.findLastException(e);

						reserva.setStatusIntegracao(StatusIntegracaoFNRH.ERM);
						atualizaReserva(reserva);
						System.out
								.println("Caiu na Exception - Message :"
										+ th.getMessage() + "- Cause -"
										+ th.getCause());
						e.printStackTrace();
						logService.adicionaLog(TipoLog.FALHA, logKey,
								"Falha ao enviar email da reserva", e
										.getMessage(), reserva
										.getIdReservaErp().toString());
					}
				}

				logService.adicionaLog(TipoLog.INFORMACAO, logKey,
						"Término do envio do email",
						"Envio de email de reservas confirmadas no CM", null);
			} catch (Exception e) {
				logService.adicionaLog(TipoLog.FALHA, logKey,
						"Falha ao percorrer lista de reservas", e.getMessage(),
						null);
			}
		}
		
	//	}
	}

	/** Utilitários **/
	public List<ReservaCMFNRH> getReservasConfirmadasParaImportacao()
			throws Exception {
		return cmRepo.getReservasConfirmadasParaImportacao();
	}
	
	public List<Reserva> getReservasExternasParaImportacao()
			throws Exception {
		return baseFnrhExternaRepo.getReservasExternasParaImportacao();
	}
	
	public List<Long> getReservasCheckinParaAtualizacao() throws Exception {
		return cmRepo.getReservasCheckinParaAtualizacao();
	}

	/* Não está sendo usado
	 * public List<ReservaCMFNRH> getReservasConfirmadasPorHotelParaImportacao(
			Long hotelId) {
		return cmRepo.getReservasConfirmadasPorHotelParaImportacao(hotelId);
	}*/

	public List<ReservaCMFNRH> getHospedesPorReserva(Long reservaId,
			Long hotelId) {
		return cmRepo.getHospedesPorReserva(reservaId, hotelId);
	}

	public Reserva setDadosReserva(ReservaCMFNRH reservaCM, Reserva reserva) {
		// Falta status
		if (reserva.getId() == null) {
			reserva.setDataInsercao((java.util.Date) timeService
					.getDatabaseTimestamp());
			reserva.setUuid(GeradorUtil.geraCodidoUUID());
			reserva.setId(null);
		}

		reserva.setIdReservaErp(reservaCM.getReservaId());
		reserva.setNumReservaErp(reservaCM.getReservaNum());

		if (reservaCM.getHotelId() != null) {
			Hotel hotel = hotelRepo.getPorCodigo(reservaCM.getHotelId()
					.toString());
			reserva.setHotel(hotel);
		} else {
			reserva.setHotel(null);
		}

		if (reservaCM.getEmail() != null) {
			reserva.setEmail(reservaCM.getEmail());
		} else {
			reserva.setEmail(null);
		}

		if (reservaCM.getDataPrevistaCheckIn() != null) {
			reserva.setDataCheckinPrevisto(reservaCM.getDataPrevistaCheckIn());
		} else {
			reserva.setDataCheckinPrevisto(null);
		}

		if (reservaCM.getDataPrevistaCheckOut() != null) {
			reserva.setDataCheckoutPrevisto(reservaCM.getDataPrevistaCheckOut());
		} else {
			reserva.setDataCheckoutPrevisto(null);
		}
		
		if (reservaCM.getOrigem() != null) {
			reserva.setOrigem(reservaCM.getOrigem());
		} else {
			reserva.setOrigem(null);
		}
		
		if (reservaCM.getOrigem() != null) {
			reserva.setOrigemCod(reservaCM.getOrigemCod());
		} else {
			reserva.setOrigemCod(null);
		}
		reserva.setDataUpdate((java.util.Date) timeService
				.getDatabaseTimestamp());

		return reserva;
	}

	public RelacionaReservaFNRHCM setDadosRelacionaReservaFNRHCM(
			ReservaCMFNRH reservaCM) {
		RelacionaReservaFNRHCM relacionamento = new RelacionaReservaFNRHCM();
		relacionamento.setReservaCM(reservaCM.getReservaId());
		relacionamento.setHotelId(reservaCM.getHotelId());
		relacionamento.setData(timeService.getDatabaseTimestamp());
		return relacionamento;
	}

	public RelacionaReservaFNRHCM setDadosRelacionaReservaFNRHCM(Reserva reserva) {
		RelacionaReservaFNRHCM relacionamento = new RelacionaReservaFNRHCM();
		relacionamento.setReservaCM(reserva.getIdReservaErp());
		relacionamento.setHotelId(Long.parseLong(reserva.getHotel()
				.getCodigoErp()));
		relacionamento.setData(timeService.getDatabaseTimestamp());
		return relacionamento;
	}
	
	public List<FnrhDocumento> setaDadosDocumento(Long pessoaId, Fnrh fnrh) {
		List<Object[]> documentoList = cmRepo.getDocumentoPessoa(pessoaId);
		
		List<FnrhDocumento> fnhDocumentoList = null;
		
		if(documentoList!=null && documentoList.size()>0){
			fnhDocumentoList = new ArrayList<FnrhDocumento>();
			FnrhDocumento fnrhDocumento; 
			for (Object[] documento : documentoList) {
				 fnrhDocumento = new FnrhDocumento(fnrh);
				if(documento[0] != null){
					Long id = ((BigDecimal) documento[0]).longValue();
					TipoDocumento td = tipoDocumentoRepo.getPorTipoDocumentoErp(id, Fnrh.LINGUA_PADRAO);
					if(td!=null){
						fnrhDocumento.setTipoDocumento(td);
						if(documento[1]!=null){
							fnrhDocumento.setNumero(documento[1].toString());
						}
						
						if(documento[2]!=null){
							fnrhDocumento.setOrgao(documento[2].toString());
						}else{
							fnrhDocumento.setOrgao(null);
						}
						fnhDocumentoList.add(fnrhDocumento);
					}					
				}
			}
		}
		
		return fnhDocumentoList;
		
	}

	public Fnrh setaDadosFnrh(ReservaCMFNRH reservaCM, Fnrh fnrh) {
		// Questionar como ficará a definição da lingua
		// Colocar temporariamente como português
		if (fnrh.getId() == null) {
			fnrh.setDataInsercao(timeService.getDatabaseTimestamp());
			fnrh.setContaHotelId(reservaCM.getContaId());
			fnrh.setHospedeId(reservaCM.getHospedeId());
		}

		fnrh.setDataUpdate(timeService.getDatabaseTimestamp());
		fnrh.setReserva(reservaRepo.getPorIdErp(reservaCM.getReservaId()));

		// Dados do hospede
		if (!StringHelper.isEmpty(reservaCM.getHospedeNome())) {
			fnrh.setNome(reservaCM.getHospedeNome());
		} else {
			fnrh.setNome(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getHospedeSobrenome())) {
			fnrh.setSobrenome(reservaCM.getHospedeSobrenome());
		} else {
			fnrh.setSobrenome(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getPrincipal())) {
			fnrh.setPrincipal(reservaCM.getPrincipal());
		} else {
			fnrh.setPrincipal(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getProfissao())) {
			fnrh.setProfissao(reservaCM.getProfissao());
		} else {
			fnrh.setProfissao(null);
		}

		if (reservaCM.getDataNascimento() != null) {
			fnrh.setDataNascimento(reservaCM.getDataNascimento());
		} else {
			fnrh.setDataNascimento(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEmail())) {
			fnrh.setEmail(reservaCM.getEmail());
		} else {
			fnrh.setEmail(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getGenero())) {
			fnrh.setGenero(generoRepo.getPorCodigo(reservaCM.getGenero(),
					Fnrh.LINGUA_PADRAO));
		} else {
			fnrh.setGenero(null);
		}

		// Paises
		if (!StringHelper.isEmpty(reservaCM.getPaisDestinoCod())) {
			fnrh.setPaisDestino(reservaCM.getPaisDestinoCod());
		} else {
			fnrh.setPaisDestino(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getPaisNacionalidadeCod())) {
			fnrh.setPaisNacionalidade(reservaCM.getPaisNacionalidadeCod());
		} else {
			fnrh.setPaisNacionalidade(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getPaisOrigemCod())) {
			fnrh.setPaisOrigem(reservaCM.getPaisOrigemCod());
		} else {
			fnrh.setPaisOrigem(null);
		}

		if (reservaCM.getPaisResidenciaId() != null
				|| !StringHelper.isEmpty(reservaCM.getPaisResidenciaCod())) {
			PaisCM pcm = null;
			if (reservaCM.getPaisResidenciaId() != null) {
				pcm = paisCMRepo.getPorId(reservaCM.getPaisResidenciaId());
			} else if (!StringHelper.isEmpty(reservaCM.getPaisResidenciaCod())) {
				pcm = paisCMRepo.getPorCodigoISO(reservaCM
						.getPaisResidenciaCod());
			}

			if (pcm != null) {
				fnrh.setPaisResidencia(pcm.getCodigoISO());
			} else {
				fnrh.setPaisResidencia(null);
			}

		} else {
			fnrh.setPaisResidencia(null);
		}

		// Estados
		if (!StringHelper.isEmpty(reservaCM.getEstadoDestinoCod())) {
			fnrh.setEstadoDestino(reservaCM.getEstadoDestinoCod());
		} else {
			fnrh.setEstadoDestino(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEstadoOrigemCod())) {
			fnrh.setEstadoOrigem(reservaCM.getEstadoOrigemCod());
		} else {
			fnrh.setEstadoOrigem(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEstadoResidenciaCod())) {
			fnrh.setEstadoResidencia(reservaCM.getEstadoResidenciaCod());
		} else {
			fnrh.setEstadoResidencia(null);
		}

		// Cidades
		if (!StringHelper.isEmpty(reservaCM.getCidadeDestinoCodIGGE())) {
			fnrh.setCidadeCodDestino(reservaCM.getCidadeDestinoCodIGGE());
		} else {
			fnrh.setCidadeCodDestino(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getCidadeOrigemCodIGGE())) {
			fnrh.setCidadeCodOrigem(reservaCM.getCidadeOrigemCodIGGE());
		} else {
			fnrh.setCidadeCodOrigem(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getCidadeResidenciaCodIGGE())) {
			fnrh.setCidadeCodResidencia(reservaCM.getCidadeResidenciaCodIGGE());
		} else {
			fnrh.setCidadeCodResidencia(null);
		}

		// Endereço
		if (!StringHelper.isEmpty(reservaCM.getEnderecoResidenciaCEP())) {
			fnrh.setCepResidencia(reservaCM.getEnderecoResidenciaCEP());
		} else {
			fnrh.setCepResidencia(null);
		}
		
		if (!StringHelper.isEmpty(reservaCM.getEnderecoResidenciaBairro())) {
			fnrh.setBairroResidencia(reservaCM
					.getEnderecoResidenciaBairro());
		} else {
			fnrh.setBairroResidencia(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEnderecoResidenciaLogradouro())) {
			fnrh.setLogradouroResidencia(reservaCM
					.getEnderecoResidenciaLogradouro());
		} else {
			fnrh.setLogradouroResidencia(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEnderecoResidenciaNumero())) {
			fnrh.setNumeroResidencia(reservaCM.getEnderecoResidenciaNumero());
		} else {
			fnrh.setNumeroResidencia(null);
		}

		if (!StringHelper.isEmpty(reservaCM.getEnderecoResidenciaComplemento())) {
			fnrh.setComplementoResidencia(reservaCM
					.getEnderecoResidenciaComplemento());
		} else {
			fnrh.setComplementoResidencia(null);
		}

		// Dados complementares
		if (reservaCM.getMotivoViagem() != null) {
			fnrh.setMotivoViagem(motivoViagemRepo.getPorCodigoErp(reservaCM
					.getMotivoViagem().getCodigo(), Fnrh.LINGUA_PADRAO));
		} else {
			fnrh.setMotivoViagem(null);
		}

		if (reservaCM.getMeioTransporte() != null) {
			fnrh.setMeioTransporte(meioTransporteRepo.getPorCodigoErp(reservaCM
					.getMeioTransporte().getCodigo(), Fnrh.LINGUA_PADRAO));
		} else {
			fnrh.setMeioTransporte(null);
		}
		
		
		fnrh.setTelefoneDDI(reservaCM.getTelefoneDDI() );
		fnrh.setTelefoneDDD(reservaCM.getTelefoneDDD());
		fnrh.setTelefoneNumero(reservaCM.getTelefoneNum());
		
		fnrh.setCelularDDI(reservaCM.getCelularDDI());
		fnrh.setCelularDDD(reservaCM.getCelularDDD());
		fnrh.setCelularNumero(reservaCM.getCelularNum());
		
		fnrh.setFnrhDocumentoList(setaDadosDocumento(reservaCM.getHospedeId(), fnrh));

		return fnrh;
	}

	private List<StatusIntegracaoFNRH> listaStatusEnvioEmail() {
		List<StatusIntegracaoFNRH> status = new ArrayList<StatusIntegracaoFNRH>();
		status.add(StatusIntegracaoFNRH.IMP);
		status.add(StatusIntegracaoFNRH.ERM);

		return status;
	}
	
	//@Asynchronous
	private boolean enviaEmail(Reserva reserva) throws Exception {
		//if (facesContext == null) return;
		
		try {
			//Parametriza o e-mail de destino para não enviar para os clientes em ambiente de teste
			Parametrizacao parametrizacaoEmailTeste = parametrizacaoRepo
					.getPorTipo(Parametrizacao.EMAIL_TESTES);			
			
			emailFrom = EmailUtil.EMAIL_PADRAO;
			Parametrizacao parametrizacaoEmailCCO= parametrizacaoRepo
					.getPorTipo(Parametrizacao.EMAIL_CCO);	
			if (parametrizacaoEmailCCO != null && !parametrizacaoEmailCCO.getDescricao().equals("")) {
				emailCco=parametrizacaoEmailCCO.getDescricao();
			}else {
				emailCco=EmailUtil.EMAIL_PADRAO_INTERNO;
			}
						
			emailDestinatarios = new ArrayList<String>();	
			if (parametrizacaoEmailTeste != null && !parametrizacaoEmailTeste.getDescricao().equals("")) {
				emailDestinatarios.add(parametrizacaoEmailTeste.getDescricao());
			} else{
				emailDestinatarios.addAll(obtemEnderecosEmailValidos(reserva.getEmail().trim()));
			}
			
			emailAssunto = "Cadastro de FNRH Online";
			emailLink = montaLinkCadastroFNRH(reserva.getUuid());
			
			setContent(emailLink);
			System.out.println("Entrou no envio de email");
			renderer.render("/MailFnrh.xhtml");
			System.out.println("Email enviado");
			return true;
			
		}	catch (Exception e) {
			LastException lastException = new LastException();
			Throwable th = lastException.findLastException(e);
			e.printStackTrace();
			th.printStackTrace();
			return false;
		}

	}
	
	
		private void setContent(String link) {
			content = "  <html> ";
			content += " <head> ";
			content += "     <title>Preparados para tirar Férias?.</title> ";
			content += "     <meta name=\"viewport\" content=\"width=device-width\"> ";
			content += "     <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> ";
			content += "         <style media=\"all\" type=\"text/css\"> ";
			content += "             @media only screen and (max-width: 420px) { ";
			content += " 			  .main { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 720px) { ";
			content += " 			  .table { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 420px) { ";
			content += " 			  .header { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; height: auto; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 420px) { ";
			content += " 			  .slider img { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 420px) { ";
			content += " 			  td img { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			}             ";
			content += "             @media only screen and (max-width: 420px) {                                   ";
			content += " 			  .nav { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; align-content: center; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 189px) {                                   ";
			content += " 			  .nav img { ";
			content += " 				display: block; ";
			content += " 				width: 80% !important; align-content: center; text-align: center; ";
			content += " 			  } ";
			content += " 			}                                    ";
			content += "             @media only screen and (max-width: 201px) {                                   ";
			content += " 			  .logo img { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; align-content: center; text-align: center; ";
			content += " 			  } ";
			content += " 			}  ";
			content += "             @media only screen and (max-width: 420px) {                                   ";
			content += " 			  .nav  a { ";
			content += " 				display: inline-block; ";
			content += " 				width: 100% !important;  margin-top: 10px; text-align: center; align-content: center; ";
			content += " 			  } ";
			content += " 			} ";
			content += "             @media only screen and (max-width: 154px) { ";
			content += " 			  .txt-1 img { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			}             ";
			content += "             @media only screen and (max-width: 567px) { ";
			content += " 			  table img { ";
			content += " 				display: block; ";
			content += " 				width: 100% !important; ";
			content += " 			  } ";
			content += " 			}             ";
			content += " 		</style> ";
			content += "     </head> ";
			content += " 	<body style=\"background-color: #ffffff; text-align: center; max-width: 600px; margin: auto;padding: 0px;\"> ";
			content += "         <div align=\"center\" class=\"header\"  ";
			content += " 		style=\"height:48px;width:600px;background-color:#f3eded;color:#929292;font-size:12px;margin-top:10px;line-height:15px;max-height:68px;\"> ";
			content += " 			<!--INICIO HEADER--> ";
			content += " 			<tr align=\"center\" class=\"header\" style=\"width:600px;color:#929292;font-size:12px;margin-top:10px;line-height:15px;height:48px;\">";
			content += " 			<!--INICIO HEADER--> ";
			content += " 				<br> ";
			content += " 				<td align=\"center\" style=\"color:#929292;font-family:arial;font-size:10px;margin-top:20px;\"> ";
			content += " 					<span style=\"font-family:arial;\">Preparados para tirar Férias?.</span> ";
			content += " 					<a conversion=\"true\" href=\"https://goo.gl/TU2xMS\" class=\"link-version\" ";
			content += "  						style=\"margin:auto;padding:0px;text-decoration:underline; ";
			content += " 						display:inline-block;margin-left:10px;font-size:10px;font-weight:600;color:#006990;\"> Veja a vers&atilde;o web</a> ";
			content += " 				</td> ";
			content += " 			</tr> ";
			content += " 		</div> ";
			content += " 		<!-- FIM HEADER--> ";
			content += "         <br> ";
			content += "         <table align=\"center\" class=\"nav\"> ";
			content += " 			<!--INICIO NAV--> ";
			content += "             <td class=\"logo\" align=\"center\"> ";
			content += " 				<a target =\"_blank\" href=\"https://goo.gl/fBZMjr\"><img align=\"center\" border=\"0\"  ";
			content += " 				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/07/promo-agentes/logo-color%20copy.png\" /></a> ";
			content += " 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
			content += "             </td> ";
			content += "             <td align=\"center\"> ";
			content += " 				<a align=\"center\" href=\"https://goo.gl/HTRmG3\"  ";
			content += " 				style=\"color:#e71939;font-size:15px;font-family:arial;text-decoration:none;text-align:center; ";
			content += " 				font-style: normal; \">Beach Park</a>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; ";
			content += " 				<a align=\"center\" href=\"https://goo.gl/zAcNyR\" style=\"color:#ee7b07;font-size:15px;font-family:arial;text-decoration:none; ";
			content += " 				text-align:center;\">Promoções</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
			content += " 				<a align=\"center\" href=\"https://goo.gl/3ssK3h\"style=\"color:#00a8cb;font-size:15px;font-family:arial;text-decoration:none; ";
			content += " 				text-align: center; \">Ingressos</a> ";
			content += " 			</td> ";
			content += "         </table> ";
			content += " 		<!--FIM NAV--> ";
			content += "         <br> ";
			content += "         <div class=\"slider\"> ";
			content += " 			<!-- INICIO SLIDER--> ";
			content += "             <td><a conversion=\"true\" href=\""+link+"\"><img border=\"0\"  ";
			content += " 				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/header.gif\"/></a> ";
			content += " 			</td> ";
			content += "         </div> ";
			content += "         <br><br> ";
			content += "         <table align=\"center\"> ";
			content += " 			<td align=\"center\"> ";
			content += " 				<strong style=\"font-family: arial;font-size:20px;color:#fc5d0d;\"> ";
			content += " 				Faça já o seu check-in e fique mais perto de<br>descobrir a felicidade com a gente.</strong> ";
			content += "             </td> ";
			content += "         </table> ";
			content += " 		<br> ";
			content += "         <table align=\"center\"> ";
			//content += " 			<td>				 ";
			//content += " 				<img src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/bolha.png\"/> ";
			//content += "             </td> ";
			content += " <td align=\"Center\" style=\"font-family:arial;font-size:18px;color: #939393;\">";
		    content += "     Em caso de hospedagem de menores de idade, confira as<br> <a href=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/regras.html\" style=\"color:fc5d0d ;\" ><u>regras aqui</u></a> e baixe a ficha de autorização.";
	        content += " </td>";
	        content += " </table>";
			content += " <br> ";
	        content += " <table align=\"center\">";
	        content += "  <td>";
	        content += " <a href=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/ficha.docx\"><img src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/but1.png\"/></a>";
	        content += " </td>";
	        
			
			
			content += "         </table> ";
			content += "         <br><br> ";
			content += " 		<!--INICIO FOOTER--> ";
			content += "         <div class=\"footer\" style=\"height: 390px ; background-color: #f1f1f1 ;  text-align: center; \"> ";
			content += " 			<img src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/09/passaporte-checkin/info.png\"/> ";
			content += " 			<br><br><br> ";
			content += " 			<!-- INICIO NAV--> ";
			content += " 			<div class=\"nav\" style=\"color:#939393;font-size:13px;font-family:Arial,  ";
			content += " 				Helvetica,sans-serif;list-style:none;text-align:center;line-height: ";
			content += " 				25px; text-decoration: none; letter-spacing:0.5px;\"> ";
			content += " 				<a  align=\"center\" conversion=\"true\" href=\"https://goo.gl/HTRmG3\" target=\"_blank\"  ";
			content += " 				style=\"list-style:none;text-decoration:none;color:  ";
			content += " 				#979797;display:inline;\">BEACH PARK</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
			content += " 				<a align=\"center\" conversion=\"true\" href=\"https://goo.gl/zAcNyR\" target=\"_blank\" ";
			content += "  				style=\"list-style:none;text-decoration:none;color:  ";
			content += " 				#979797;display:inline;\">PROMOÇÕES</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";
			content += " 				<a align=\"center\" conversion=\"true\" href=\"https://goo.gl/3ssK3h\" target=\"_blank\"  ";
			content += " 				style=\"list-style:none;text-decoration:none;color: ";
			content += " 				#979797;display:inline; \">INGRESSOS</a> ";
			content += " 				<br> ";
			content += " 				<!--FIM NAV--> ";
			content += " 			</div> ";
			content += " 			<br><br> ";
			content += " 			<!--INICIO SOCIAL--> ";
			content += "             <div class=\"social\" style=\"border: 0px;\"> ";
			content += "                 <a conversion=\"true\" href=\"https://goo.gl/2Trkbe\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br/2017/julho/programacao-vila/images/1fb.png\" target=\"_blank\"></a> ";
			content += "                 <a conversion=\"true\" href=\"https://goo.gl/jpWGMv\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br/2017/julho/programacao-vila/images/2inst.png\"target=\"_blank\"></a> ";
			content += "                 <a conversion=\"true\" href=\"https://goo.gl/szj9tc\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br/2017/julho/programacao-vila/images/3tw.png\"target=\"_blank\"></a> ";
			content += "                 <a conversion=\"true\" href=\"https://goo.gl/GweoLq\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/05/feriado/images/4yt.png\"target=\"_blank\"></a> ";
			content += "                 <a conversion=\"true\" href=\"https://goo.gl/SqmWPj\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/05/feriado/images/5ta.png\" target=\"_blank\"></a> ";
			content += "             </div> ";
			content += "             <!--FIM SOCIAL--> ";
			content += " 			<!--FIM TXT-4--> ";
			content += " 			<!--INICIO INFORMACOES--> ";
			content += " 			<div class=\"informacoes\" style=\"color:#929292;font-size:14px;font-family:Arial,Helvetica,sans-serif;color:#828282;border:0px;\"> ";
			content += " 				<img style=\"border: 0px;\" ";
			content += "  				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/vencimentocartao/imagens/iconduvida.jpg\"/> <strong ";
			content += " 				style=\"font-family:arial;font-size:12px;color:#939393;\">PRECISA DE AJUDA?</strong> <span ";
			content += "  				style=\"font-family:arial;font-size:10px;color: ";
			content += " 				#939393;\">&nbsp;&nbsp;&nbsp; (85) 4012-3000  &nbsp;&nbsp;&nbsp;contato@beachpark.com.br </span> ";
			content += " 				<img  src=\"http://newsletters.beachpark.com.br/2017/junho/html_test/images/line.png\"/ style=\"margin-top: 10px;\"><br> ";
			content += " 				<br> ";
			content += " 			</div> ";
			content += "             <!--FIM INFORMACOES--> ";
			content += "             <!--INICIO ICON BP--> ";
			content += "             <div class=\"icon-bp\" style=\"margin-top:10px; border: 0px;\"> ";
			content += " 				<a conversion=\"true\" href=\"https://goo.gl/jsPXzN\" style=\"margin:auto;padding:0px;color:#348eda;text-decoration:none;\"><img ";
			content += " 				src=\"http://newsletters.beachpark.com.br.s3.amazonaws.com/2018/vencimentocartao/imagens/iconbp.jpg\"/></a>             ";
			content += "             </div> ";
			content += "             <!-- FIM ICON BP--> ";
			content += "             <!--INICIOS LINKS--> ";
			content += "             <div class=\"links-rodape\" ";
			content += " 				 style=\"font-family:serif;color:#929292;font-size:14px;margin-top:5px;text-decoration:none;padding-bottom:20px;\"> ";
			content += " 				<p style=\"font-family: Arial,'Lucida Grande',sans-serif;font-weight:normal;margin-bottom:15px;margin: 0 15px;padding:20px 0 0; ";
			content += " 				line-height:20px;text-align:center;font-size:10px;color:#7f7e7e;\"> <a conversion=\"true\" href=\"https://goo.gl/g5BP8m\" ";
			content += " 							style=\"margin:auto;padding:0px;text-decoration:underline;color:#7f7e7e;\">Política de Privacidade </a></p> ";
			content += "             </div> ";
			content += "             <!--FIM LINKS-->  ";
			content += "         </div><!--FIM FOOTER-->     ";
			content += "     </body> ";
			content += " </html> "; 
			
		}
		
	

	private List<String> obtemEnderecosEmail(String emailReserva){		
		List<String> destinatarios =  new ArrayList<String>();
		emailReserva= emailReserva.replace(",", ";");
		
		String[] emails = emailReserva.split(";");
			
		for(String e : emails){
			if(StringHelper.isNotEmpty(e.trim())){
				destinatarios.add(e.trim());	
			}			
		}		
		
		return destinatarios;		
	}
	
	private List<String> obtemEnderecosEmailValidos(String emailReserva){		
		List<String> destinatarios =  new ArrayList<String>();
		emailReserva= emailReserva.replace(",", ";");
		
		String[] emails = emailReserva.split(";");
	
		
		for(String e : emails){
			if(StringHelper.isNotEmpty(e.trim())){
				if(EmailUtil.emailValido(e)){
					destinatarios.add(e.trim());
				}	
			}
					
		}		
		
		return destinatarios;		
	}
	
	private boolean emailValido(String email){
		// A pedido do Tibério se uma reserva tem mais de um email e pelo menos um é válido a reserva deve ser considerada com email válido
		List<String> destinatarios =  obtemEnderecosEmail(email);
		if(destinatarios!=null){
			for(String d : destinatarios){
				if(StringHelper.isNotEmpty(d.trim())){
					if(EmailUtil.emailValido(d)){ // Mudança aqui para se houver algum válido retorna true
						return true;
					}	
				}				
			}	
		}else{
			return false;
		}
		
		return false;
		
	}

	public void atualizaReserva(Reserva reserva) {
		reservaRepo.persist(reserva);
	}

	public void insereFnrhs(List<ReservaCMFNRH> reservasCM) {
		for (ReservaCMFNRH reservaCM : reservasCM) {
			Fnrh fnrh = new Fnrh();
			fnrh = setaDadosFnrh(reservaCM, fnrh);
			fnrhRepo.persist(fnrh);
		}
	}

	public List<Fnrh> getListFnrhsPorReserva(Long reservaId, Long hotelId) {
		List<ReservaCMFNRH> reservasHospedes = getHospedesPorReserva(reservaId,
				hotelId);
		List<Fnrh> fnrhs = new ArrayList<Fnrh>();
		for (ReservaCMFNRH reservaCM : reservasHospedes) {
			Fnrh fnrh = new Fnrh();
			fnrh = setaDadosFnrh(reservaCM, fnrh);
			fnrhs.add(fnrh);
		}

		return fnrhs;
	}

	public void insereReservas(List<ReservaCMFNRH> reservasCM) throws Exception {
		String logKey = GeradorUtil.geraCodidoUUID();

		for (ReservaCMFNRH reservaCM : reservasCM) {
			try {
				Reserva reservaFNRH = new Reserva();
				reservaFNRH = setDadosReserva(reservaCM, reservaFNRH);
				reservaFNRH.setStatusIntegracao(StatusIntegracaoFNRH.IMP);
				reservaRepo.persist(reservaFNRH);
				
				List<ReservaCMFNRH> reservasHospedes = getHospedesPorReserva(
						reservaCM.getReservaId(), reservaCM.getHotelId());
				//insereFnrhs(reservasHospedes);
				List<Fnrh> fnrhs = new ArrayList<Fnrh>();
				for (ReservaCMFNRH reservaHosp : reservasHospedes) {
					Fnrh fnrh = new Fnrh();
					fnrh = setaDadosFnrh(reservaHosp, fnrh);
					fnrhs.add(fnrh);
					fnrhRepo.persist(fnrh);
				}
				RelacionaReservaFNRHCM relacionamentoReservaFNRH = setDadosRelacionaReservaFNRHCM(reservaCM);
				relacionamentoReservaFNRH
						.setTipo(RelacionaReservaFNRHCM.TIPO_IMPORTACAO);
				cmRepo.insereRelacionamentoReservaCMEFNRH(relacionamentoReservaFNRH);
				
			} catch (Exception e) {
				logService.adicionaLog(TipoLog.FALHA, logKey,
						"Falha ao inserir reserva", e.getMessage(), reservaCM
								.getReservaId().toString());
				//throw new Exception(e.getMessage());
			}
		}

	}
	
	public void atualizaReservasImportadasBaseExterna(List<Reserva> reservas) throws Exception {
		String logKey = GeradorUtil.geraCodidoUUID();
		Long status;
		for (Reserva r : reservas) {
			if (statusCheckinCheckout(r) {
				continue;
			}
			try {			
				reservaRepo.merge(r);			
				List<Fnrh> fnrhs = baseFnrhExternaRepo.getFnrhsPorReserva(r);
				
				if(fnrhs.size()>0){
					for(Fnrh fnrh: fnrhs){
						fnrhRepo.merge(fnrh);
					}
					
				}
				

			} catch (Exception e) {
				logService.adicionaLog(TipoLog.FALHA, logKey,
						"Falha ao atualizar reserva importada da base externa", e.getMessage(), r.getIdReservaErp().toString());
			}
		}	
		
	}
	
	private Long verificaStatusVHF(Long numReservaErp) {
		return cmRepo.getStatusReserva(numReservaErp);
	}

	public String montaLinkCadastroFNRH(String uuid) {		
		String link = TomcatUtil.obterValorPorChave("wsTipoConexaoFnrh")
				+ TomcatUtil.obterValorPorChave("wsAddressFnrh")
				//+ ":"
				//+ TomcatUtil.obterValorPorChave("wsPortFnrh")
				+ "/fnrh/home.seam?uuid=" + uuid;
		return link;
	}

	public String montaLinkImagensFNRH() {
		String link = TomcatUtil.obterValorPorChave("wsCaminhoImagens");
		return link;
	}
	
	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailAssunto() {
		return emailAssunto;
	}

	public void setEmailAssunto(String emailAssunto) {
		this.emailAssunto = emailAssunto;
	}

	public String getEmailCorpo() {
		return emailCorpo;
	}

	public void setEmailCorpo(String emailCorpo) {
		this.emailCorpo = emailCorpo;
	}

}
