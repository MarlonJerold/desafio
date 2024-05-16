package br.com.beachpark.fnrhAdm.model;

/**
 * Enum que representa as opções de Meio de Transporte
 * @author LesteTI
 *
 */
public enum MeioTransporteEnum {
	
	AVIAO("A","Avião"),
	AUTOMOVEL("C","Automóvel"),
	NAVIO("N","Navio"),
	OUTROS("O","Outros");
	
	private String codigo;
	private String descricao;
	
	
	private MeioTransporteEnum (String codigo){
		this.codigo=codigo;
	}
	
	private MeioTransporteEnum (String codigo,String descricao){
		this.descricao=descricao;
		this.codigo=codigo;
	}
	
	@Override
	public String toString(){
		return getDescricao();
	}
	
	
	/* Getters and Setters */
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public static MeioTransporteEnum get(String codigo) {
		for (MeioTransporteEnum m : MeioTransporteEnum.values()) {
			if (m.codigo.equals(codigo))
				return m;
		}
		return null;
	}
}
