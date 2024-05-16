package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Entity para Parametrizacao da base fnrh.(fnrhsr.parametrizacao)
 * 
 * @author LesteTI
 * 
 */

@Entity
@Table(name = "parametrizacao", schema="fnrhsr")
//@Table(name = "parametrizacao")
public class Parametrizacao implements Serializable {
	
	private static final long serialVersionUID = -6920718127448840216L;
	
	private Long id;
	private String tipo;
	private String descricao;
	private BigDecimal valor;
	private Long quantidade;
	private String unidadeMedida;
	private String hora;
	private boolean inativo;
	
	public static final String IMPORTACAO_CM="IMPORTACAO";
	public static final String IMPORTACAO_BE="IMPORTACAO_BASE_EXTERNA";
	public static final String ENVIO_EMAIL_FNRH="ENVIO_EMAIL";
	public static final String EXPORTACAO_CM="EXPORTACAO";
	public static final String ANTECEDENCIA_ENVIO_EMAIL="ANTECEDENCIA_ENVEMAIL";
	public static final String ATUALIZACAO_CHECKIN="ATUALIZACAO_CHECKIN";
	public static final String EMAIL_TESTES="EMAIL_TESTES";
	public static final String EMAIL_CCO="EMAIL_CCO";
	

	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "TIPO",length=45)
	@NotWhiteSpace
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "DESCRICAO",length=200)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "VALOR")
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Column(name = "QUANTIDADE")
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "UNIDADEMEDIDA")
	public String getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	
	@Column(name = "HORA")
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	
	@Column(name = "INATIVO")
	public boolean isInativo() {
		return inativo;
	}
	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}
	
		
		
	

}
