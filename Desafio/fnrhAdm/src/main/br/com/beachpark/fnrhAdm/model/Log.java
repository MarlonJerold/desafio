package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Entity para Log da base fnrh (fnrhsr.log)
 * 
 * @author LesteTI
 * 
 */

@Entity
@Table(name = "log", schema="fnrhsr")
//@Table(name = "log")
public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6350666283941663788L;	
	
	private Long id;	
	private Date dataHora;
	private String descricao;
	private String detalhe;
	private String ip;
	private String reserva;
	private String uuid;
	private TipoLog tipo;
	

	
	
	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "DATA")
	@NotNull
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	@Column(name = "DESCRICAO",length=200)
	@NotWhiteSpace
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "DETALHE",length=2000)	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	
	@Column(name = "IP",length=20)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name = "RESERVA",length=20)
	public String getReserva() {
		return reserva;
	}
	public void setReserva(String reserva) {
		this.reserva = reserva;
	}
	
	@Column(name = "UUID",length=45)
	@NotWhiteSpace
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="TIPO")
	public TipoLog getTipo() {
		return tipo;
	}
	public void setTipo(TipoLog tipo) {
		this.tipo = tipo;
	}
	
	

}
