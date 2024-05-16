package br.com.beachpark.fnrhAdm.job;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.async.Asynchronous;
import org.jboss.seam.annotations.async.Expiration;
import org.jboss.seam.annotations.async.IntervalDuration;
import org.jboss.seam.async.QuartzTriggerHandle;
import org.jboss.seam.faces.Renderer;

import br.com.beachpark.fnrhAdm.repo.ParametrizacaoRepo;
import br.com.beachpark.fnrhAdm.repo.ReservaRepo;
import br.com.beachpark.fnrhAdm.service.LogService;
import br.com.beachpark.fnrhAdm.service.ReservaService;

@Name("emailReservaJob")
@AutoCreate
@Scope(ScopeType.APPLICATION)

/**
 * 
 * @author LesteTI
 *
 */
public class EmailReservaJob {
	
	@In
	private ReservaService reservaService;
	
	@In
	private Renderer renderer;
	
	@In
	private LogService logService;
	
	@In
	private ReservaRepo reservaRepo;
	
	@In
	private ParametrizacaoRepo parametrizacaoRepo;
	
	private String emailFrom;
	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailAssunto() {
		return emailAssunto;
	}

	public void setEmailAssunto(String emailAssunto) {
		this.emailAssunto = emailAssunto;
	}

	public String getEmailLink() {
		return emailLink;
	}

	public void setEmailLink(String emailLink) {
		this.emailLink = emailLink;
	}

	private String emailTo;
	private String emailAssunto;
	private String emailLink;
	
	@Asynchronous	
	@Transactional
	public QuartzTriggerHandle executaJob(@Expiration Date when,
			@IntervalDuration long interval) {		
		try{
			System.out.print("Envio de email das reservas exportadas......\n");	
			reservaService.enviaEmailReservasImportadas();
		}catch(Exception e)	{
			
		}
		return null;
	}	
	
		
	
	
	
	

}
