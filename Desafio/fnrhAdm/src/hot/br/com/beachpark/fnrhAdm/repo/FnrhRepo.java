package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Fnrh;
import br.com.padrao.base.EMMySql;

/**
 * 
 * Repositório da tabela de FNRH 
 * @author LesteTI
 *
 */
@Name("fnrhRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class FnrhRepo implements Serializable {

	private static final long serialVersionUID = -8867102593331228552L;
	
	@In
	private EMMySql emMySql;
	
	public Fnrh getPorId(Long id) {
		return emMySql.find(Fnrh.class, id);
	}
	
	public void persist(Fnrh fnrh) {
		emMySql.persist(fnrh);
	}
	
	public void persist(List<Fnrh> fnrhs) {
		emMySql.persist(fnrhs);
	}
	
	public void merge(Fnrh fnrh){
		emMySql.merge(fnrh);
	}

	
	@SuppressWarnings("unchecked")
	/**
	 * Lista as fnrhs digitadas pelos hóspedes aptas a serem importadas para o cm 	 *
	 * @return List<Fnrh>
	 */
	public List<Fnrh> getFnrhsPorReserva(Long reservaId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select f from Fnrh f left join fetch f.fnrhDocumentoList   ");
		hql.append("where f.reserva.id=:reservaId ");
		hql.append("order by f.id							   ");	
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("reservaId", reservaId);	
		
		List<Fnrh> fnrhs = query.getResultList();
		return fnrhs;
	}
	
	//@SuppressWarnings("unchecked")
	/**
	 * Lista as fnrhs digitadas pelos hóspedes aptas a serem importadas para o cm 	 *
	 * @return List<Fnrh>
	 */
	/*public List<Fnrh> getFnrhsPorReserva(Reserva r) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Fnrh f 					  				   ");
		hql.append("where f.reserva.id=:reservaId ");
		hql.append("order by f.id							   ");	
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("reservaId",r.getId());	
		
		List<Fnrh> fnrhs = query.getResultList();
		return fnrhs;
	}
	*/

}
