package br.com.padrao.repo;

import java.io.Serializable;
import java.sql.Timestamp;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.padrao.base.EM;

/**
 * Serviço para obter a data e hora do banco de dados.
 * 
 * @author leonardo
 *
 */
@Name("timeService")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class TimeService implements Serializable {  

	private static final long serialVersionUID = 1L;
	
	@In
	private EM em;

	/**
	 * Obtém data e hora do banco de dados.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getDatabaseTimestamp() {
		Timestamp timestamp = (Timestamp) em.createNamedQuery(
				"currentTimestamp").getSingleResult();
		timestamp.setNanos(0);

		return timestamp;
	}	

}