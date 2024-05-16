package br.com.padrao.validator;

import org.hibernate.validator.Validator;

import br.com.padrao.annotation.NotWhiteSpace;

/**
 * Valida campo com espaço vazio
 * 
 * @author daniel.almeida
 * 
 */
public class NotWhiteSpaceValidator implements Validator<NotWhiteSpace> {

	public boolean isValid(Object value) {

		if (value == null) {
			return false;
		}

		if (!value.toString().trim().isEmpty()) {
			return true;
		}

		return false;
	}

	public void initialize(NotWhiteSpace arg0) {
		// TODO Auto-generated method stub

	}

}
