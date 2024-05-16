package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import br.com.beachpark.fnrhAdm.model.Log;
import br.com.padrao.base.EMMySql;

@Name("logRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class LogRepo implements Serializable {

	private static final long serialVersionUID = -4604131548926169484L;
	
	@In
	private EMMySql emMySql;	 
	
	public Log getPorId(Long id) {
		return emMySql.find(Log.class, id);
	}
	
	public void persist(Log log) {
		if (log.getId() == null) {
			emMySql.persist(log);
		} else {
			emMySql.merge(log);
		}
	}

}
