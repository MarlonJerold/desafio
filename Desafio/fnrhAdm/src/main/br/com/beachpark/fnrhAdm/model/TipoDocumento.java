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
 * Entity para Tipo de Documento da base fnrh (FNRH.TIPODOCUMENTO)
 * 
 * @author LesteTI
 * 
 */

@Entity
//@Table(name = "FNRH.TIPODOCUMENTO")
@Table(name = "tipodocumento", schema="fnrh") 
@NamedQueries({@NamedQuery(name="tipoDocumento.getAllOrderDescricao",query="from TipoDocumento td order by td.descricao")})
public class TipoDocumento implements Serializable {

	private static final long serialVersionUID = 9116846050323846970L;
	
	private Long id;
	private String tipoLingua;	
	private String codigo;
	private String descricao;
	private String mascara;  
	private String obrigaOrgao;
	private Long tipoDocumentoErp;	

	public static String OBRIGA_ORGAO="S";	
	
	
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
	
	
	@Column(name = "CDTIPODOCUMENTO",length=10)
	@NotWhiteSpace
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "NOME",length=10)
	@NotWhiteSpace
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "MASCARA",length=20)
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	
	
	@Column(name = "OBRIGAORGAO",length=1)
	@NotWhiteSpace
	public String getObrigaOrgao() {
		return obrigaOrgao;
	}
	public void setObrigaOrgao(String obrigaOrgao) {
		this.obrigaOrgao = obrigaOrgao;
	}
	
	@Column(name = "ID_TIPODOCERP")	
	public Long getTipoDocumentoErp() {
		return tipoDocumentoErp;
	}
	public void setTipoDocumentoErp(Long tipoDocumentoErp) {
		this.tipoDocumentoErp = tipoDocumentoErp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (id != null) {
			result += id.hashCode();
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
		TipoDocumento other = (TipoDocumento) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (id!=other.id) {
			return false;
		}
		return true;
	}

}
