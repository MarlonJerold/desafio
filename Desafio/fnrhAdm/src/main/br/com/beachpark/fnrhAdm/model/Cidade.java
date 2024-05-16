package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity para cidade da base cep (CEP.LOG_LOCALIDADE)
 * @author Leste TI
 */
@Entity
@Table(name = "log_localidade", schema="cep")
public class Cidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1320683593292235314L;
	
	private Long numero;	
	private String siglaEstado;
	private String descricao;
	private String codigoIBGE;
	
	
	@Id
	@Column(name="LOC_NU",length=20)
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	@Column(name="UFE_SG",length=2)
	public String getSiglaEstado() {
		return siglaEstado;
	}
	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}
	
	
	@Column(name="LOC_NO",length=72)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name="MUN_NU",length=7)
	public String getCodigoIBGE() {
		return codigoIBGE;
	}
	public void setCodigoIBGE(String codigoIBGE) {
		this.codigoIBGE = codigoIBGE;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (numero != null) {
			result += numero.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Cidade other = (Cidade) obj;
		if (numero == null) {
			if (other.numero != null) {
				return false;
			}
		} else if (numero!=other.numero) {
			return false;
		}
		return true;
	}

	

}
