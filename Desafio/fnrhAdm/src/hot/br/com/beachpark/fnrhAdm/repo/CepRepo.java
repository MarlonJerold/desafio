package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Cep;
import br.com.padrao.base.EMMySql;

/**
 * Repositório da tabela cep da base cep (CEP.ECT_CEP)
 * @author Leste TI
 *
 */
@Name("cepRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class CepRepo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -171951219938247662L;
	
	@In
	private EMMySql emMySql;
	
	public Cep getPorId(String cep) {
		return emMySql.find(Cep.class, cep);
	}

}
