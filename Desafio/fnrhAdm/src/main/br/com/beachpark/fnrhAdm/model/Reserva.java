package br.com.beachpark.fnrhAdm.model;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.NotNull;

import br.com.padrao.annotation.NotWhiteSpace;
import br.com.padrao.util.TomcatUtil;


/**
 * Reserva da base fnrh (FNRH.RESERVA)
 * 
 * @author LesteTI
 *
 */
@Entity 
//@Table(name = "FNRH.RESERVA")
@Table(name = "reserva", schema="fnrh")
public class Reserva implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = -2633917852573820175L;
	
	private Long id;
	private Long numReservaErp;
	private Long idReservaErp;
	private String email;	
	private String uuid;
	private Date dataCheckinPrevisto;
	private Date dataCheckoutPrevisto;
	private java.util.Date dataInsercao;
	private java.util.Date dataUpdate;
	private StatusIntegracaoFNRH statusIntegracao;
	private Hotel hotel;
	private Long localizador;
	private String origem;
	private String origemCod;
	private String link;
	//private List<Fnrh> fnrhList;
	
	
	@Id
	@Column(name = "ID")	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
	@Column(name = "ID_RESERVA_ERP")
	public Long getIdReservaErp() {
		return idReservaErp;
	}
	public void setIdReservaErp(Long idReservaErp) {
		this.idReservaErp = idReservaErp;
	}
	
	@Column(name = "LOCALIZADOR")
	public Long getLocalizador() {
		return localizador;
	}
	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}
	@Column(name = "NUM_RESERVA_ERP")
	public Long getNumReservaErp() {
		return numReservaErp;
	}
	public void setNumReservaErp(Long numReservaErp) {
		this.numReservaErp = numReservaErp;
	}
	
	//@Column(name = "EMAIL",length=250)
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "UUID",length=45)
	@NotWhiteSpace	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column(name = "DT_CHECKIN_PREVISTO")
	@NotNull	
	public Date getDataCheckinPrevisto() {
		return dataCheckinPrevisto;
	}
	public void setDataCheckinPrevisto(Date dataCheckinPrevisto) {
		this.dataCheckinPrevisto = dataCheckinPrevisto;
	}
	
	@Column(name = "DT_CHECKOUT_PREVISTO")
	@NotNull
	public Date getDataCheckoutPrevisto() {
		return dataCheckoutPrevisto;
	}
	public void setDataCheckoutPrevisto(Date dataCheckoutPrevisto) {
		this.dataCheckoutPrevisto = dataCheckoutPrevisto;
	}
	
	@Column(name = "DT_INSERT")
	@NotNull
	public java.util.Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(java.util.Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@Column(name = "DT_UPDATE")
	@NotNull
	public java.util.Date getDataUpdate() {
		return dataUpdate;
	}
	public void setDataUpdate(java.util.Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TP_STATUS_INTEGRA")
	public StatusIntegracaoFNRH getStatusIntegracao() {
		return statusIntegracao;
	}
	public void setStatusIntegracao(StatusIntegracaoFNRH statusIntegracao) {
		this.statusIntegracao = statusIntegracao;
	}
	
	@ManyToOne
	@JoinColumn(name = "HOTEL_ID")
	@NotNull
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	/*	@Column(name = "LOCALIZADOR")
	public Long getLocalizador() {
		return localizador;
	}
	public void setLocalizador(Long localizador) {
		this.localizador = localizador;
	}*/
	
	
	/*@OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY, 
		cascade = javax.persistence.CascadeType.ALL)
	@Cascade(value = {CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "RESERVA_ID")
	public List<Fnrh> getFnrhList() {
		return fnrhList;
	}
	public void setFnrhList(List<Fnrh> fnrhList) {
		this.fnrhList = fnrhList;
	}*/
	
	@Column(name = "ORIGEM")
	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}
	
	@Column(name = "ORIGEM_COD")
	public String getOrigemCod() {
		return origemCod;
	}

	public void setOrigemCod(String origemCod) {
		this.origemCod = origemCod;
	}
	
	
	@Transient
	public String getLink() {
		return TomcatUtil.obterValorPorChave("wsTipoConexaoFnrh")
				+ TomcatUtil.obterValorPorChave("wsAddressFnrh")
				//+ ":"
				//+ TomcatUtil.obterValorPorChave("wsPortFnrh")
				+ "/fnrh/home.seam?uuid=" + uuid;
	}
	public void setLink(String link) {
		this.link = link;
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
		Reserva other = (Reserva) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (id!=other.id) {
			return false;
		}
		return true;
	}
	


}
