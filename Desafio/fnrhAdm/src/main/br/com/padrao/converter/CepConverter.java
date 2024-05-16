package br.com.padrao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
/**
 * Converte campo de Cep para ser mostrado com "."
 * Exemplo: No banco 60000 mostrará 60.000
 * Formata o campo para ter sempre 5 dígitos 
 * Exemplo: 6000 mostrará 06.000
 * @author Sabrina
 */
public class CepConverter extends NumberConverter {
	private static final int LENGTH_CEP = 5;
	private static final int INICIO = 0;
	private static final int PONTO = 5;
	
	public CepConverter() {
		super();
		setMaxIntegerDigits(LENGTH_CEP);
		setMinIntegerDigits(LENGTH_CEP);
	}

	public Long getAsObject(FacesContext context, UIComponent component,
			String value) {
		/*
		 * Ir� converter CEP formatado para um n� sem pontos
		 */
		String cep = value;
		if (value != null && !value.equals("")) {
			cep = value.replaceAll("\\.", "");

			return new Long(cep);
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			String value) {
		/*
		 * Ir� converter CEP n�o formatado para um com pontos.
		 */
		String cep = value;
		if (cep != null && cep.length() == LENGTH_CEP) {
			cep = cep.substring(INICIO, PONTO) + "." + cep.substring(PONTO, LENGTH_CEP);
		}
		return cep;
	}
}
