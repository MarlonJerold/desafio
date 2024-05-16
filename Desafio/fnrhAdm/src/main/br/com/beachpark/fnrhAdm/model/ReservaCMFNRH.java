package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Esta classe representará a view (BPRECEPTOR.RESERVA_FNRH)
 * 
 * @author LesteTI
 * 
 */

public class ReservaCMFNRH implements Serializable {

	public static final Long STATUS_CONFIRMADA = 1l;
	public static final Long STATUS_CHECKIN = 2l;
	public static final Long STATUS_CHECKOUT = 3l;
	public static final String HOSPEDE_PRINCIPAL = "S";

	private static final long serialVersionUID = 3519500151653696276L;

	private Long reservaId;
	private Long reservaNum;
	private Long reservaStatusId;
	private String principal;
	private String designacao;
	private String hospedeNome;
	private String hospedeSobrenome;
	private String pessoaRazaoSocial;
	private String pessoaNome;
	private Date dataNascimento;
	private String email;
	private String profissao;
	private Long paisNacionalidadeId;
	private String paisNacionalidadeCod;
	private String genero;
	private Long enderecoResidenciaId;
	private String enderecoResidenciaLogradouro;
	private String enderecoResidenciaBairro;
	private String enderecoResidenciaNumero;
	private String enderecoResidenciaComplemento;
	private String enderecoResidenciaCEP;
	private Long paisResidenciaId;
	private String paisResidenciaCod;
	private String estadoResidenciaCod;
	private Long cidadeResidenciaId;
	private String cidadeResidenciaCodIGGE;
	private Long historicoEstadaId;
	private String paisOrigemCod;
	private String estadoOrigemCod;
	private String cidadeOrigemCodIGGE;
	private String paisDestinoCod;
	private String estadoDestinoCod;
	private String cidadeDestinoCodIGGE;
	private MotivoViagemEnum motivoViagem;
	private MeioTransporteEnum meioTransporte;
	private Long hospedeId;
	private Long contaId;
	private Long hotelId;
	private Date dataPrevistaCheckIn;
	private Date dataPrevistaCheckOut;
	private Long localizador;
	private String origem;
	private String telefoneDDI;
	private String telefoneDDD;
	private String telefoneNum;
	private String celularDDI;
	private String celularDDD;
	private String celularNum;
	private String origemCod;

	/* Getters and Setters */
	public Long getReservaId() {
		return reservaId;
	}

	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}

	public Long getReservaStatusId() {
		return reservaStatusId;
	}

	public void setReservaStatusId(Long reservaStatusId) {
		this.reservaStatusId = reservaStatusId;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getDesignacao() {
		return designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public String getHospedeNome() {
		return hospedeNome;
	}

	public void setHospedeNome(String hospedeNome) {
		this.hospedeNome = hospedeNome;
	}

	public String getHospedeSobrenome() {
		return hospedeSobrenome;
	}

	public void setHospedeSobrenome(String hospedeSobrenome) {
		this.hospedeSobrenome = hospedeSobrenome;
	}

	public String getPessoaRazaoSocial() {
		return pessoaRazaoSocial;
	}

	public void setPessoaRazaoSocial(String pessoaRazaoSocial) {
		this.pessoaRazaoSocial = pessoaRazaoSocial;
	}

	public String getPessoaNome() {
		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome) {
		this.pessoaNome = pessoaNome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public Long getPaisNacionalidadeId() {
		return paisNacionalidadeId;
	}

	public void setPaisNacionalidadeId(Long paisNacionalidadeId) {
		this.paisNacionalidadeId = paisNacionalidadeId;
	}

	public String getPaisNacionalidadeCod() {
		return paisNacionalidadeCod;
	}

	public void setPaisNacionalidadeCod(String paisNacionalidadeCod) {
		this.paisNacionalidadeCod = paisNacionalidadeCod;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Long getEnderecoResidenciaId() {
		return enderecoResidenciaId;
	}

	public void setEnderecoResidenciaId(Long enderecoResidenciaId) {
		this.enderecoResidenciaId = enderecoResidenciaId;
	}

	public String getEnderecoResidenciaLogradouro() {
		return enderecoResidenciaLogradouro;
	}

	public void setEnderecoResidenciaLogradouro(
			String enderecoResidenciaLogradouro) {
		this.enderecoResidenciaLogradouro = enderecoResidenciaLogradouro;
	}

	public String getEnderecoResidenciaNumero() {
		return enderecoResidenciaNumero;
	}

	public void setEnderecoResidenciaNumero(String enderecoResidenciaNumero) {
		this.enderecoResidenciaNumero = enderecoResidenciaNumero;
	}

	public String getEnderecoResidenciaComplemento() {
		return enderecoResidenciaComplemento;
	}

	public void setEnderecoResidenciaComplemento(
			String enderecoResidenciaComplemento) {
		this.enderecoResidenciaComplemento = enderecoResidenciaComplemento;
	}

	public String getEnderecoResidenciaCEP() {
		return enderecoResidenciaCEP;
	}

	public void setEnderecoResidenciaCEP(String enderecoResidenciaCEP) {
		this.enderecoResidenciaCEP = enderecoResidenciaCEP;
	}

	public Long getPaisResidenciaId() {
		return paisResidenciaId;
	}

	public void setPaisResidenciaId(Long paisResidenciaId) {
		this.paisResidenciaId = paisResidenciaId;
	}

	public String getPaisResidenciaCod() {
		return paisResidenciaCod;
	}

	public void setPaisResidenciaCod(String paisResidenciaCod) {
		this.paisResidenciaCod = paisResidenciaCod;
	}

	public String getEstadoResidenciaCod() {
		return estadoResidenciaCod;
	}

	public void setEstadoResidenciaCod(String estadoResidenciaCod) {
		this.estadoResidenciaCod = estadoResidenciaCod;
	}

	public Long getCidadeResidenciaId() {
		return cidadeResidenciaId;
	}

	public void setCidadeResidenciaId(Long cidadeResidenciaId) {
		this.cidadeResidenciaId = cidadeResidenciaId;
	}

	public String getCidadeResidenciaCodIGGE() {
		return cidadeResidenciaCodIGGE;
	}

	public void setCidadeResidenciaCodIGGE(String cidadeResidenciaCodIGGE) {
		this.cidadeResidenciaCodIGGE = cidadeResidenciaCodIGGE;
	}

	public Long getHistoricoEstadaId() {
		return historicoEstadaId;
	}

	public void setHistoricoEstadaId(Long historicoEstadaId) {
		this.historicoEstadaId = historicoEstadaId;
	}

	public String getPaisOrigemCod() {
		return paisOrigemCod;
	}

	public void setPaisOrigemCod(String paisOrigemCod) {
		this.paisOrigemCod = paisOrigemCod;
	}

	public String getEstadoOrigemCod() {
		return estadoOrigemCod;
	}

	public void setEstadoOrigemCod(String estadoOrigemCod) {
		this.estadoOrigemCod = estadoOrigemCod;
	}

	public String getCidadeOrigemCodIGGE() {
		return cidadeOrigemCodIGGE;
	}

	public void setCidadeOrigemCodIGGE(String cidadeOrigemCodIGGE) {
		this.cidadeOrigemCodIGGE = cidadeOrigemCodIGGE;
	}

	public String getPaisDestinoCod() {
		return paisDestinoCod;
	}

	public void setPaisDestinoCod(String paisDestinoCod) {
		this.paisDestinoCod = paisDestinoCod;
	}

	public String getEstadoDestinoCod() {
		return estadoDestinoCod;
	}

	public void setEstadoDestinoCod(String estadoDestinoCod) {
		this.estadoDestinoCod = estadoDestinoCod;
	}

	public String getCidadeDestinoCodIGGE() {
		return cidadeDestinoCodIGGE;
	}

	public void setCidadeDestinoCodIGGE(String cidadeDestinoCodIGGE) {
		this.cidadeDestinoCodIGGE = cidadeDestinoCodIGGE;
	}

	public MotivoViagemEnum getMotivoViagem() {
		return motivoViagem;
	}

	public void setMotivoViagem(MotivoViagemEnum motivoViagem) {
		this.motivoViagem = motivoViagem;
	}

	public MeioTransporteEnum getMeioTransporte() {
		return meioTransporte;
	}

	public void setMeioTransporte(MeioTransporteEnum meioTransporte) {
		this.meioTransporte = meioTransporte;
	}

	public Long getHospedeId() {
		return hospedeId;
	}

	public void setHospedeId(Long hospedeId) {
		this.hospedeId = hospedeId;
	}

	public Long getContaId() {
		return contaId;
	}

	public void setContaId(Long contaId) {
		this.contaId = contaId;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Date getDataPrevistaCheckIn() {
		return dataPrevistaCheckIn;
	}

	public void setDataPrevistaCheckIn(Date dataPrevistaCheckIn) {
		this.dataPrevistaCheckIn = dataPrevistaCheckIn;
	}

	public Date getDataPrevistaCheckOut() {
		return dataPrevistaCheckOut;
	}

	public void setDataPrevistaCheckOut(Date dataPrevistaCheckOut) {
		this.dataPrevistaCheckOut = dataPrevistaCheckOut;
	}

	public Long getLocalizador() {
		return localizador;
	}

	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}

	public Long getReservaNum() {
		return reservaNum;
	}

	public void setReservaNum(Long reservaNum) {
		this.reservaNum = reservaNum;
	}

	public String getEnderecoResidenciaBairro() {
		return enderecoResidenciaBairro;
	}

	public void setEnderecoResidenciaBairro(String enderecoResidenciaBairro) {
		this.enderecoResidenciaBairro = enderecoResidenciaBairro;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getTelefoneDDI() {
		return telefoneDDI;
	}

	public void setTelefoneDDI(String telefoneDDI) {
		this.telefoneDDI = telefoneDDI;
	}

	public String getTelefoneDDD() {
		return telefoneDDD;
	}

	public void setTelefoneDDD(String telefoneDDD) {
		this.telefoneDDD = telefoneDDD;
	}

	public String getTelefoneNum() {
		return telefoneNum;
	}

	public void setTelefoneNum(String telefoneNum) {
		this.telefoneNum = telefoneNum;
	}

	public String getCelularDDI() {
		return celularDDI;
	}

	public void setCelularDDI(String celularDDI) {
		this.celularDDI = celularDDI;
	}

	public String getCelularDDD() {
		return celularDDD;
	}

	public void setCelularDDD(String celularDDD) {
		this.celularDDD = celularDDD;
	}

	public String getCelularNum() {
		return celularNum;
	}

	public void setCelularNum(String celularNum) {
		this.celularNum = celularNum;
	}

	public String getOrigemCod() {
		return origemCod;
	}

	public void setOrigemCod(String origemCod) {
		this.origemCod = origemCod;
	}

}
