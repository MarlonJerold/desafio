package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.PaisCM;
import br.com.padrao.base.EM;

@Name("paisCMRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class PaisCMRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7987362418420245111L;
	
	@In
	private EM em;	
	
	public PaisCM getPorId(Long id) {
		return em.find(PaisCM.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public PaisCM getPorCodigoISO(String codISO) {
		StringBuffer hql = new StringBuffer();
		hql.append("from PaisCM p 					  ");
		hql.append("where p.codigoISO=:codISO ");
		hql.append("order by p.id  ");
		Query query = em.createQuery(hql.toString());
		query.setParameter("codISO", codISO);
		
		List<PaisCM> paises = query.getResultList();
		if(paises.size()>0){
			return (PaisCM) paises.get(0);
		}else{
			return null;
		}
		
	}	
	

}
