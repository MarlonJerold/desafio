package br.com.beachpark.fnrhAdm.model;


public enum TipoDocumentoEnum {	
	
	PASSAPORTE("PAS","Passaporte"),
	CEDULA_IDENTIDADE("RG","Cédula de Identidade"),	
	IDENTIDADE_ESTRANGEIRA("CIE","Cédula de Identidade Estrangeira"),
	CPF("CPF","CPF"),
	CERTIDAO_NASCIMENTO("CN","Cédula de Nascimento");
	
	
	private String codigo;
	private String descricao; 
	
	private TipoDocumentoEnum(String codigo){		
		this.codigo=codigo;
	}
	
	private TipoDocumentoEnum(String codigo,String descricao){	
		this.descricao=descricao;
		this.codigo=codigo;
	}	
	
	
	@Override
	public String toString(){
		return getDescricao();
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
