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


@Name("emMySqlExterna")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class EMMySqlExterna implements Serializable {

	private static final long serialVersionUID = -5725351285932492055L;
	
	@In(value = "entityManagerMySqlExterna")
	private EntityManager entityManagerMySqlExterna;
	
	public Query createNamedQuery(String arg0) {
		return entityManagerMySqlExterna.createNamedQuery(arg0);
	}

	public Query createNativeQuery(String arg0, String arg1) {
		return entityManagerMySqlExterna.createNativeQuery(arg0, arg1);
	}

	public Query createNativeQuery(String arg0) {
		return entityManagerMySqlExterna.createNativeQuery(arg0);
	}

	public Query createQuery(String arg0) {
		return entityManagerMySqlExterna.createQuery(arg0);
	}

	public <T> T find(Class<T> arg0, Object arg1) {
		return entityManagerMySqlExterna.find(arg0, arg1);
	}

	public void flush() {
		entityManagerMySqlExterna.flush();
	}

	public <T> T merge(T arg0) {
		return entityManagerMySqlExterna.merge(arg0);
	}


	public void persist(Object arg0) {
		entityManagerMySqlExterna.persist(arg0);
		entityManagerMySqlExterna.flush();
	}

	public void remove(Object arg0) {
		entityManagerMySqlExterna.remove(arg0);
		entityManagerMySqlExterna.flush();
	}
	
	public EntityTransaction getTransaction(){	
		return (EntityTransaction) entityManagerMySqlExterna.getTransaction();
	}


}
