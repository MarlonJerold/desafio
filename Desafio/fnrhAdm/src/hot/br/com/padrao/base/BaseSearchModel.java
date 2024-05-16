package br.com.padrao.base;

import java.io.Serializable;

/**
 * Para uso em conjunto com BaseSearch.
 * 
 * A entidade deve ser convertida para BaseSearchModel para evitar que objetos
 * complexos vindos do hibernate sejam carregados para o cliente.
 * BaseSearchModel pode ser extendida para adicionar novas propriedades.
 * 
 * @author leonardo
 * 
 */
public class BaseSearchModel implements Serializable {

	private static final long serialVersionUID = -1730205288944302681L;

	protected String id;

	protected String codigo;

	protected String nome;

	public BaseSearchModel() {

	}

	public BaseSearchModel(String id, String codigo, String nome) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		BaseSearchModel other = (BaseSearchModel) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
