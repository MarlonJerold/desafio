package br.com.beachpark.fnrhAdm.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.async.QuartzTriggerHandle;

import br.com.beachpark.fnrhAdm.job.AtualizaCheckinJob;
import br.com.beachpark.fnrhAdm.job.EmailReservaJob;
import br.com.beachpark.fnrhAdm.job.ExportaFnrhJob;
import br.com.beachpark.fnrhAdm.job.ImportaReservasBaseExternaJob;
import br.com.beachpark.fnrhAdm.job.ImportaReservasJob;
import br.com.beachpark.fnrhAdm.model.Parametrizacao;
import br.com.beachpark.fnrhAdm.repo.ParametrizacaoRepo;
import br.com.padrao.repo.TimeService;
import br.com.padrao.util.DateUtil;

@Name("quartzService")
@AutoCreate
@Startup(depends = {"entityManager","importaReservasJob", "importaReservasBaseExternaJob", "exportaFnrhJob", "emailReservaJob", "atualizaCheckinJob", "timeService","parametrizacaoRepo", "logService" })
@Scope(ScopeType.APPLICATION)

public class QuartzService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@In
	ImportaReservasJob importaReservasJob;
	
	@In
	ImportaReservasBaseExternaJob importaReservasBaseExternaJob;
	
	@In
	ExportaFnrhJob exportaFnrhJob;
	
	@In
	EmailReservaJob emailReservaJob;
	
	@In
	AtualizaCheckinJob atualizaCheckinJob;
	
	@In
	TimeService timeService;	
	
	@In
	ParametrizacaoRepo parametrizacaoRepo;
	
	@In
	LogService logService;  
	
	@SuppressWarnings("unused")
	private QuartzTriggerHandle quartzTestTriggerHandle;
	
	private QuartzTriggerHandle quartzEmail;
	private QuartzTriggerHandle quartzImportacao;
	private QuartzTriggerHandle quartzImportacaoBaseExterna;
	private QuartzTriggerHandle quartzExportacao;
	private QuartzTriggerHandle quartzAtualizacaoCheckin;
	
	public QuartzTriggerHandle getQuartzImportacao() {
		return quartzImportacao;
	}
	
	public QuartzTriggerHandle getQuartzImportacaoBaseExterna() {
		return quartzImportacaoBaseExterna;
	}

	public void setQuartzImportacaoBaseExterna(
			QuartzTriggerHandle quartzImportacaoBaseExterna) {
		this.quartzImportacaoBaseExterna = quartzImportacaoBaseExterna;
	}

	public void setQuartzImportacao(QuartzTriggerHandle quartzImportacao) {
		this.quartzImportacao = quartzImportacao;
	}

	public QuartzTriggerHandle getQuartzExportacao() {
		return quartzExportacao;
	}

	public void setQuartzExportacao(QuartzTriggerHandle quartzExportacao) {
		this.quartzExportacao = quartzExportacao;
	}

	public QuartzTriggerHandle getQuartzAtualizacaoCheckin() {
		return quartzAtualizacaoCheckin;
	}

	public void setQuartzAtualizacaoCheckin(
			QuartzTriggerHandle quartzAtualizacaoCheckin) {
		this.quartzAtualizacaoCheckin = quartzAtualizacaoCheckin;
	}	

	public QuartzTriggerHandle getQuartzEmail() {
		return quartzEmail;
	}

	public void setQuartzEmail(QuartzTriggerHandle quartzEmail) {
		this.quartzEmail = quartzEmail;
	}

	@Create
	public void scheduleTimer() {
		
    	quartzEmail = new QuartzTriggerHandle(null);
		quartzImportacao = new QuartzTriggerHandle(null);		
		quartzExportacao = new QuartzTriggerHandle(null);
		quartzAtualizacaoCheckin = new QuartzTriggerHandle(null);
		quartzImportacaoBaseExterna = new QuartzTriggerHandle(null);
		
		waitForResource();
			
		Parametrizacao parametrizacaoImportacaoCM = parametrizacaoRepo.getPorTipo(Parametrizacao.IMPORTACAO_CM);
		Parametrizacao parametrizacaoImportacaoBE = parametrizacaoRepo.getPorTipo(Parametrizacao.IMPORTACAO_BE);
		Parametrizacao parametrizacaoExportacaoCM = parametrizacaoRepo.getPorTipo(Parametrizacao.EXPORTACAO_CM);
	    Parametrizacao parametrizacaoEnvioEmailFNRH = parametrizacaoRepo.getPorTipo(Parametrizacao.ENVIO_EMAIL_FNRH);
		Parametrizacao parametrizacaoAtualizacaoCheckinCM = parametrizacaoRepo.getPorTipo(Parametrizacao.ATUALIZACAO_CHECKIN);
		
	
		try {   
			System.err.println("Entrou no quartz de importação de reservas do cm " + timeService.getDatabaseTimestamp());
			//Job que importa os dados do CM (Reservas confirmadas a serem exportadas para base FNRH)
			quartzImportacao = importaReservasJob.executaJob(getHorarioParametrizacao(parametrizacaoImportacaoCM),getIntervaloQuartz(parametrizacaoImportacaoCM));
		} catch (Exception e) {
			System.err.println("#{messages.error} - Erro na importação de reservas do cm " + e.getMessage());			
		}
	
		waitForResource();       
		
		try {
			System.err.println("Entrou no quartz de importação de reservas da base externa " + timeService.getDatabaseTimestamp());
			//Job que importa os dados do CM (Reservas confirmadas a serem exportadas para base FNRH)
			quartzImportacaoBaseExterna = importaReservasBaseExternaJob.executaJob(getHorarioParametrizacao(parametrizacaoImportacaoBE),getIntervaloQuartz(parametrizacaoImportacaoBE));
		} catch (Exception e) {
			System.err.println("#{messages.error} - Erro na importação de reservas da base externa " + e.getMessage());			
		}
	
		waitForResource();
		
		try {
			System.err.println("Entrou no quartz de exportacao das fnrhs para o cm  " + timeService.getDatabaseTimestamp());
			//Job que busca as fnrhs já preenchidas pelos hóspedes e exporta os dados para o cm
			quartzExportacao = exportaFnrhJob.executaJob(getHorarioParametrizacao(parametrizacaoExportacaoCM),getIntervaloQuartz(parametrizacaoExportacaoCM));
		} catch (Exception e) {
			System.err.println("#{messages.error} - Erro na exportação das fnrhs para o cm " + e.getMessage());			
		}			
		
		waitForResource();
		
		try {
			System.err.println("Entrou no quartz de atualização de dados no cm após chekin  " + timeService.getDatabaseTimestamp());
			//Atualização de dados no cm após checkin
			quartzAtualizacaoCheckin = atualizaCheckinJob.executaJob(getHorarioParametrizacao(parametrizacaoAtualizacaoCheckinCM),getIntervaloQuartz(parametrizacaoAtualizacaoCheckinCM));
		} catch (Exception e) {
			System.err.println("#{messages.error} -Erro na atualização de dados no cm após chekin " + e.getMessage());
			
		}    
		
		waitForResourceEmail();
		
		try {
			System.err.println("Entrou no quartz de envio de email de FNRH " + timeService.getDatabaseTimestamp());
			//Envio de email das FNRHs a serem preenchidas pelos hóspedes
			quartzEmail = emailReservaJob.executaJob(getHorarioParametrizacao(parametrizacaoEnvioEmailFNRH),getIntervaloQuartz(parametrizacaoEnvioEmailFNRH));
		} catch (Exception e) {
			System.err.println("#{messages.error} -Erro no envio de email de FNRH " + e.getMessage());				
		}
		
	}	
	
	private Date getHorarioParametrizacao(Parametrizacao param){
		int hora=0;
		int minuto=0;
		int segundo=0;
		
		GregorianCalendar calendar = new GregorianCalendar();
    	calendar.setTime(new Date());
    	if(!StringHelper.isEmpty(param.getHora())){
			String horaMinuto[] = param.getHora().split(":");
			
			hora=Integer.valueOf(horaMinuto[0]).intValue();
			minuto=Integer.valueOf(horaMinuto[1]).intValue();
			calendar= configurarHorario(hora,minuto,segundo);
		} 
		
		return calendar.getTime();		
	}
			
	public GregorianCalendar configurarHorario(int hora, int minuto, int s) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(timeService.getDatabaseTimestamp());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
		//calendar.set(Calendar.HOUR_OF_DAY, hora);
		calendar.set(Calendar.MILLISECOND, 0);
		//calendar.set(Calendar.MINUTE, minuto);
		//calendar.set(Calendar.SECOND, 0);
		
		return calendar;
	}
	
	public long getIntervaloQuartz(Parametrizacao param){		
		return DateUtil.converteEmMiliSegundos(param.getUnidadeMedida(), param.getQuantidade());		
	}
	
	private void waitForResource() {
		final long timeWait = 120000000;
		long i = 0L;
		while (i < timeWait) {
				i++;
		}
	}	
	private void waitForResourceEmail() {
			final long timeWait = 720000000;
			long i = 0L;
			while (i < timeWait) {
					i++;
			}
		}	



}
