package br.com.beachpark.fnrhAdm.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.international.StatusMessage.Severity;

import br.com.beachpark.fnrhAdm.model.Cep;
import br.com.beachpark.fnrhAdm.model.Cidade;
import br.com.beachpark.fnrhAdm.model.Estado;
import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.beachpark.fnrhAdm.model.Genero;
import br.com.beachpark.fnrhAdm.model.MeioTransporte;
import br.com.beachpark.fnrhAdm.model.MotivoViagem;
import br.com.beachpark.fnrhAdm.model.Pais;
import br.com.beachpark.fnrhAdm.model.Reserva;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.beachpark.fnrhAdm.model.TipoDocumento;
import br.com.beachpark.fnrhAdm.model.TipoDocumentoEnum;
import br.com.beachpark.fnrhAdm.repo.CepRepo;
import br.com.beachpark.fnrhAdm.repo.CidadeRepo;
import br.com.beachpark.fnrhAdm.repo.EstadoRepo;
import br.com.beachpark.fnrhAdm.repo.FnrhRepo;
import br.com.beachpark.fnrhAdm.repo.GeneroRepo;
import br.com.beachpark.fnrhAdm.repo.MeioTransporteRepo;
import br.com.beachpark.fnrhAdm.repo.MotivoViagemRepo;
import br.com.beachpark.fnrhAdm.repo.PaisRepo;
import br.com.beachpark.fnrhAdm.repo.ReservaRepo;
import br.com.beachpark.fnrhAdm.repo.TipoDocumentoRepo;
import br.com.padrao.util.CepUtil;
import br.com.padrao.util.DateUtil;
import br.com.padrao.util.EmailUtil;
import br.com.padrao.util.StringUtil;
import br.com.padrao.validator.CpfValidator;

@Name("fnrhCtrl")
@Scope(ScopeType.CONVERSATION)    
public class FnrhCtrl implements Serializable {

	private static final long serialVersionUID = -181591049482755858L;

	@In
	private FacesMessages facesMessages;

	@In
	private PaisRepo paisRepo;

	@In
	private GeneroRepo generoRepo;

	@In
	private EstadoRepo estadoRepo;

	@In
	private CidadeRepo cidadeRepo;

	@In
	private MotivoViagemRepo motivoViagemRepo;

	@In
	private TipoDocumentoRepo tipoDocumentoRepo;

	@In
	private MeioTransporteRepo meioTransporteRepo;

	@In
	private CepRepo cepRepo;

	@In
	private FnrhRepo fnrhRepo;

	@In
	private ReservaRepo reservaRepo;

	@RequestParameter
	private String uuid;

	@In
	private LocaleSelector localeSelector;

	private Fnrh fnrh;

	private Long fnrhSelecionada;
	private Reserva reserva;

	private String paisPadrao = Fnrh.PAIS_PADRAO;

	private List<Genero> generos = new ArrayList<Genero>();
	private List<MotivoViagem> motivosViagem = new ArrayList<MotivoViagem>();
	private List<MeioTransporte> meiosTransporte = new ArrayList<MeioTransporte>();
	private List<TipoDocumento> tiposDocumento = new ArrayList<TipoDocumento>();

	private List<Fnrh> fnrhs = new ArrayList<Fnrh>();

	private List<Pais> paisesNacionalidade = new ArrayList<Pais>();
	private List<Pais> paisesResidencia = new ArrayList<Pais>();
	private List<Pais> paisesOrigem = new ArrayList<Pais>();
	private List<Pais> paisesDestino = new ArrayList<Pais>();

	private List<Estado> estadosResidencia = new ArrayList<Estado>();
	private List<Estado> estadosOrigem = new ArrayList<Estado>();
	private List<Estado> estadosDestino = new ArrayList<Estado>();

	private List<Cidade> cidadesResidencia = new ArrayList<Cidade>();
	private List<Cidade> cidadesOrigem = new ArrayList<Cidade>();
	private List<Cidade> cidadesDestino = new ArrayList<Cidade>();

	private boolean haFnrhsNaoPreenchidas;
	
	public Long getFnrhSelecionada() {
		return fnrhSelecionada;
	}

	public void setFnrhSelecionada(Long fnrhSelecionada) {
		this.fnrhSelecionada = fnrhSelecionada;
	}

	public List<Fnrh> getFnrhs() {
		return fnrhs;
	}

	public void setFnrhs(List<Fnrh> fnrhs) {
		this.fnrhs = fnrhs;
	}

	public boolean isHaFnrhsNaoPreenchidas() {
		return haFnrhsNaoPreenchidas;
	}

	public void setHaFnrhsNaoPreenchidas(boolean haFnrhsNaoPreenchidas) {
		this.haFnrhsNaoPreenchidas = haFnrhsNaoPreenchidas;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public String getPaisPadrao() {
		return paisPadrao;
	}

	public void setPaisPadrao(String paisPadrao) {
		this.paisPadrao = paisPadrao;
	}

	public List<MeioTransporte> getMeiosTransporte() {		
		return meiosTransporte;
	}

	public void setMeiosTransporte(List<MeioTransporte> meiosTransporte) {
		this.meiosTransporte = meiosTransporte;
	}

	public List<TipoDocumento> getTiposDocumento() {
		return tiposDocumento;
	}

	public void setTiposDocumento(List<TipoDocumento> tiposDocumento) {
		this.tiposDocumento = tiposDocumento;
	}

	public List<MotivoViagem> getMotivosViagem() {
		return motivosViagem;
	}

	public void setMotivosViagem(List<MotivoViagem> motivosViagem) {
		this.motivosViagem = motivosViagem;
	}

	public List<Pais> getPaisesOrigem() {
		return paisesOrigem;
	}

	public void setPaisesOrigem(List<Pais> paisesOrigem) {
		this.paisesOrigem = paisesOrigem;
	}

	public List<Pais> getPaisesDestino() {
		return paisesDestino;
	}

	public void setPaisesDestino(List<Pais> paisesDestino) {
		this.paisesDestino = paisesDestino;
	}

	public List<Estado> getEstadosOrigem() {
		return estadosOrigem;
	}

	public void setEstadosOrigem(List<Estado> estadosOrigem) {
		this.estadosOrigem = estadosOrigem;
	}

	public List<Estado> getEstadosDestino() {
		return estadosDestino;
	}

	public void setEstadosDestino(List<Estado> estadosDestino) {
		this.estadosDestino = estadosDestino;
	}

	public List<Estado> getEstadosResidencia() {
		return estadosResidencia;
	}

	public void setEstadosResidencia(List<Estado> estadosResidencia) {
		this.estadosResidencia = estadosResidencia;
	}

	public List<Pais> getPaisesResidencia() {
		return paisesResidencia;
	}

	public void setPaisesResidencia(List<Pais> paisesResidencia) {
		this.paisesResidencia = paisesResidencia;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public List<Pais> getPaisesNacionalidade() {
		return paisesNacionalidade;
	}

	public void setPaisesNacionalidade(List<Pais> paisesNacionalidade) {
		this.paisesNacionalidade = paisesNacionalidade;
	}

	public Fnrh getFnrh() {
		return fnrh;
	}

	public void setFnrh(Fnrh fnrh) {
		this.fnrh = fnrh;
	}

	public List<Cidade> getCidadesResidencia() {
		return cidadesResidencia;
	}

	public void setCidadesResidencia(List<Cidade> cidadesResidencia) {
		this.cidadesResidencia = cidadesResidencia;
	}

	public List<Cidade> getCidadesOrigem() {
		return cidadesOrigem;
	}

	public void setCidadesOrigem(List<Cidade> cidadesOrigem) {
		this.cidadesOrigem = cidadesOrigem;
	}

	public List<Cidade> getCidadesDestino() {
		return cidadesDestino;
	}

	public void setCidadesDestino(List<Cidade> cidadesDestino) {
		this.cidadesDestino = cidadesDestino;
	}

	@Create
	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public void begin() {
		if (uuid != null) {
			carregaDadosReserva();
		} else {
			novo();
		}
	}

	@Begin(join = true,flushMode = FlushModeType.MANUAL)
	public String carregaDadosReserva() {
		fnrh = new Fnrh();
		reserva = new Reserva();
		haFnrhsNaoPreenchidas = true;
		setaReserva();
		carregaListasNovo();
		return "/Fnrh.xhtml";
	}

	@Begin(join = true,flushMode = FlushModeType.MANUAL)
	public String novo() {
		fnrh = new Fnrh();
		reserva = new Reserva();
		setaReserva();
		carregaListasNovo();
		return "/Fnrh.xhtml";
	}

	
	public String save() {
		try {
			if (validaSave()) {
				retiraCaracteresEspeciais();
				atualizaFnrh();
				carregaFnrhs();
				atualizaPreenchimentoFnrhs();
				carregaProximaFnrh();
				if(!haFnrhsNaoPreenchidas){
					saveReserva();
					facesMessages.add(Severity.INFO, "#{messages.registroGravado}");
					return "/ConsultaReserva.xhtml";
				}
				
			}
		} catch (Exception be) {
			facesMessages
					.addFromResourceBundle(Severity.ERROR, be.getMessage());
			return null;
		}

		return "/Fnrh.xhtml";
	}

	@End
	@Transactional
	public void saveReserva() {
		try {
			reserva.setStatusIntegracao(StatusIntegracaoFNRH.DIG);
			//reserva.setFnrhList(fnrhs);
			reservaRepo.persist(reserva);
			//facesMessages.add(Severity.INFO, "#{messages.registroGravado}");
			
		} catch (Exception be) {
			facesMessages.addFromResourceBundle(Severity.ERROR, be.getMessage());
			//return null;
		}
	}

	@End
	@Transactional
	private void atualizaFnrh() {
		fnrh.setPreenchimentoCompleto(true);
		fnrhRepo.persist(fnrh);
	}

	/* Métodos Auxiliares */

	private void setaReserva() {
		if (StringHelper.isNotEmpty(uuid)) {
			reserva = reservaRepo.getPorUUID(uuid);
			/*
			 * fnrhs = fnrhRepo.getFnrhsPorReserva(reserva.getId());
			 * if(fnrhs.size()>0){ buscaPrincipal(); }
			 */
			carregaFnrhs();						
			//buscaPrincipal();
			if(fnrh.getId()==null){
				carregaProximaFnrh();
			}
			
		}
	}

/*	private void carregaFnrhs() {
		if (reserva.getFnrhList().size() > 0) {
			fnrhs = reserva.getFnrhList();
		}
	}*/
	
	private void carregaFnrhs() {
		fnrhs = fnrhRepo.getFnrhsPorReserva(reserva.getId());
	}
	
	/*private void buscaPrincipal() {
		for (Fnrh fnrhPrincipal : fnrhs) {
			if (fnrhPrincipal.getPrincipal().equals(Fnrh.PRINCIPAL) && !fnrhPrincipal.isPreenchimentoCompleto()) { 
				fnrh = fnrhPrincipal;
				return;
			}
		}
	}*/

	public void setaDadosFnrh() {
		if (fnrh != null) {
			int indice = fnrhs.indexOf(fnrh);
			Fnrh f = fnrhs.get(indice);
	
			fnrh = new Fnrh();
			fnrh = f;
			listaEstados();
			listaCidades();
		}

	}

	/* Listas */
	private void carregaListasNovo() {
		listaPaises();
		listaEstados();
		listaCidades();
		listaMotivosViagem();
		listaMeiosTransporte();
		listaGeneros();
		listaTiposDocumento();
	}

	private void listaMotivosViagem() {
		motivosViagem = motivoViagemRepo.getPorTipoLingua(localeSelector
				.getLanguage());
	}

	private void listaMeiosTransporte() {
		meiosTransporte = meioTransporteRepo.getPorTipoLingua(localeSelector
				.getLanguage());
	}

	private void listaTiposDocumento() {
		tiposDocumento = tipoDocumentoRepo.getPorTipoLingua(localeSelector
				.getLanguage());
	}

	private void listaGeneros() {
		generos = generoRepo.getAllPorLingua(localeSelector.getLanguage());
	}

	private void listaPaises() {
		paisesNacionalidade = paisRepo.getAllOrderNome();
		paisesResidencia = paisRepo.getAllOrderNome();
		paisesOrigem = paisRepo.getAllOrderNome();
		paisesDestino = paisRepo.getAllOrderNome();
	}

	private void listaEstados() {
		setaEstadosResidencia();
		setaEstadosOrigem();
		setaEstadosDestino();
	}

	private void listaCidades() {
		setaCidadesResidencia();
		setaCidadesOrigem();
		setaCidadesDestino();
	}

	

	@Begin(join=true,flushMode = FlushModeType.MANUAL)
	public void setaDadosCep() {
		Cep cep =null;
		if (StringHelper.isNotEmpty(fnrh.getCepResidencia())
				&& CepUtil.validaCep(fnrh.getCepResidencia())) {
			cep = cepRepo.getPorId(CepUtil.retiraCaracteresCep(fnrh
					.getCepResidencia()));
		}	
		
		setaDadosCepResidencia(cep);
		setaDadosCepOrigem(cep);
		setaDadosCepDestino(cep);		
	}

	private void setaDadosCepResidencia(Cep cep) {
		if(cep!=null){
			fnrh.setPaisResidencia(Fnrh.PAIS_PADRAO);
			setaEstadosResidencia();
			fnrh.setEstadoResidencia(cep.getEstadoSigla());
			setaCidadesResidencia();
			fnrh.setCidadeCodResidencia(cep.getCodigoIBGE());
			fnrh.setCidadeDescricaoResidencia(cep.getCidadeNome());
			fnrh.setLogradouroResidencia(cep.getLogradouro());
			fnrh.setComplementoResidencia(cep.getComplemento());
		}else{
			fnrh.setPaisResidencia(null);
			setaEstadosResidencia();
			fnrh.setEstadoResidencia(null);
			fnrh.setEstadoDescricaoResidencia(null);
			setaCidadesResidencia();
			fnrh.setCidadeCodResidencia(null);
			fnrh.setCidadeDescricaoResidencia(null);
			fnrh.setLogradouroResidencia(null);
			fnrh.setComplementoResidencia(null);
		}		

	}

	private void setaDadosCepOrigem(Cep cep) {
		if(cep!=null){
			fnrh.setPaisOrigem(Fnrh.PAIS_PADRAO);
			setaEstadosOrigem();
			fnrh.setEstadoOrigem(cep.getEstadoSigla());
			setaCidadesOrigem();
			fnrh.setCidadeCodOrigem(cep.getCodigoIBGE());
			fnrh.setCidadeDescricaoOrigem(cep.getCidadeNome());
		}else{
			fnrh.setPaisOrigem(null);
			setaEstadosOrigem();
			fnrh.setEstadoOrigem(null);
			fnrh.setEstadoDescricaoOrigem(null);
			setaCidadesOrigem();
			fnrh.setCidadeCodOrigem(null);
			fnrh.setCidadeDescricaoOrigem(null);
		}		
	}

	private void setaDadosCepDestino(Cep cep) {
		if(cep!=null){
			fnrh.setPaisDestino(Fnrh.PAIS_PADRAO);
			setaEstadosDestino();
			fnrh.setEstadoDestino(cep.getEstadoSigla());
			setaCidadesDestino();
			fnrh.setCidadeCodDestino(cep.getCodigoIBGE());
			fnrh.setCidadeDescricaoDestino(cep.getCidadeNome());
		}else{
			fnrh.setPaisDestino(null);
			setaEstadosDestino();
			fnrh.setEstadoDestino(null);
			fnrh.setEstadoDescricaoDestino(null);
			setaCidadesDestino();
			fnrh.setCidadeCodDestino(null);
			fnrh.setCidadeDescricaoDestino(null);
		}
		
	}
	
	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaEstadosResidencia() {
		estadosResidencia = new ArrayList<Estado>();			
		if (StringHelper.isNotEmpty(fnrh.getPaisResidencia())) {
			if (fnrh.getPaisResidencia().equals(Fnrh.PAIS_PADRAO)) {
				estadosResidencia = estadoRepo.getAllOrderSigla();
			} 			
		}
		
		setaCidadesResidencia();
	}

	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaEstadosOrigem() {
		estadosOrigem = new ArrayList<Estado>();
		if (StringHelper.isNotEmpty(fnrh.getPaisOrigem())) {
			if (fnrh.getPaisOrigem().equals(Fnrh.PAIS_PADRAO)) {
				estadosOrigem = estadoRepo.getAllOrderSigla();
			}
		}
		
		setaCidadesOrigem();
	}

	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaEstadosDestino() {
		estadosDestino = new ArrayList<Estado>();
		if (StringHelper.isNotEmpty(fnrh.getPaisDestino())) {
			if (fnrh.getPaisDestino().equals(Fnrh.PAIS_PADRAO)) {
				estadosDestino = estadoRepo.getAllOrderSigla();
			} 
		}
		
		setaCidadesDestino();
	}

	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaCidadesResidencia() {
		cidadesResidencia = new ArrayList<Cidade>();
		if (StringHelper.isNotEmpty(fnrh.getPaisResidencia())
				&& StringHelper.isNotEmpty(fnrh.getEstadoResidencia())) {
			if (fnrh.getPaisResidencia().equals(Fnrh.PAIS_PADRAO)) {
				cidadesResidencia = cidadeRepo.getPorSiglaEstado(fnrh
						.getEstadoResidencia());
			}			
		}
	}

	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaCidadesOrigem() {
		cidadesOrigem = new ArrayList<Cidade>();
		if (StringHelper.isNotEmpty(fnrh.getPaisOrigem())
				&& StringHelper.isNotEmpty(fnrh.getEstadoOrigem())) {
			if (fnrh.getPaisOrigem().equals(Fnrh.PAIS_PADRAO)) {
				cidadesOrigem = cidadeRepo.getPorSiglaEstado(fnrh
						.getEstadoOrigem());
			}
		}
	}

	@Begin(join=true,flushMode= FlushModeType.MANUAL)
	public void setaCidadesDestino() {
		cidadesDestino = new ArrayList<Cidade>();
		if (StringHelper.isNotEmpty(fnrh.getPaisDestino())
				&& StringHelper.isNotEmpty(fnrh.getEstadoDestino())) {
			if (fnrh.getPaisDestino().equals(Fnrh.PAIS_PADRAO)) {
				cidadesDestino = cidadeRepo.getPorSiglaEstado(fnrh
						.getEstadoDestino());
			} 
		}
	}
	
	private void atualizaPreenchimentoFnrhs() {
		haFnrhsNaoPreenchidas = false;
		for (Fnrh f : fnrhs) {
			if (!f.isPreenchimentoCompleto()) {
				haFnrhsNaoPreenchidas = true;
			}
		}
	}

	private void carregaProximaFnrh() {
		if (haFnrhsNaoPreenchidas) {
			for (Fnrh f : fnrhs) {
				if (!f.isPreenchimentoCompleto()) {
					fnrh = f;
					listaEstados();
					listaCidades();
					return;
				}
			}
		}
	}
	
	private void retiraCaracteresEspeciais() {
		fnrh.setCelularDDD(StringUtil.reticaCaracteresEspeciais(
				StringUtil.reticaCaracteresEspeciais(fnrh.getCelularDDD(), "("),
				")"));
		fnrh.setCelularDDI(StringUtil.reticaCaracteresEspeciais(
				StringUtil.reticaCaracteresEspeciais(fnrh.getCelularDDI(), "("),
				")"));
		fnrh.setCelularNumero(StringUtil.reticaCaracteresEspeciais(StringUtil
				.reticaCaracteresEspeciais(fnrh.getCelularNumero(), "("), ")"));
		fnrh.setTelefoneNumero(StringUtil.reticaCaracteresEspeciais(StringUtil
				.reticaCaracteresEspeciais(fnrh.getTelefoneNumero(), "("), ")"));
		fnrh.setTelefoneDDD(StringUtil.reticaCaracteresEspeciais(StringUtil
				.reticaCaracteresEspeciais(fnrh.getTelefoneDDD(), "("), ")"));
		fnrh.setTelefoneDDI(StringUtil.reticaCaracteresEspeciais(StringUtil
				.reticaCaracteresEspeciais(fnrh.getTelefoneDDI(), "("), ")"));
		fnrh.setDocumentoNumero(StringUtil.reticaCaracteresEspeciais(
				fnrh.getDocumentoNumero(), "."));
		fnrh.setCepResidencia(StringUtil.reticaCaracteresEspeciais(
				fnrh.getCepResidencia(), "-"));
	}

	/* Validações */

	/**
	 * Validação feita de acordo com especificação contida no PGTUR Manual
	 * Técnico de Utilização do Web Service 2.4 Para maiores informações
	 * consultar documentação do projeto.
	 */
	public boolean validaSave() {
		if (validaDadosPessoaisHospede()) {
			int idade = 0;
			
			if (fnrh.getDataNascimento() != null) {
				idade = DateUtil.calculaIdade(fnrh.getDataNascimento());
			}

			if (idade > Fnrh.IDADE_OBRIGATORIEDADE_FNRH) {
				if(StringHelper.isEmpty(fnrh.getEmail())){
					facesMessages.addFromResourceBundle(Severity.ERROR,
							"#{messages['email.obrigatorio']}");
					return false;
				}
				
				if(!EmailUtil.emailValido(fnrh.getEmail())){
					facesMessages.addFromResourceBundle(Severity.ERROR,
							"#{messages['emailInvalido']}");
					return false;
				}
				return (validaDadosPessoaisHospede() && validaEnderecos()
						&& validaDetalhesViagem() && validaDocumento());
			}
		} else {
			return false;
		}

		return true;

	}

	/**
	 * Valida motivo e meio de viagem
	 * 
	 * @see MotivoViagem
	 * @see MeioTransporte
	 */
	public boolean validaDetalhesViagem() {
		if (fnrh.getMotivoViagem() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['motivo.viagem.obrigatorio']}");
			return false;
		}

		if (fnrh.getMeioTransporte() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['meio.transporte.obrigatorio']}");
			return false;
		}

		return true;
	}

	/**
	 * Valida informações como Nome, Sobrenome, Data de Nascimento e Gênero do
	 * hóspede
	 */
	public boolean validaDadosPessoaisHospede() {

		if (StringHelper.isEmpty(fnrh.getNome())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['primeiro.nome.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getSobrenome())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['ultimo.nome.obrigatorio']}");
			return false;
		}

		if (fnrh.getDataNascimento() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['data.nascimento.obrigatorio']}");
			return false;
		}

		if (fnrh.getGenero() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['genero.obrigatorio']}");
			return false;
		}

		return true;
	}

	/**
	 * Valida pais de nacionalidade e pais,estado e cidade de residencia, origem
	 * e destino
	 */
	public boolean validaEnderecos() {

		// Nacionalidade
		if (StringHelper.isEmpty(fnrh.getPaisNacionalidade())) {

			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['pais.nacionalidade.obrigatorio']}");
			return false;
		}

		// Residência
		if (StringHelper.isEmpty(fnrh.getPaisResidencia())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['pais.residencia.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getEstadoResidencia()) && StringHelper.isEmpty(fnrh.getEstadoDescricaoResidencia())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['uf.residencia.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getCidadeDescricaoResidencia())
				&& fnrh.getCidadeCodResidencia() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['cidade.residencia.obrigatorio']}");
			return false;
		}

		// Origem
		if (StringHelper.isEmpty(fnrh.getPaisOrigem())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['pais.origem.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getEstadoOrigem()) && StringHelper.isEmpty(fnrh.getEstadoDescricaoOrigem())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['uf.origem.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getCidadeDescricaoOrigem())
				&& fnrh.getCidadeCodOrigem() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['cidade.origem.obrigatorio']}");
			return false;
		}

		// Destino
		if (StringHelper.isEmpty(fnrh.getPaisDestino())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['pais.proximo.destino.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getEstadoDestino()) && StringHelper.isEmpty(fnrh.getEstadoDescricaoDestino())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['uf.proximo.destino.obrigatorio']}");
			return false;
		}

		if (StringHelper.isEmpty(fnrh.getCidadeDescricaoDestino())
				&& fnrh.getCidadeCodDestino() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['cidade.proximo.destino.obrigatorio']}");
			return false;
		}

		return true;

	}

	/**
	 * Validação feita de acordo com especificação contida no PGTUR Manual
	 * Técnico de Utilização do Web Service 2.4 Item 6.1.4 Para maiores
	 * informações consultar documentação do projeto
	 */
	public boolean validaDocumento() {
		String paisNacionalidade = fnrh.getPaisNacionalidade();
		int idade = 0;
		if (fnrh.getDataNascimento() != null) {
			idade = DateUtil.calculaIdade(fnrh.getDataNascimento());
		}

		// Verifica se o tipo de documento foi informado
		if (fnrh.getTipoDocumento() == null) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['tipo.documento.obrigatorio']}");
			return false;
		}

		if (paisNacionalidade.equals(Fnrh.PAIS_PADRAO)) {
			if (idade < Fnrh.IDADE_MAIORIDADE) {
				// Se o campo “snnacionalidade” contiver o valor “BRASIL” e a
				// Data de Nascimento for menor que “18” anos deverá preencher
				// um dos seguintes documentos:
				// “CPF”, “RG” ou “CN” (Certidão de Nascimento).
				if ((!fnrh
						.getTipoDocumento()
						.getCodigo()
						.equals(TipoDocumentoEnum.CEDULA_IDENTIDADE.getCodigo()))
						&& (!fnrh
								.getTipoDocumento()
								.getCodigo()
								.equals(TipoDocumentoEnum.CERTIDAO_NASCIMENTO
										.getCodigo()))
						&& (!fnrh.getTipoDocumento().getCodigo()
								.equals(TipoDocumentoEnum.CPF.getCodigo()))) {
					facesMessages
							.addFromResourceBundle(Severity.ERROR,
									"#{messages['documentos.brasil.minoridade.obrigatorio']}");
					return false;
				}
			} else {
				// Se o campo “snnacionalidade” contiver o valor “BRASIL” e a
				// Data de Nascimento maior ou igual a “18” anos
				// obrigatoriamente deverá ser preenchido:
				// “CPF”.
				if (!fnrh.getTipoDocumento().getCodigo()
						.equals(TipoDocumentoEnum.CPF.getCodigo())) {
					facesMessages
							.addFromResourceBundle(Severity.ERROR,
									"#{messages['documentos.brasil.maioridade.obrigatorio']}");
					return false;
				}
			}

		} else if (paisRepo.isPaisMercosul(paisNacionalidade)) {
			// Se o campo “snnacionalidade” não contiver “BRASIL” selecionado e
			// o país
			// selecionado for pertencente ao Bloco Econômico “MERCOSUL” (item
			// 6.1.1),
			// então deverá preencher um dos seguintes documentos:
			// “PASSAPORTE” ou “CIE”;
			if ((!fnrh.getTipoDocumento().getCodigo()
					.equals(TipoDocumentoEnum.PASSAPORTE.getCodigo()))
					&& (!fnrh
							.getTipoDocumento()
							.getCodigo()
							.equals(TipoDocumentoEnum.IDENTIDADE_ESTRANGEIRA
									.getCodigo()))) {
				facesMessages.addFromResourceBundle(Severity.ERROR,
						"#{messages['documentos.mercosul.obrigatorio']}");
				return false;
			}
		} else {

			// Se o campo “snnacionalidade” não contiver “BRASIL” selecionado e
			// o país não for
			// pertencente ao Bloco Econômico “MERCOSUL” (item 6.1.1), então
			// obrigatoriamente deverá ser preenchido:
			// “PASSAPORTE”.
			if ((!fnrh.getTipoDocumento().getCodigo()
					.equals(TipoDocumentoEnum.PASSAPORTE.getCodigo()))
					&& (!fnrh
							.getTipoDocumento()
							.getCodigo()
							.equals(TipoDocumentoEnum.IDENTIDADE_ESTRANGEIRA
									.getCodigo()))) {
				facesMessages.addFromResourceBundle(Severity.ERROR,
						"#{messages['documentos.mercosul.obrigatorio']}");
				return false;
			}
		}

		// Verifica se o número do documento foi informado
		if (StringHelper.isEmpty(fnrh.getDocumentoNumero())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['numero.documento.obrigatorio']}");
			return false;
			// Caso o tipo de documento seja CPF verifica se este é válido
		} else if (fnrh.getTipoDocumento().getCodigo()
				.equals(TipoDocumentoEnum.CPF.getCodigo())
				&& !(new CpfValidator().isValid(fnrh.getDocumentoNumero()))) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages.cpfInvalido}");
			return false;
		}

		// Verifica se o tipo de documento informado exige o preenchimento do
		// órgão e se este campo foi preenchido
		if (fnrh.getTipoDocumento().getObrigaOrgao()
				.equals(TipoDocumento.OBRIGA_ORGAO)
				&& StringHelper.isEmpty(fnrh.getDocumentoOrgao())) {
			facesMessages.addFromResourceBundle(Severity.ERROR,
					"#{messages['orgao.documento.obrigatorio']}");
			return false;
		}

		return true;

	}

}
