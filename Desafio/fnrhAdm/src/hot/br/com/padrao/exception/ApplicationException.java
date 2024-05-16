package br.com.padrao.exception;

/**
 * @author sabrina.lanny
 *
 */
public final class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -3064121947903806214L;
	
	private String message;
	
	private Object[] params;

	public String getMessage() {
		return message;
	}

	public Object[] getParams() {
		return params;
	}

	public ApplicationException(String message) {
		super();
		this.message = message;
	}
	
	public ApplicationException(String message, Object... params) {
		super();
		this.message = message;
		this.params = params;
	}

}
