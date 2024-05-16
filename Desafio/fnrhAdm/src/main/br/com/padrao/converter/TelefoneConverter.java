package br.com.padrao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
/**
 * Converte campo de Telefone para ser mostrado com "-"
 * Exemplo: No banco 22222222 mostrará 2222-2222
 * Formata o campo para ter sempre 8 dígitos 
 * Exemplo: 3333333 mostrará 0333-3333
 * @author Sabrina
 */
public class TelefoneConverter extends NumberConverter {
	
	private static final int LENGTH_TELEFONE = 8;
	private static final int INICIO = 0; // Início da string antes do traço
	private static final int ANTES_TRACO = 3; // Fim da string antes do traço
	private static final int DEPOIS_TRACO = 4; // Início da string depois do traço
	
	
	public TelefoneConverter() {
		super();
		setMaxIntegerDigits(LENGTH_TELEFONE);
		setMinIntegerDigits(LENGTH_TELEFONE);
	}
	public Long getAsObject(FacesContext context, UIComponent component,
			String value) {
			
		/*
		 * Ir� converter Telefone formatado para um n� sem tra�o
		 */
		String tel = value;
		if (value != null && !value.equals("")) {
			tel = value.replaceAll("\\-", "");

			return new Long(tel);
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component,
			String value) {
		/*
		 * Ir� converter Telefone n�o formatado para um n�mero com tra�o.
		 */
		String tel = value;
		if (tel != null && tel.length() == LENGTH_TELEFONE) {
			tel = tel.substring(INICIO, ANTES_TRACO) + "-" 
			+ tel.substring(DEPOIS_TRACO, LENGTH_TELEFONE);
		}
		return tel;
	}
}
