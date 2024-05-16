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

import br.com.beachpark.fnrhAdm.service.ReservaService;
import br.com.padrao.exception.BusinessRuleException;


@Name("importaReservasJob")
@AutoCreate
@Scope(ScopeType.APPLICATION)
public class ImportaReservasJob {
	
	@In
	private ReservaService reservaService;
	
	@Asynchronous	
	@Transactional
	public QuartzTriggerHandle executaJob(@Expiration Date when,
			@IntervalDuration long interval) {		
		try{
			System.out.print("Realização da importação de reservas ......\n");			
			reservaService.importarReservasViaAgendadorTarefa();
		}catch (BusinessRuleException bre) {

		}	
		
		return null;
	}


}
