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
 * Entity para Meio de Transporte  da base fnrh. (FNRH.MEIO)
 * 
 * @author LesteTI
 * 
 */

@Entity
//@Table(name = "FNRH.MEIO")
@Table(name = "meio", schema="fnrh")
public class MeioTransporte implements Serializable {

	
	private static final long serialVersionUID = -6990586257826213454L;
	
	private Long id;
	private String tipoLingua;
	private String codigo;
	private String descricao;
	private String codigoErp;
	
	
	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TP_LANG",length=10)
	@NotWhiteSpace
	public String getTipoLingua() {
		return tipoLingua;
	}
	public void setTipoLingua(String tipoLingua) {
		this.tipoLingua = tipoLingua;
	}
	
	@Column(name = "CD_MEIO",length=2)
	@NotWhiteSpace
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "NOME",length=45)
	@NotWhiteSpace
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "CD_MEIO_ERP",length=1)
	@NotWhiteSpace
	public String getCodigoErp() {
		return codigoErp;
	}
	public void setCodigoErp(String codigoErp) {
		this.codigoErp = codigoErp;
	}
	

}
