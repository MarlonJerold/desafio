package br.com.padrao.exception;

import org.jboss.seam.annotations.ApplicationException;

@ApplicationException
public class BusinessRuleException extends Exception{

	private static final long serialVersionUID = -7716432057718238636L;
	
	private String message;
	private Object[] params;
	
	public BusinessRuleException (String message){
		super();
		this.message=message;
	}
	
	public BusinessRuleException(String message,Object... params){
		super();
		this.message=message;
		this.params=params;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	

	

}
