package br.com.padrao.converter;

import javax.faces.convert.NumberConverter;
/**
 * Formata campo Cep Complementar para ter sempre 3 dígitos.
 * Exemplo: Se  no banco estiver gravado 0 mostrará 000 
 * @author Sabrina
 */
public class CepComplementoConverter extends NumberConverter {
	 
	public static final int LENGTH = 3;
	
	public CepComplementoConverter() {
		super();
		setMaxIntegerDigits(LENGTH);
		setMinIntegerDigits(LENGTH);
	}
}