package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Entity para cep da base cep (CEP.ECT_CEP)
 * @author Leste TI
 */
@Entity  
@Table(name="ect_cep", schema="cep")     
public class Cep implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4594531934316700649L;	
	
	private String cep;	
	private String bairro;
	private String logradouro;
	private String complemento;	
	private String estadoSigla;
	private String cidadeNome;	
	private String codigoIBGE;
	
	@Id
	@Column(name="CEP",length=8)
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Column(name="BAI_NO",length=72)
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name="LOG_NO",length=137)
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@Column(name="LOG_COMPLEMENTO",length=100)
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Column(name="UFE_SG",length=2)
	public String getEstadoSigla() {
		return estadoSigla;
	}
	public void setEstadoSigla(String estadoSigla) {
		this.estadoSigla = estadoSigla;
	}
	
	@Column(name="COD_IBGE",length=7)
	public String getCodigoIBGE() {
		return codigoIBGE;
	}
	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	
	@Column(name="LOC_NO",length=72)
	public String getCidadeNome() {
		return cidadeNome;
	}
	public void setCidadeNome(String cidadeNome) {
		this.cidadeNome = cidadeNome;
	}
	
	

}
