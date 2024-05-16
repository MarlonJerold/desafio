package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Parametrizacao;
import br.com.padrao.base.EMMySql;

@Name("parametrizacaoRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class ParametrizacaoRepo implements Serializable {

	
	private static final long serialVersionUID = -5972937837491935832L;
	
	@In
	private EMMySql emMySql;	 
	
	public Parametrizacao getPorId(Long id) {
		return emMySql.find(Parametrizacao.class, id);
	}
	
	public void persist(Parametrizacao parametrizacao) {
		emMySql.persist(parametrizacao);
	}
	
	@SuppressWarnings("unchecked")
	public Parametrizacao getPorTipo(String tipo) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Parametrizacao p 					  ");
		hql.append("where upper(p.tipo)=:tipo ");
		hql.append("and p.inativo=false ");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("tipo", tipo);
		
		List<Parametrizacao> parametrizacoes = query.getResultList();
		if(parametrizacoes.size()>0){
			return (Parametrizacao) parametrizacoes.get(0);
		}else{
			return null;
		}
		
	}

}
