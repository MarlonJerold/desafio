package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.beachpark.fnrhAdm.model.Hotel;
import br.com.beachpark.fnrhAdm.model.Reserva;
import br.com.beachpark.fnrhAdm.model.StatusIntegracaoFNRH;
import br.com.padrao.base.EMMySqlExterna;
import br.com.padrao.repo.TimeServiceMySql;


/**
 * Repositório de operações a serem realizadas no schema BaseFnrhExter
 * @author LesteTI
 */
@Name("baseFnrhExternaRepo")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class BaseFnrhExternaRepo implements Serializable {

	private static final long serialVersionUID = -1027122438905681993L;
	
	@In
	private EMMySqlExterna emMySqlExterna;
	
	@In
	private TimeServiceMySql timeServiceMySql;	
	
	@In
	private HotelRepo hotelRepo;
	
	@In
	private GeneroRepo generoRepo;
	
	@In
	private MotivoViagemRepo motivoViagemRepo;
	
	@In
	private MeioTransporteRepo meioTransporteRepo;
	
	@In
	private TipoDocumentoRepo tipoDocumentoRepo;
		
	@SuppressWarnings("unchecked")
	public List<Reserva> getReservasExternasParaImportacao()
			throws Exception {
		//String data =null;	
		List<StatusIntegracaoFNRH> status = new ArrayList<StatusIntegracaoFNRH>();
		status.add(StatusIntegracaoFNRH.PRE);
		status.add(StatusIntegracaoFNRH.DIG);
		
		try {
			
			StringBuffer hql = new StringBuffer();
			hql.append("from Reserva r 					  	  ");
			hql.append("where r.statusIntegracao in (:status) ");
			hql.append("  and r.dataCheckinPrevisto>(:data)   ");
			hql.append("order by r.numReservaErp			  ");	
			Query query = emMySqlExterna.createQuery(hql.toString());
			
			query.setParameter("status", status);		
			query.setParameter("data", timeServiceMySql.getDatabaseTimestamp());
			
			List<Reserva> reservasASeremImportadas = query.getResultList();
			return reservasASeremImportadas;	
			
			
		} catch (Exception e) {
			System.out.print(e.getStackTrace());
			throw new SQLException(
					"Problema na consulta <getReservasExternasParaImportacao>. Detalhes :"
							+ e.getMessage());		
		}		
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Lista as fnrhs digitadas pelos hóspedes aptas a serem importadas para o cm 	 *
	 * @return List<Fnrh>
	 */
	public List<Fnrh> getFnrhsPorReserva(Reserva r) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Fnrh f 					  				   ");
		hql.append("where f.reserva.id=:reservaId ");
		hql.append("order by f.id							   ");	
		Query query = emMySqlExterna.createQuery(hql.toString());
		query.setParameter("reservaId",r.getId());	
		
		List<Fnrh> fnrhs = query.getResultList();
		return fnrhs;
	}
	
	/**
	 * Método que extrai dados do resultset e converte em Reserva
	 * @param  dadosReserva Array de objetos com dados da view 
	 * @return ReservaCMFNRH
	 */
	public Reserva setaDadosReserva(Object[] dadosReserva) {
		Reserva reserva = new Reserva();		
		
		Hotel hotel = hotelRepo.getPorId(Long.valueOf(dadosReserva[1].toString()));
		
		reserva.setId(Long.valueOf(dadosReserva[0].toString()));
		reserva.setHotel(hotel);
		reserva.setEmail(dadosReserva[2].toString());
		reserva.setUuid(dadosReserva[3].toString());
		reserva.setDataCheckinPrevisto((java.sql.Date) dadosReserva[4]);
		reserva.setDataCheckoutPrevisto((java.sql.Date) dadosReserva[5]);
		reserva.setStatusIntegracao(StatusIntegracaoFNRH.valueOf(dadosReserva[6].toString()));
		reserva.setDataInsercao((java.util.Date) dadosReserva[7]);
		if(dadosReserva[8]!=null){
			reserva.setDataUpdate((java.util.Date) dadosReserva[8]);
		}else{
			reserva.setDataUpdate(null);
		}
		
		if(dadosReserva[9]!=null){
			reserva.setLocalizador(Long.valueOf(dadosReserva[9].toString()));
		}else{
			reserva.setLocalizador(null);
		}
		

		reserva.setIdReservaErp(Long.valueOf(dadosReserva[10].toString()));
		reserva.setNumReservaErp(Long.valueOf(dadosReserva[11].toString()));	
		return reserva;
		
	}	

}
