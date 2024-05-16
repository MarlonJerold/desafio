package br.com.padrao.validator;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *  Valida campo de CNPJ
 * @author Sabrina
 *
 */
public class CnpjValidator implements Validator {

	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (!validaCNPJ(String.valueOf(arg2))) {  		
			FacesMessage message = new FacesMessage();  
			message.setSeverity(FacesMessage.SEVERITY_ERROR);  
			message.setSummary(ResourceBundle.getBundle(FacesContext.getCurrentInstance()
					.getApplication().getMessageBundle()).getString("validator.cnpj"));  
			throw new ValidatorException(message);  
		
		}  
	}  
			   
	/** 
	* Valida CNPJ  
	* 
	*/  
	//CHECKSTYLE:OFF
	public static boolean validaCNPJ(String cnpj)  { 
		if (cnpj == null) {
			return false;
		}
		
		while (cnpj.length() < 14) {
			cnpj = "0" + cnpj;
		}
		
		try {  
			Long.parseLong(cnpj);  
		} catch (NumberFormatException e) { // CNPJ não possui somente números  
			return false;  
		}          
			   
		int soma = 0;  
		String cnpjCalc = cnpj.substring(0, 12);  
			   
		char[] chrCnpj = cnpj.toCharArray();  
		  
		for (int i = 0; i < 4; i++) {  
			if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {  
				soma += (chrCnpj[i] - 48) * (6 - (i + 1));
			}
		}
		for (int i = 0; i < 8; i++) {  
			if (chrCnpj[i + 4] - 48 >= 0 && chrCnpj[i + 4] - 48 <= 9) {  
				soma += (chrCnpj[i + 4] - 48) * (10 - (i + 1));  
			}
		}
		   
		int dig = 11 - soma % 11;  
		//cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc)))
		//	.append(dig != 10 && dig != 11 ? Integer.toString(dig) : "0").toString();
		if (dig != 10 && dig != 11) {
			cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc)))
				.append(Integer.toString(dig)).toString();
		} else {
			cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc))).append(0).toString();
		}
			
		soma = 0;  
		for (int i = 0; i < 5; i++) {  
			if (chrCnpj[i] - 48 >= 0 && chrCnpj[i] - 48 <= 9) {  
				soma += (chrCnpj[i] - 48) * (7 - (i + 1));
			}
		}
		   
		for (int i = 0; i < 8; i++) {  
			if (chrCnpj[i + 5] - 48 >= 0 && chrCnpj[i + 5] - 48 <= 9) {  
				soma += (chrCnpj[i + 5] - 48) * (10 - (i + 1));
			}
		}
		dig = 11 - soma % 11;  

		if (dig != 10 && dig != 11) {
			cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc)))
				.append(Integer.toString(dig)).toString();
		} else { 
			cnpjCalc = (new StringBuilder(String.valueOf(cnpjCalc))).append(0).toString();
		}   
		if (!cnpj.equals(cnpjCalc)) {  
			return false; 
		}
	
		return true;
	}
	//CHECKSTYLE:ON

}
		




