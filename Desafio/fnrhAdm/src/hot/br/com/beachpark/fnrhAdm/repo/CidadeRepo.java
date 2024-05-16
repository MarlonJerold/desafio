package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Cidade;
import br.com.padrao.base.EMMySql;

/**
 * Repositório da tabela cidade da base cep (CEP.LOG_LOCALIDADE)
 * @author Leste TI
 *
 */
@Name("cidadeRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)  

public class CidadeRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3772647048401963552L;
	
	@In
	private EMMySql emMySql;
	
	
	/**
	 * Busca as cidades de um determinado estado
	 * @param sigla Sigla do estado
	 * @return List<Cidade> @see Cidade
	 */	
	@SuppressWarnings("unchecked")
	public List<Cidade> getPorSiglaEstado(String sigla) {
		StringBuffer hql = new StringBuffer();
		hql.append("from Cidade c ");
		hql.append("where c.siglaEstado=:sigla ");
		hql.append("  and c.codigoIBGE is not null ");
		hql.append("order by c.descricao ");		
		Query query = emMySql.createQuery(hql.toString());
		query.setParameter("sigla", sigla);
		
		List<Cidade> cidades =(List<Cidade>) query.getResultList();
		return cidades;	
	}


}
