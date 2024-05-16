package br.com.padrao.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;
import org.jboss.seam.international.StatusMessage.Severity;

@Scope(ScopeType.CONVERSATION)
@Name("emailUtil")
@AutoCreate
public class EmailUtil implements Serializable {

	private static final long serialVersionUID = 7288132632494429989L;	
	
	@In(create=true)
	private Renderer renderer;

	@In
	private FacesMessages facesMessages;

	public static final String EMAIL_PADRAO = "no-reply@beachpark.com.br";
	public static final String EMAIL_PADRAO_INTERNO = "fnrhadm@beachpark.com.br";


	public String emailTo;
	public String emailFrom;
	public String assunto;
	public String corpoMensagem;

	public EmailUtil(String emailTo, String emailFrom, String assunto,
			String corpoMensagem) {
		this.emailTo = emailTo;
		this.emailFrom = emailFrom;
		this.assunto = assunto;
		this.corpoMensagem = corpoMensagem;
	}
	
	public EmailUtil() {
		
	}

	public void send() {
		try {
			renderer.render("/Mail.xhtml");			
		} catch (Exception e) {
			e.getStackTrace();
		//	System.err.println(e.getStackTrace());
			facesMessages
					.add(Severity.ERROR, "#{messages['emailErro']}",e.getMessage());
		}
	}

	public void enviarEmail() {
		send();
	}

	/* Getters e Setters */
	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpoMensagem() {
		return corpoMensagem;
	}

	public void setCorpoMensagem(String corpoMensagem) {
		this.corpoMensagem = corpoMensagem;
	}

	public static boolean emailValido(String email) {
		Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		Matcher m = p.matcher(email);
		return m.find();
	}

}
