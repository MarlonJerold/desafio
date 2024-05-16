package br.com.padrao.exception;

import org.jboss.seam.annotations.ApplicationException;

@ApplicationException
public class WebServiceException extends Exception{
	
	private static final long serialVersionUID = -3819129096834184049L;
	
	private String mensagem;

	public WebServiceException(String string) {
		this.mensagem = string;
	}

	@Override
	public String getMessage() {
		return mensagem;
	}    
	

	

}
