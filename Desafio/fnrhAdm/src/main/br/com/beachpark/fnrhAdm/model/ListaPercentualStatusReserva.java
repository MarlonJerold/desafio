package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

public class ListaPercentualStatusReserva implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6039343812888093418L;

	private StatusIntegracaoFNRH status;
	private Double totalPercentual;

	public StatusIntegracaoFNRH getStatus() {
		return status;
	}

	public ListaPercentualStatusReserva(StatusIntegracaoFNRH status, Double totalPercentual) {
		super();
		this.status = status;
		this.setTotalPercentual(totalPercentual);
	}

	public void setStatus(StatusIntegracaoFNRH status) {
		this.status = status;
	}

	public Double getTotalPercentual() {
		return totalPercentual;
	}

	public void setTotalPercentual(Double totalPercentual) {
		this.totalPercentual = totalPercentual;
	}

	

}
