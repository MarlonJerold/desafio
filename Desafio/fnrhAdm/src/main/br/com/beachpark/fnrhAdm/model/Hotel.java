package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Entity para Hotel da base fnrh.(FNRH.HOTEL)
 * 
 * @author LesteTI
 * 
 */

@Entity
//@Table(name = "FNRH.HOTEL")
@Table(name = "hotel", schema="fnrh")
@NamedQueries({@NamedQuery(name="hotel.getAllAtivosOrderDescricao",query="from Hotel h where h.ativo='T' order by h.descricao"),
	  @NamedQuery(name="hotel.getAllOrderDescricao",query="from Hotel h order by h.descricao")
})
public class Hotel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1742659232086395667L;
	
	private Long id;
	private String descricao;
	private String codigoErp;
	private String ativo;
	
	
	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "NMHOTEL",length=45)
	@NotWhiteSpace	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "CDHOTELERP",length=10)	
	@NotWhiteSpace	
	public String getCodigoErp() {
		return codigoErp;
	}
	public void setCodigoErp(String codigoErp) {
		this.codigoErp = codigoErp;
	}
	
	@Column(name = "TP_ATIVO",length=1)	
	@NotWhiteSpace
	public String getAtivo() {
		return ativo;
	}
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	
	

}
