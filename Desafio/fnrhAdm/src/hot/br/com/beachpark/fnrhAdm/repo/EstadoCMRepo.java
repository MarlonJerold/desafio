package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.EstadoCM;
import br.com.beachpark.fnrhAdm.model.PaisCM;
import br.com.padrao.base.EM;

@Name("estadoCMRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class EstadoCMRepo implements Serializable {


	private static final long serialVersionUID = -1771199893820665323L;
	
	@In
	private EM em;	
	
	public EstadoCM getPorId(Long id) {
		return em.find(EstadoCM.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public EstadoCM getPorPaisCodigo(PaisCM pais,String estadoCod) {
		StringBuffer hql = new StringBuffer();
		hql.append("from EstadoCM e 							 \n");
		hql.append("where e.paisCM.id=:pais 					 \n");
	    if(pais.getCodigoISO().equals(PaisCM.COD_BRASIL)){
			hql.append("and trim(e.codigo)=:estadoCod					 \n");
		}else{
			hql.append("and upper(e.nome)=:estadoNome			 \n");			
		}
		hql.append("order by e.id                                \n");
		Query query = em.createQuery(hql.toString());
		query.setParameter("pais", pais.getId());

		if(pais.getCodigoISO().equals(PaisCM.COD_BRASIL)){
			query.setParameter("estadoCod",estadoCod.trim());		
		}else{
			query.setParameter("estadoNome", pais.getNome().toUpperCase());		
		}	
		
		List<EstadoCM> estados = query.getResultList();
		if(estados.size()>0){
			return (EstadoCM) estados.get(0);
		}else{
			return null;
		}
		
	}	
	

}
