package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Entity para Genero da base fnrh (FNRH.GENERO)  
 * @author LesteTI
 * 
 */

@Entity
//@Table(name = "FNRH.GENERO")
@Table(name = "genero", schema="fnrh")
public class Genero  implements Serializable {

	private static final long serialVersionUID = 5465915124608559105L;
	
	private Long id;
	private String tipoLingua;
	private String codigo;
	private String descricao;
	
	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TP_LANG")
	@NotWhiteSpace
	public String getTipoLingua() {
		return tipoLingua;
	}
	public void setTipoLingua(String tipoLingua) {
		this.tipoLingua = tipoLingua;
	}
	
	
	@Column(name = "CDGENERO")
	@NotWhiteSpace
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "NOME")
	@NotWhiteSpace
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
