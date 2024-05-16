package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.util.StringHelper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Hotel;
import br.com.beachpark.fnrhAdm.model.ListaReserva;
import br.com.beachpark.fnrhAdm.model.Parametrizacao;
import br.com.beachpark.fnrhAdm.model.Reserva;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.padrao.base.EMMySql;
import br.com.padrao.repo.TimeServiceMySql;
import br.com.padrao.util.DateUtil;

/**
 * Repositório da tabela de reserva da base FNRH (fnrh.reserva)
 * @author LesteTI
 */
@Name("reservaRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class ReservaRepo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5579617915285110646L;
	
	@In
	private EMMySql emMySql;
	
	@In
	private TimeServiceMySql timeServiceMySql;	
	
	@In
	private ParametrizacaoRepo parametrizacaoRepo;
	
	public Reserva getPorId(Long id) {
		return emMySql.find(Reserva.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public Reserva getPorIdErp(Long reservaId) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  ");
		hql.append("where r.idReservaErp=:idReserva ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("idReserva", reservaId);
		
		List<Reserva> reserva = query.getResultList();
		if(reserva.size()>0){
			return (Reserva) reserva.get(0);
		}else{
			return null;
		}
		
	}	
	
	@SuppressWarnings("unchecked")
	public List<Reserva> getPorStatus(StatusIntegracaoFNRH status) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  ");
		hql.append("where r.statusIntegracao=:status ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("status", status);
		
		List<Reserva> reservas = query.getResultList();
		return reservas;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Reserva> getReservasAptasEnvioEmail(List<StatusIntegracaoFNRH> status,Long antecedencia) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  ");		
		hql.append("where r.statusIntegracao in (:status)");		
		hql.append("  and r.dataCheckinPrevisto>=:dataInicial ");
		hql.append("  and r.dataCheckinPrevisto<=:dataFinal ");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("status", status);
		query.setParameter("dataInicial", DateUtil.getDataSomada(timeServiceMySql.getDatabaseTimestamp(),1));
		query.setParameter("dataFinal", DateUtil.getDataSomada(timeServiceMySql.getDatabaseTimestamp(),antecedencia.intValue()+1));	
		
		List<Reserva> reservas = query.getResultList();
		
		//Altera e-mail para testes
		Parametrizacao parametrizacaoEmailTeste = parametrizacaoRepo
				.getPorTipo(Parametrizacao.EMAIL_TESTES);	
		if (parametrizacaoEmailTeste != null && !parametrizacaoEmailTeste.getDescricao().equals("")) {
			for (Reserva reserva : reservas) {
				reserva.setEmail(parametrizacaoEmailTeste.getDescricao());
			}
		}
		
		
		return reservas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reserva> getReservasParaExportacaoCM() {
		
		List<StatusIntegracaoFNRH> status = new ArrayList<StatusIntegracaoFNRH>();
		status.add(StatusIntegracaoFNRH.PRE);
		status.add(StatusIntegracaoFNRH.DIG);
		
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  				   ");
		//hql.append("where r.statusIntegracao=:statusIntegracao ");
		hql.append("where r.statusIntegracao in (:status) ");
		hql.append("order by r.id							   ");	
		Query query = emMySql.createQuery(hql.toString());
		//query.setParameter("statusIntegracao", StatusIntegracaoFNRH.DIG);	
		query.setParameter("status", status);		
		List<Reserva> reservas = query.getResultList();
		return reservas;
	}
	
	/**
	 * Lista de fnrhs por status da integração da reserva e período previsto de checkin
	 * @param status
	 * @param dataInicial
	 * @param dataFinal
	 * @return List<Reserva>
	 */
	@SuppressWarnings("unchecked")
	public List<ListaReserva> getReservasIntegracaoPorStatusPeriodoCheckin(Hotel hotel,StatusIntegracaoFNRH status,Date dataInicial,Date dataFinal,String numeroReserva,List<StatusIntegracaoFNRH> statusPossiveis) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r	  				  ");		
		hql.append("where r.dataCheckinPrevisto between :dataInicial and :dataFinal ");
		if(status!=null){
			hql.append("  and r.statusIntegracao=:status ");	
		}else{
			hql.append("  and r.statusIntegracao in (:status) ");	
		}
		hql.append("  and r.hotel=:hotel ");
		if(StringHelper.isNotEmpty(numeroReserva)){
		hql.append("  and r.numReservaErp=:reservaNumero ");
		}
		
		hql.append("order by r.dataCheckinPrevisto desc				  ");	
		Query query = emMySql.createQuery(hql.toString());
		if(status!=null){
			query.setParameter("status", status);	
		}else{
			query.setParameter("status", statusPossiveis);	
		}
		query.setParameter("dataInicial", dataInicial);	
		query.setParameter("dataFinal", dataFinal);
		query.setParameter("hotel", hotel);
		if(StringHelper.isNotEmpty(numeroReserva)){
			query.setParameter("reservaNumero", Long.parseLong(numeroReserva));	
		}
				
		List<Reserva> reservas = query.getResultList();	
		List<ListaReserva> listReservas = new ArrayList<ListaReserva>();
		for(Reserva r : reservas){
			ListaReserva lr = new ListaReserva();
			lr.setReserva(r);
			lr.setPermiteEdicao(false);
			if(r.getStatusIntegracao().equals(StatusIntegracaoFNRH.HSE) || r.getStatusIntegracao().equals(StatusIntegracaoFNRH.ERM)
			|| r.getStatusIntegracao().equals(StatusIntegracaoFNRH.EIV)){
				lr.setPermiteEdicao(true);
			}
			
			listReservas.add(lr);				
		}
		return listReservas;
	}
	
	@SuppressWarnings("unchecked")
	public Reserva getPorUUID(String uuid) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  ");
		hql.append("where r.uuid=:uuid ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("uuid", uuid);
		
		List<Reserva> reserva = query.getResultList();
		if(reserva.size()>0){
			return (Reserva) reserva.get(0);
		}else{
			return null;
		}
		
	}

	
	public void persist(Reserva reserva) {		
		emMySql.persist(reserva);
	}
	
	public void merge(Reserva reserva){
		emMySql.merge(reserva);
	}


	@SuppressWarnings("unchecked")
	public List<Reserva> getReservasParaExportacaoCMVacation() {
		StringBuffer hql = new StringBuffer();
		hql.append("from Reserva r 					  				   ");
		hql.append("where r.statusIntegracao=:statusIntegracao ");
		hql.append("and r.origemCod = :vacation ");
		hql.append("order by r.id							   ");	
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("statusIntegracao", StatusIntegracaoFNRH.MAI);	
		query.setParameter("vacation", "VC");	
		List<Reserva> reservas = query.getResultList();
		return reservas;
	}
	
}
