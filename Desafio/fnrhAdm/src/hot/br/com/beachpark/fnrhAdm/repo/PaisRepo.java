package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Pais;
import br.com.padrao.base.EMMySql;

/**
 * Repositório da tabela pais da base cep (CEP.ECT_PAIS)
 * @author Leste TI
 *
 */
@Name("paisRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class PaisRepo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5638238355313157108L;
	@In
	private EMMySql emMySql;
	
	
	@SuppressWarnings("unchecked")
	public List<Pais> getAllOrderNome() {
		return emMySql.createNamedQuery("pais.getAllOrderNome").getResultList();
	}
	
	/**
	 * Busca de pais por sigla
	 * @param sigla Corresponde ao código ISO do pais
	 * @return List<Pais> @see Pais
	 */	
	@SuppressWarnings("unchecked")
	public List<Pais> getPorSigla(String sigla) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Pais p ");
		hql.append("where p.sigla=:sigla ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("sigla", sigla);
		
		List<Pais> paises =(List<Pais>) query.getResultList();
		return paises;
		
	}
	
	/**
	 * Verifica se o pais informado faz parte do grupo do mercosul.
	 * O campo PAI_MERCOSUL quando possui o valor 1 identifica que este pais pertence ao referido grupo
	 * @param sigla Sigla do pais de acordo com o código ISO
	 * @return boolean 
	 */	
	@SuppressWarnings("unchecked")
	public boolean isPaisMercosul(String sigla) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Pais p ");
		hql.append("where p.sigla=:sigla ");
		hql.append("  and p.pertenceAoMercosul=true ");
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("sigla", sigla);
		
		List<Pais> paises =(List<Pais>) query.getResultList();
		if(paises.size()>0){
			return true;
		}else{
			return false;
		}	
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pais> getAllOrderNome(String lingua) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Pais p ");
		hql.append("order by :nome ");		
		Query query = emMySql.createQuery(hql.toString());
		if(lingua.equals("pt")){
			query.setParameter("nome", "nomePortugues");
		}else if(lingua.equals("es")){
			query.setParameter("nome", "nomeEspanhol");
		}else{
			query.setParameter("nome", "nomeIngles");
		}		
		
		List<Pais> paises =(List<Pais>) query.getResultList();
		return paises;
		
	}

}
