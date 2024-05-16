package br.com.padrao.base;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;


/**
 * Wrapper para as funcionalidades contidas em EntityManager voltadas para o
 * contexto do sistema. Inclui uma chamada a session para utilização em acesso a
 * stored procedures.
 * 
 * @author leonardo.brasileiro
 * 
 */
@Name("em")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class EM implements Serializable {

	private static final long serialVersionUID = -2891977014477516445L;
	
	@In(value="entityManagerOracle")
	private EntityManager entityManagerOracle;

	public Query createNamedQuery(String arg0) {
		return entityManagerOracle.createNamedQuery(arg0);
	}

	public Query createNativeQuery(String arg0, String arg1) {
		return entityManagerOracle.createNativeQuery(arg0, arg1);
	}

	public Query createNativeQuery(String arg0) {
		return entityManagerOracle.createNativeQuery(arg0);
	}

	public Query createQuery(String arg0) {
		return entityManagerOracle.createQuery(arg0);
	}

	public <T> T find(Class<T> arg0, Object arg1) {
		return entityManagerOracle.find(arg0, arg1);
	}

	public void flush() {
		entityManagerOracle.flush();
	}

	public <T> T merge(T arg0) {
		return entityManagerOracle.merge(arg0);
	}


	public void persist(Object arg0) {
		entityManagerOracle.persist(arg0);
		entityManagerOracle.flush();
	}

	public void remove(Object arg0) {
		entityManagerOracle.remove(arg0);
		entityManagerOracle.flush();
	}
	
	public EntityTransaction getTransaction(){	
		return (EntityTransaction) entityManagerOracle.getTransaction();
	}
	
	
		
	
}
