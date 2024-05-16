package br.com.padrao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base para utilização do objeto rich:suggestionbox
 * 
 * Para utilizá-la deve-se estender a classe base passando como parâmetros a
 * classe do objeto, o tipo de dados do objeto e os nomes dos campos id, codigo
 * e nome.
 * 
 * @author leonardo
 * 
 * @param <E>
 *            Classe do objeto a ser pesquisado
 */
public abstract class BaseSearch<E> implements Serializable {

	private static final long serialVersionUID = 7410269927566720303L;

	private E objeto;

	private BaseSearchModel baseSearchModel;

	private List<BaseSearchModel> suggestionList = 
		new ArrayList<BaseSearchModel>();

	private List<E> objetoList = new ArrayList<E>();

	public String getId() {
		if (baseSearchModel == null) {
			return null;
		} else {
			return baseSearchModel.getId();
		}
	}

	public String getCodigo() {
		if (baseSearchModel == null) {
			return null;
		} else {
			return baseSearchModel.getCodigo();
		}
	}

	public String getNome() {
		if (baseSearchModel == null) {
			return null;
		} else {
			return baseSearchModel.getNome();
		}
	}

	public void setNome(String nome) {
		// read-only
	}

	public void setCodigo(String codigo) {
		// read-only
	}

	public void setId(String id) {
		try {
			if (id == null || "".equals(id)) {
				setBaseSearchModel(null);
			} else if (suggestionList != null) {
				for (BaseSearchModel item : suggestionList) {
					if (item.getId().equals(id)) {
						setBaseSearchModel(item);
					}
				}
			} else {
				setBaseSearchModel(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	private void setBaseSearchModel(BaseSearchModel baseSearchModel) {
		this.baseSearchModel = baseSearchModel;
		if (baseSearchModel == null) {
			this.objeto = null;
		} else if (objetoList != null) {
			for (E objeto : objetoList) {
				if (equals(baseSearchModel, objeto)) {
					this.objeto = objeto;
				}
			}
		}
	}

	protected abstract boolean equals(BaseSearchModel baseSearchModel,
			E objeto);

	protected abstract List<E> preencheSuggestionList(String param);

	public List<BaseSearchModel> getSuggestionList(Object param) {
		if (!param.equals("null")) {
			objetoList = preencheSuggestionList((String) param);
			suggestionList.clear();
			for (E objeto : objetoList) {
				suggestionList.add(convertToBaseSearchModel(objeto));
			}
		}
		return suggestionList;
	}

	protected abstract BaseSearchModel convertToBaseSearchModel(E objeto);

	public E getObjeto() {
		return objeto;
	}

	public void setObjeto(E objeto) {
		this.objeto = objeto;
		baseSearchModel = null;
		if (objeto != null) {
			baseSearchModel = convertToBaseSearchModel(objeto);
		}
		if (baseSearchModel != null
				&& !this.suggestionList.contains(baseSearchModel)) {
			this.suggestionList.add(baseSearchModel);
		}
	}

}
