package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.CidadeCM;
import br.com.padrao.base.EM;

@Name("cidadeCMRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class CidadeCMRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6921418800290516814L;
	
	@In
	private EM em;	
	
	public CidadeCM getPorId(Long id) {
		return em.find(CidadeCM.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public CidadeCM getPorCodigoIBGE(String cidadeCodIBGE) {
		StringBuffer hql = new StringBuffer();
		hql.append("from CidadeCM c 					 \n");
		hql.append("where c.codIBGE=:codigoIbge	\n");			
		hql.append("order by c.id  ");
		Query query = em.createQuery(hql.toString());
		query.setParameter("codigoIbge",cidadeCodIBGE);	
		
		List<CidadeCM> cidades = query.getResultList();
		if(cidades.size()>0){
			return (CidadeCM) cidades.get(0);
		}else{
			return null;
		}
		
	}


}
