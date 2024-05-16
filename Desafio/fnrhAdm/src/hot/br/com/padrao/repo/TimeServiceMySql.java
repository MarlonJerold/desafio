package br.com.padrao.repo;
import java.io.Serializable;
import java.sql.Timestamp;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.padrao.base.EMMySql;

/**
 * Serviço para obter a data e hora do banco de dados.
 * 
 * @author leonardo
 *
 */
@Name("timeServiceMySql")
@Scope(ScopeType.CONVERSATION)
@AutoCreate
public class TimeServiceMySql implements Serializable {  

	private static final long serialVersionUID = 1L;
	
	@In
	private EMMySql emMySql;

	/**
	 * Obtém data e hora do banco de dados.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getDatabaseTimestamp() {
		Timestamp timestamp = (Timestamp) emMySql.createNativeQuery("select now()").getSingleResult();
		timestamp.setNanos(0);

		return timestamp;
	}	

}

