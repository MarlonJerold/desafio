package br.com.beachpark.fnrhAdm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;
import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.security.Identity;

import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.beachpark.fnrhAdm.model.Hotel;
import br.com.beachpark.fnrhAdm.model.ListaPercentualStatusReserva;
import br.com.beachpark.fnrhAdm.model.ListaReserva;
import br.com.beachpark.fnrhAdm.model.Reserva;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.beachpark.fnrhAdm.model.TipoLog;
import br.com.beachpark.fnrhAdm.repo.FnrhRepo;
import br.com.beachpark.fnrhAdm.repo.HotelRepo;
import br.com.beachpark.fnrhAdm.repo.ReservaRepo;
import br.com.beachpark.fnrhAdm.repo.UsuarioRepo;
import br.com.beachpark.fnrhAdm.service.LogService;
import br.com.padrao.exception.BusinessRuleException;
import br.com.padrao.security.Usuario;
import br.com.padrao.util.EmailUtil;
import br.com.padrao.util.GeradorUtil;

@Name("consultaReservaCtrl")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class ConsultaReservaCtrl implements Serializable{


	private static final long serialVersionUID = -5326174131640294972L;
	
	@In
	private FacesMessages facesMessages;  
	
	@In
	private FnrhRepo fnrhRepo;
	
	@In
	private HotelRepo hotelRepo;
	
	@In
	private ReservaRepo reservaRepo;
	
	@In
	private UsuarioRepo usuarioRepo;
	
	@In
	private LogService logService;
	
	@In
	private Identity identity;
	
	@RequestParameter
	private String reservaId;
	
	private List<Fnrh> fnrhs = new ArrayList<Fnrh>();
	private List<Hotel> hoteis = new ArrayList<Hotel>();
	private List<ListaReserva> listaReservas = new ArrayList<ListaReserva>();
	private List<ListaPercentualStatusReserva> listaPercentualStatusReserva = new ArrayList<ListaPercentualStatusReserva>();


	private StatusIntegracaoFNRH status;
	private Date dataInicial;
	private Date dataFinal;
	private String numeroReserva;
    private Hotel hotel;
    private Usuario usuarioLogado;
    private Reserva reserva;
    private String email;

    
	public List<ListaReserva> getListaReservas() {
		return listaReservas;
	}

	public void setListaReservas(List<ListaReserva> listaReservas) {
		this.listaReservas = listaReservas;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Hotel> getHoteis() {
		if(hoteis.size()<=0){
			hoteis = hotelRepo.getAllAtivosOrderDescricao();
		}
		return hoteis;
	}

	public void setHoteis(List<Hotel> hoteis) {
		this.hoteis = hoteis;
	}

	public String getNumeroReserva() {
		return numeroReserva;
	}

	public void setNumeroReserva(String numeroReserva) {
		this.numeroReserva = numeroReserva;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public StatusIntegracaoFNRH getStatus() {
		return status;
	}

	public void setStatus(StatusIntegracaoFNRH status) {
		this.status = status;
	}

	public List<Fnrh> getFnrhs() {
		return fnrhs;
	}

	public void setFnrhs(List<Fnrh> fnrhs) {
		this.fnrhs = fnrhs;
	}

	public List<StatusIntegracaoFNRH> getStatusIntegracaoList() {
		return listaStatusPossiveisConsulta();
	}
	
	@Begin(join=true)
	public String consultaReservas(){
		
		try {
			listaReservas=new ArrayList<ListaReserva>();
			if (validaPeriodo()) {
				listaReservas=reservaRepo.getReservasIntegracaoPorStatusPeriodoCheckin(hotel,status, dataInicial, dataFinal,numeroReserva,listaStatusPossiveisConsulta());		
				if(listaReservas.isEmpty()){
					facesMessages.add(Severity.ERROR,
							"#{messages['error.naoExisteDados']}");	
				}
				setListaPercentualStatusReserva(criaListaReservaStatus(listaReservas));
				
			}
		} catch (Exception be) {
			facesMessages
					.addFromResourceBundle(Severity.ERROR, be.getMessage());
			return null;
		}

		return "/ConsultaReserva.xhtml";
		
	}
	
	private List<ListaPercentualStatusReserva> criaListaReservaStatus(List<ListaReserva> listaReservas) {
		List<ListaPercentualStatusReserva> retorno = new ArrayList<ListaPercentualStatusReserva>();
		int totalReservas = listaReservas.size();
		MultiValueMap map = new MultiValueMap();   
		for(int i = 0; i<listaReservas.size(); i++) {
			ListaReserva listaReserva = listaReservas.get(i);
			map.put(listaReserva.getReserva().getStatusIntegracao(), listaReserva);
		}
		
		Set<StatusIntegracaoFNRH> keySet = map.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			StatusIntegracaoFNRH statusIntegracaoFNRH = (StatusIntegracaoFNRH) iterator.next();
			List list = (List)map.get(statusIntegracaoFNRH);
			double total = list.size();
			retorno.add(new ListaPercentualStatusReserva(statusIntegracaoFNRH,(total/totalReservas)));
		}
		return retorno;
	}

	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public String editarFnrhs(){
		return montaLinkCadastroFNRH(reserva.getUuid());	
	}
	
	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public String editarReserva(){
		String keyLog = GeradorUtil.geraCodidoUUID();
		listaReservas = new ArrayList<ListaReserva>();
		try {
			if(StringHelper.isNotEmpty(reservaId)){
				reserva  = reservaRepo.getPorId(Long.parseLong(reservaId));
				if(reserva!=null){
					logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
							"Edição de reserva : Usuário :" +getUsuarioLogado().getLogin(), null, reserva.getId().toString());
					email=reserva.getEmail();
					return "/ConsultaReservaEdit.xhtml";	
					
				}else{
					logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
							"Edição de reserva : Usuário :" +getUsuarioLogado().getLogin(), "Reserva não localizada", reservaId);
				}					
			}else{
				reserva=null;
			}
			
		} catch (Exception be) {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Erro na edição de reserva : Usuário :" +getUsuarioLogado().getLogin(), be.getMessage(), reservaId);
			facesMessages
					.addFromResourceBundle(Severity.ERROR, be.getMessage());
			return null;
		}

		return "/ConsultaReserva.xhtml";		
	}
	
	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public String confirmarEdicaoReserva(){
		String keyLog = GeradorUtil.geraCodidoUUID();
		if(StringHelper.isEmpty(email)){
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['email.obrigatorio']}");
			return "/ConsultaReservaEdit.xhtml";	
			
		}else if(!EmailUtil.emailValido(email)){
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['emailInvalido']}");
			return "/ConsultaReservaEdit.xhtml";				
		}
		
		try {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Edição de reserva : Usuário :" +getUsuarioLogado().getLogin(), null, reserva.getId().toString());
			reserva.setStatusIntegracao(StatusIntegracaoFNRH.IMP);
			reserva.setEmail(email);
			reservaRepo.persist(reserva);	
			facesMessages.add(Severity.INFO, "#{messages.registroGravado}");	
			return "/ConsultaReserva.xhtml";	
		} catch (Exception be) {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Erro na edição de reserva : Usuário :" +getUsuarioLogado().getLogin(), be.getMessage(), reservaId);
			facesMessages
					.addFromResourceBundle(Severity.ERROR, be.getMessage());
			return null;
		}

				
	}
	
	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public String ignorar(){		
		String keyLog = GeradorUtil.geraCodidoUUID();
		try {
			if(StringHelper.isNotEmpty(reservaId)){
				reserva  = reservaRepo.getPorId(Long.parseLong(reservaId));
				if(reserva!=null){
					logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
							"Ignorar reserva : Usuário :" +getUsuarioLogado().getLogin(), null, reserva.getId().toString());
					reserva.setStatusIntegracao(StatusIntegracaoFNRH.IGN);
					reservaRepo.persist(reserva);
					facesMessages.add(Severity.INFO, "#{messages.registroGravado}");						
				}else{
					logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
							"Ignorar reserva: Usuário :" +getUsuarioLogado().getLogin(), "Reserva não localizada", reservaId);
				}					
			}else{
				reserva=null;
			}
			
		} catch (Exception be) {
			logService.adicionaLog(TipoLog.INFORMACAO, keyLog,
					"Ignorar reserva : Usuário :" +getUsuarioLogado().getLogin(), be.getMessage(), reservaId);
			facesMessages
					.addFromResourceBundle(Severity.ERROR, be.getMessage());
			return null;
		}

		return "/ConsultaReserva.xhtml";	
	}
	
	public boolean validaPeriodo()
			throws BusinessRuleException {
		if (dataInicial == null && dataFinal != null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['dataInicialObrigatoria']}");
			return false;
			
		} else if (dataInicial != null && dataFinal == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['dataFinalObrigatoria']}");
			return false;
			
		} else if (dataInicial != null && dataFinal != null) {
			if (dataInicial.after(dataFinal)) {
				facesMessages.addFromResourceBundle(Severity.ERROR,
						"#{messages['error.datafinalmenorqueinicial']}");		
			return false;
			}
		}
		
		return true;
	}
	
	
	public List<StatusIntegracaoFNRH> listaStatusPossiveisConsulta(){
		return  Arrays.asList(StatusIntegracaoFNRH.values());
	}
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null) {
			usuarioLogado = usuarioRepo.getPorLogin(Identity.instance()
					.getPrincipal().getName());
		}
		return usuarioLogado;
	}
	
	public String montaLinkCadastroFNRH(String uuid){			
		String link = "/Fnrh.seam?uuid=" +uuid ;
		return link;		
	}

	public List<ListaPercentualStatusReserva> getListaPercentualStatusReserva() {
		return listaPercentualStatusReserva;
	}

	public void setListaPercentualStatusReserva(List<ListaPercentualStatusReserva> listaPercentualStatusReserva) {
		
		this.listaPercentualStatusReserva = listaPercentualStatusReserva;
	}
}
