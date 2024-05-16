package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Genero;
import br.com.padrao.base.EMMySql;


@Name("generoRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class GeneroRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5781428254231602837L;
	
	@In
	private EMMySql emMySql;	           
	
	
	public Genero getPorId(Long id) {
		return emMySql.find(Genero.class, id);
	}
	
	public void persist(Genero genero) {
		emMySql.persist(genero);
	}
	
	@SuppressWarnings("unchecked")
	public Genero getPorCodigo(String cdGenero,String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Genero g ");
		hql.append("where g.codigo=:cdGenero ");
		hql.append("  and g.tipoLingua=:tipoLingua ");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("cdGenero", cdGenero);
		query.setParameter("tipoLingua", tipoLingua);
		
		
		List<Genero> generos = query.getResultList();
		if(generos.size()>0){
			return (Genero) generos.get(0);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Genero> getAllPorLingua(String tipoLingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Genero g ");
		hql.append("where g.tipoLingua=:tipoLingua ");
		hql.append("order by g.descricao ");

		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("tipoLingua", tipoLingua);

		return query.getResultList();
	}


}
