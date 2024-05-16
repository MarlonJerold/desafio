package br.com.beachpark.fnrhAdm.model;

/**
 * Enum que representa as opções de tipo de Log.
 * @author LesteTI
 *
 */

public enum TipoLog {
	
	INFORMACAO(0L,"Informação"),
	FALHA(1L,"Falha");
	
	private String descricao;
	private Long codigo;
	
	private TipoLog(Long codigo,String descricao){	
		this.descricao=descricao;
		this.codigo=codigo;
	}
	
	@Override
	public String toString(){
		return getDescricao();
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
		
	

}
