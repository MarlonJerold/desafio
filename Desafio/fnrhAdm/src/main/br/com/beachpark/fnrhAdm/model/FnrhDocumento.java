package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * @author sabrina
 * 
 */
@Entity
@Table(name = "fnrh_documento", schema="fnrh")
public class FnrhDocumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2650439101338846551L;

	private Long id;
	private TipoDocumento tipoDocumento;
	private String numero;
	private String orgao;
	private Fnrh fnrh;

	public FnrhDocumento() {
		super();
	}

	public FnrhDocumento(Fnrh fnrh) {
		this.fnrh = fnrh;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "tipodocumento_id")
	@NotNull
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@Column(name = "numero_documento")
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Column(name = "orgao_documento")
	public String getOrgao() {
		return orgao;
	}

	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	@ManyToOne
	@JoinColumn(name = "fnrh_id")
	@NotNull
	public Fnrh getFnrh() {
		return fnrh;
	}

	public void setFnrh(Fnrh fnrh) {
		this.fnrh = fnrh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FnrhDocumento other = (FnrhDocumento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
