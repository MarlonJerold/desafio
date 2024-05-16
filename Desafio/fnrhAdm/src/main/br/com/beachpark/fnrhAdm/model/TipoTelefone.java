package br.com.beachpark.fnrhAdm.model;

/**
 * Enum que representa as opções de tipo de Log.
 *
 * @author LesteTI
 *
 */
public enum TipoTelefone {
	
	CELULAR("L"),
	PARTICULAR("P");
	
	private String descricao;
	
	private TipoTelefone(String descricao){
		this.descricao=descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	
	
	@Override
	public String toString(){
		return getDescricao();
	}

}
