package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.padrao.annotation.NotWhiteSpace;


/**
 * Entidade para a tabela de pais do cm  (CM.ESTADO) 
 * @author LesteTI
 *
 */
@Entity
@Table(name = "PAIS", schema="cm")
public class PaisCM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4760732586451469787L;
	private Long id;
	private String nome;
	private String codigoISO;
	
	public static final String COD_BRASIL="BR";
	
	@Id
	@Column(name = "IDPAIS")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NOMEPAIS")
	@NotWhiteSpace	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "CODISO")
	@NotWhiteSpace
	public String getCodigoISO() {
		return codigoISO;
	}

	public void setCodigoISO(String codigoISO) {
		this.codigoISO = codigoISO;
	}
	

}
