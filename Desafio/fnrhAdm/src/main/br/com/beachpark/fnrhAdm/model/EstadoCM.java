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
 * Entidade para a tabela de estado do cm  (CM.ESTADO) 
 * @author LesteTI
 *
 */
@Entity
@Table(name = "ESTADO", schema="cm")
public class EstadoCM implements Serializable{	

	private static final long serialVersionUID = -6104260557479096313L;
	
	
	private Long id;
	private String codigo;
	private String nome;
	private PaisCM paisCM;
	
	
	@Id
	@Column(name = "IDESTADO")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "CODESTADO")
	@NotWhiteSpace
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "NOMEESTADO")
	@NotWhiteSpace	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToOne
	@JoinColumn(name = "IDPAIS")
	@NotNull
	public PaisCM getPaisCM() {
		return paisCM;
	}
	public void setPaisCM(PaisCM paisCM) {
		this.paisCM = paisCM;
	}
	
	

}
