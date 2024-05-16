package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Estado;
import br.com.padrao.base.EMMySql;


/**
 * Repositório da tabela estado da base cep (CEP.LOG_FAIXA_UF)
 * @author Leste TI
 *
 */
@Name("estadoRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class EstadoRepo implements Serializable {
	
	@In
	private EMMySql emMySql;

	
	private static final long serialVersionUID = -1014764652517299103L;
	
	@SuppressWarnings("unchecked")
	public List<Estado> getAllOrderSigla() {
		return emMySql.createNamedQuery("estado.getAllOrderSigla").getResultList();
	}

}
