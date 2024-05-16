package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.util.StringHelper;
import org.hibernate.validator.NotNull;

import br.com.padrao.annotation.NotWhiteSpace;
import br.com.padrao.util.StringUtil;

/**
 * Entity para Fnrh (Ficha Nacional de Registro de Hóspede) (FNRH.FNRH)
 * 
 * @author LesteTI
 * 
 */

@Entity
// @Table(name = "fnrh.FNRH")
@Table(name = "fnrh", schema="fnrh")
public class Fnrh implements Serializable {

	private static final long serialVersionUID = -6369943041744014400L;

	private Long id;
	private Reserva reserva;
	private String principal;
	private String nome;
	private String sobrenome;
	private Date dataNascimento;
	private String email;
	private String telefoneDDI;
	private String telefoneDDD;
	private String telefoneNumero;
	private String celularDDI;
	private String celularDDD;
	private String celularNumero;
	private String profissao;
	private String paisNacionalidade;
	private Genero genero;

	private TipoDocumento tipoDocumento;
	private String documentoNumero;
	private String documentoOrgao;

	private String logradouroResidencia;
	private String numeroResidencia;
	private String complementoResidencia;

	private String paisResidencia;
	private String estadoResidencia;
	private String estadoDescricaoResidencia;
	private String cidadeCodResidencia;
	private String cidadeDescricaoResidencia;
	private String bairroResidencia;
	private String cepResidencia;

	private String paisOrigem;
	private String estadoOrigem;
	private String estadoDescricaoOrigem;
	private String cidadeCodOrigem;
	private String cidadeDescricaoOrigem;

	private String paisDestino;
	private String estadoDestino;
	private String estadoDescricaoDestino;
	private String cidadeCodDestino;
	private String cidadeDescricaoDestino;

	private MotivoViagem motivoViagem;
	private MeioTransporte meioTransporte;
	private String observacao;

	private java.util.Date dataInsercao;
	private java.util.Date dataUpdate;
	private Long hospedeId;
	private Long contaHotelId;
	private boolean preenchimentoCompleto;
	
	private List<FnrhDocumento> fnrhDocumentoList;

	public static final String LINGUA_PADRAO = "pt";
	public static final String PAIS_PADRAO = "BR";
	public static final int IDADE_MAIORIDADE = 18;
	public static final int IDADE_OBRIGATORIEDADE_FNRH = 16;
	public static final String PRINCIPAL = "S";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "RESERVA_ID")
	@NotNull
	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Column(name = "PRINCIPAL")
	@NotWhiteSpace
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "NOME")
	@NotWhiteSpace
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "SOBRENOME")
	@NotWhiteSpace
	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "NASCIMENTO")
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "DDI_TELEFONE")
	public String getTelefoneDDI() {
		return telefoneDDI;
	}

	public void setTelefoneDDI(String telefoneDDI) {
		this.telefoneDDI = telefoneDDI;
	}

	@Column(name = "DDD_TELEFONE")
	public String getTelefoneDDD() {
		return telefoneDDD;
	}

	public void setTelefoneDDD(String telefoneDDD) {
		this.telefoneDDD = telefoneDDD;
	}

	@Column(name = "NR_TELEFONE")
	public String getTelefoneNumero() {
		return telefoneNumero;
	}

	public void setTelefoneNumero(String telefoneNumero) {
		this.telefoneNumero = telefoneNumero;
	}

	@Column(name = "DDI_CELULAR")
	public String getCelularDDI() {
		return celularDDI;
	}

	public void setCelularDDI(String celularDDI) {
		this.celularDDI = celularDDI;
	}

	@Column(name = "DDD_CELULAR")
	public String getCelularDDD() {
		return celularDDD;
	}

	public void setCelularDDD(String celularDDD) {
		this.celularDDD = celularDDD;
	}

	@Column(name = "NR_CELULAR")
	public String getCelularNumero() {
		return celularNumero;
	}

	public void setCelularNumero(String celularNumero) {
		this.celularNumero = celularNumero;
	}

	@Column(name = "PROFISSAO")
	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "CD_PAIS_NACIONALIDADE")
	public String getPaisNacionalidade() {
		return paisNacionalidade;
	}

	public void setPaisNacionalidade(String paisNacionalidade) {
		this.paisNacionalidade = paisNacionalidade;
	}

	@Column(name = "DT_INSERT")
	@NotNull
	public java.util.Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(java.util.Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	@Column(name = "DT_UPDATE")
	@NotNull
	public java.util.Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(java.util.Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	@Column(name = "ID_HOSPEDE")
	@NotNull
	public Long getHospedeId() {
		return hospedeId;
	}

	public void setHospedeId(Long hospedeId) {
		this.hospedeId = hospedeId;
	}

	@Column(name = "ID_CONTAHOTEL")
	@NotNull
	public Long getContaHotelId() {
		return contaHotelId;
	}

	public void setContaHotelId(Long contaHotelId) {
		this.contaHotelId = contaHotelId;
	}

	// Obrigatorio no preenchimento da fnrh
	@ManyToOne
	@JoinColumn(name = "GENERO_ID")
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	// Obrigatorio no preenchimento da fnrh
	@ManyToOne
	@JoinColumn(name = "TIPODOCUMENTO_ID")
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "NUMERO_DOCUMENTO")
	public String getDocumentoNumero() {
		return documentoNumero;
	}

	public void setDocumentoNumero(String documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	@Column(name = "ORGAO_DOCUMENTO")
	public String getDocumentoOrgao() {
		return documentoOrgao;
	}

	public void setDocumentoOrgao(String documentoOrgao) {
		this.documentoOrgao = documentoOrgao;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "CD_PAIS_RES")
	public String getPaisResidencia() {
		return paisResidencia;
	}

	public void setPaisResidencia(String paisResidencia) {
		this.paisResidencia = paisResidencia;
	}

	@Column(name = "CD_UF_RES")
	public String getEstadoResidencia() {
		return estadoResidencia;
	}

	public void setEstadoResidencia(String estadoResidencia) {
		this.estadoResidencia = estadoResidencia;
	}

	@Column(name = "NM_CIDADE_RES")
	public String getCidadeDescricaoResidencia() {
		return cidadeDescricaoResidencia;
	}

	public void setCidadeDescricaoResidencia(String cidadeDescricaoResidencia) {
		this.cidadeDescricaoResidencia = cidadeDescricaoResidencia;
	}

	@Column(name = "CEP_RES")
	public String getCepResidencia() {
		return cepResidencia;
	}

	public void setCepResidencia(String cepResidencia) {
		if(StringHelper.isNotEmpty(cepResidencia)){
			cepResidencia= StringUtil.reticaCaracteresEspeciais(
					cepResidencia, "-");
		}
		this.cepResidencia = cepResidencia;
	}

	// Obrigatorio no preenchimento da fnrh
	@ManyToOne
	@JoinColumn(name = "MOTIVO_ID")
	public MotivoViagem getMotivoViagem() {
		return motivoViagem;
	}

	public void setMotivoViagem(MotivoViagem motivoViagem) {
		this.motivoViagem = motivoViagem;
	}

	// Obrigatorio no preenchimento da fnrh
	@ManyToOne
	@JoinColumn(name = "MEIO_ID")
	public MeioTransporte getMeioTransporte() {
		return meioTransporte;
	}

	public void setMeioTransporte(MeioTransporte meioTransporte) {
		this.meioTransporte = meioTransporte;
	}

	@Column(name = "OBSERVACAO")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "CD_PAIS_DE")
	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	@Column(name = "CD_UF_DE")
	public String getEstadoOrigem() {
		return estadoOrigem;
	}

	public void setEstadoOrigem(String estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}

	@Column(name = "NM_CIDADE_DE")
	public String getCidadeDescricaoOrigem() {
		return cidadeDescricaoOrigem;
	}

	public void setCidadeDescricaoOrigem(String cidadeDescricaoOrigem) {
		this.cidadeDescricaoOrigem = cidadeDescricaoOrigem;
	}

	// Obrigatorio no preenchimento da fnrh
	@Column(name = "CD_PAIS_PARA")
	public String getPaisDestino() {
		return paisDestino;
	}

	public void setPaisDestino(String paisDestino) {
		this.paisDestino = paisDestino;
	}

	@Column(name = "CD_UF_PARA")
	public String getEstadoDestino() {
		return estadoDestino;
	}

	public void setEstadoDestino(String estadoDestino) {
		this.estadoDestino = estadoDestino;
	}

	@Column(name = "NM_CIDADE_PARA")
	public String getCidadeDescricaoDestino() {
		return cidadeDescricaoDestino;
	}

	public void setCidadeDescricaoDestino(String cidadeDescricaoDestino) {
		this.cidadeDescricaoDestino = cidadeDescricaoDestino;
	}

	@Column(name = "LOGRADOURO_RES")
	public String getLogradouroResidencia() {
		return logradouroResidencia;
	}

	public void setLogradouroResidencia(String logradouroResidencia) {
		this.logradouroResidencia = logradouroResidencia;
	}

	@Column(name = "NUMERO_RES")
	public String getNumeroResidencia() {
		return numeroResidencia;
	}

	public void setNumeroResidencia(String numeroResidencia) {
		this.numeroResidencia = numeroResidencia;
	}

	@Column(name = "COMPLEMENTO_RES")
	public String getComplementoResidencia() {
		return complementoResidencia;
	}

	public void setComplementoResidencia(String complementoResidencia) {
		this.complementoResidencia = complementoResidencia;
	}
	
	@Column(name = "BAIRRO_RES")
	public String getBairroResidencia() {
		return bairroResidencia;
	}
	public void setBairroResidencia(String bairroResidencia) {
		this.bairroResidencia = bairroResidencia;
	}

	@Column(name = "PREENCHIMENTO_COMPLETO")
	public boolean isPreenchimentoCompleto() {
		return preenchimentoCompleto;
	}

	public void setPreenchimentoCompleto(boolean preenchimentoCompleto) {
		this.preenchimentoCompleto = preenchimentoCompleto;
	}

	@Column(name = "CD_CIDADE_RES")
	public String getCidadeCodResidencia() {
		return cidadeCodResidencia;
	}

	public void setCidadeCodResidencia(String cidadeCodResidencia) {
		this.cidadeCodResidencia = cidadeCodResidencia;
	}

	// @Column(name = "NM_UF_RES",length=100)
	@Column(name = "NM_UF_RES")
	public String getEstadoDescricaoResidencia() {
		return estadoDescricaoResidencia;
	}

	public void setEstadoDescricaoResidencia(String estadoDescricaoResidencia) {
		this.estadoDescricaoResidencia = estadoDescricaoResidencia;
	}

	// @Column(name = "CD_CIDADE_DE",length=7)
	@Column(name = "CD_CIDADE_DE")
	public String getCidadeCodOrigem() {
		return cidadeCodOrigem;
	}

	public void setCidadeCodOrigem(String cidadeCodOrigem) {
		this.cidadeCodOrigem = cidadeCodOrigem;
	}

	// @Column(name = "CD_CIDADE_PARA",length=7)
	@Column(name = "CD_CIDADE_PARA")
	public String getCidadeCodDestino() {
		return cidadeCodDestino;
	}

	public void setCidadeCodDestino(String cidadeCodDestino) {
		this.cidadeCodDestino = cidadeCodDestino;
	}

	// @Column(name = "NM_UF_DE",length=100)
	@Column(name = "NM_UF_DE")
	public String getEstadoDescricaoOrigem() {
		return estadoDescricaoOrigem;
	}

	public void setEstadoDescricaoOrigem(String estadoDescricaoOrigem) {
		this.estadoDescricaoOrigem = estadoDescricaoOrigem;
	}

	// @Column(name = "NM_UF_PARA",length=100)
	@Column(name = "NM_UF_PARA")
	public String getEstadoDescricaoDestino() {
		return estadoDescricaoDestino;
	}

	public void setEstadoDescricaoDestino(String estadoDescricaoDestino) {
		this.estadoDescricaoDestino = estadoDescricaoDestino;
	}
	
	@OneToMany(mappedBy = "fnrh", targetEntity = FnrhDocumento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<FnrhDocumento> getFnrhDocumentoList() {
		return fnrhDocumentoList;
	}

	public void setFnrhDocumentoList(List<FnrhDocumento> fnrhDocumentoList) {
		this.fnrhDocumentoList = fnrhDocumentoList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fnrh other = (Fnrh) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
