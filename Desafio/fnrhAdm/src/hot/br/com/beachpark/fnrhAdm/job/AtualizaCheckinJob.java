package br.com.beachpark.fnrhAdm.job;

import java.util.Date;

import javax.persistence.PersistenceException;

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

import br.com.beachpark.fnrhAdm.service.ReservaService;

@Name("atualizaCheckinJob")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class AtualizaCheckinJob {
	
	@In
	private ReservaService reservaService;
	
	@Asynchronous	
	@Transactional
	public QuartzTriggerHandle executaJob(@Expiration Date when,
			@IntervalDuration long interval) {		
		try{
			System.out.print("Realização da atualização de reservas do cm que efetuaram checkin ......\n");			
			reservaService.atualizarReservasCheckinViaAgendadorTarefa();
		}catch (IllegalStateException bre) {

		}catch (PersistenceException bre) {

		}catch (Exception bre) {

		}					
		return null;
	}

}
