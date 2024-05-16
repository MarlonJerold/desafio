package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  Entity para pais na base CEP (CEP.ECT_PAIS)
 *  @author Leste TI 
 */

@Entity
@Table(name = "ect_pais", schema="cep")
//@Table(name = "CEP.ECT_PAIS",uniqueConstraints = {@UniqueConstraint(columnNames={"sigla"})})
@NamedQueries( {@NamedQuery(name = "pais.getAllOrderNome", query = "from Pais p order by p.nomePortugues")})

public class Pais implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7698818364902395994L;
	private String sigla;
	private String siglaAlternativa;
	private String nomePortugues;
	private String nomeIngles;
	
	//Tem que acrescentar o espanhol
	private boolean pertenceAoMercosul;
	
	private String nomeEspanhol;
	private String nome;	
	
	public static final String PORTUGUES="pt";
	public static final String ESPANHOL="es";
	public static final String INGLES="eN";
	

	@Transient
	public String getNome(String lingua) {	
		nome=nomeIngles;
		if(lingua.equals(PORTUGUES)){
			nome=nomePortugues;
		}else if(lingua.equals(ESPANHOL)){
			nome=nomePortugues;
		}
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Id
	@Column(name="PAI_SG",length=2)	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name="PAI_SG_ALTERNATIVA",length=3)	
	public String getSiglaAlternativa() {
		return siglaAlternativa;
	}
	public void setSiglaAlternativa(String siglaAlternativa) {
		this.siglaAlternativa = siglaAlternativa;
	}
	
	@Column(name="PAI_NO_PORTUGUES",length=72)	
	public String getNomePortugues() {
		return nomePortugues;
	}
	public void setNomePortugues(String nomePortugues) {
		this.nomePortugues = nomePortugues;
	}
	
	
	@Column(name="PAI_MERCOSUL")
	public boolean isPertenceAoMercosul() {
		return pertenceAoMercosul;
	}
	public void setPertenceAoMercosul(boolean pertenceAoMercosul) {
		this.pertenceAoMercosul = pertenceAoMercosul;
	}
	
	@Column(name="PAI_NO_INGLES")
	public String getNomeIngles() {
		return nomeIngles;
	}
	public void setNomeIngles(String nomeIngles) {
		this.nomeIngles = nomeIngles;
	}
	
	@Column(name="PAI_NO_ESPANHOL")
	public String getNomeEspanhol() {
		return nomeEspanhol;
	}
	public void setNomeEspanhol(String nomeEspanhol) {
		this.nomeEspanhol = nomeEspanhol;
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
		Pais other = (Pais) obj;
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
