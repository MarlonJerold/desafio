package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

public class ListaReserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6039343812888093418L;

	private Reserva reserva;
	private boolean permiteEdicao;

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public boolean isPermiteEdicao() {
		return permiteEdicao;
	}

	public void setPermiteEdicao(boolean permiteEdicao) {
		this.permiteEdicao = permiteEdicao;
	}

}
