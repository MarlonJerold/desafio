package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe que representa o relacionamento entre as reservas cm e fnrhs
 * Tabela BPRECEPTOR.RELRESERVAFNRH
 * 
 * @author LesteTI
 */
public class RelacionaReservaFNRHCM implements Serializable {

	
	private static final long serialVersionUID = -1015275410786245344L;
	
	private Long reservaCM;
	private Long hotelId;
	private Long tipo;
	private Date data;	
	
	public static final Long TIPO_IMPORTACAO=0L;
	public static final Long TIPO_EXPORTACAO=1L;
	public static final Long TIPO_CHECKIN=2L;
	

	public Long getReservaCM() {
		return reservaCM;
	}

	public void setReservaCM(Long reservaCM) {
		this.reservaCM = reservaCM;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	

}
