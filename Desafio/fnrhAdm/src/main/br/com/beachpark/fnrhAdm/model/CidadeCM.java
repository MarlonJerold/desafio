package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Entidade para a tabela de cidades do cm (M.CIDADES) 
 * @author LesteTI
 *
 */
@Entity
@Table(name = "CIDADES", schema="cm")
public class CidadeCM implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6757008104092135626L;
	
	private Long id;
	private EstadoCM estadoCM;
	private PaisCM paisCM;
	private String nome;
	private String codIBGE;
	
	@Id
	@Column(name = "IDCIDADES")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "IDESTADO")
	@NotNull
	public EstadoCM getEstadoCM() {
		return estadoCM;
	}
	public void setEstadoCM(EstadoCM estadoCM) {
		this.estadoCM = estadoCM;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "IDPAIS")
	public PaisCM getPaisCM() {
		return paisCM;
	}
	public void setPaisCM(PaisCM paisCM) {
		this.paisCM = paisCM;
	}
	
	@Column(name = "NOME")
	@NotWhiteSpace	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	@Column(name = "CODMUNICIPIOIBGE")
	@NotWhiteSpace	
	public String getCodIBGE() {
		return codIBGE;
	}
	public void setCodIBGE(String codIBGE) {
		this.codIBGE = codIBGE;
	}
	
	
	

}
