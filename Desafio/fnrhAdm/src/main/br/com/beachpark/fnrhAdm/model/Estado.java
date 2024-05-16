package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Entity para estado da base cep (CEP.ECT_PAIS)
 * @author Leste TI
 */
@Entity
@Table(name = "log_faixa_uf", schema="cep")
@NamedQueries({@NamedQuery(name="estado.getAllOrderSigla",query="from Estado e order by e.descricao")})
public class Estado implements Serializable {

	
	private static final long serialVersionUID = 7709226150960082546L;
	
	private String sigla;
	private String descricao;
	
	@Id
	@Column(name="UFE_SG",length=2)	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
	@Column(name="UFE_DESC",length=150)	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (sigla != null) {
			result += sigla.hashCode();
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
		Estado other = (Estado) obj;
		if (sigla == null) {
			if (other.sigla != null) {
				return false;
			}
		} else if (sigla!=other.sigla) {
			return false;
		}
		return true;
	}
	
	

	
	
	

}
