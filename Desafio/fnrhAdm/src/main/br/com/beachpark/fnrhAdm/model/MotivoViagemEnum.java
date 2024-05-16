package br.com.beachpark.fnrhAdm.model;

/**
 * Enum que representa as opções de Motivo de Viagem
 * 
 * @author LesteTI
 * 
 */
public enum MotivoViagemEnum {

	LAZER("L", "Lazer"), NEGOCIOS("N", "Negócios"), TURISMO("T", "Turismo"), OUTROS(
			"O", "Outros");

	private String descricao;
	private String codigo;

	private MotivoViagemEnum(String codigo) {
		this.codigo = codigo;
	}

	private MotivoViagemEnum(String codigo, String descricao) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	@Override
	public String toString() {
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

	public static MotivoViagemEnum get(String codigo) {
		for (MotivoViagemEnum m : MotivoViagemEnum.values()) {
			if (m.codigo.equals(codigo))
				return m;
		}
		return null;
	}

}
