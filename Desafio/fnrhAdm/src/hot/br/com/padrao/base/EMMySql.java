package br.com.padrao.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("emMySql")
@AutoCreate
@Scope(ScopeType.CONVERSATION)    
public class EMMySql implements Serializable {

	private static final long serialVersionUID = 6054438149048737701L;

	@In(value = "entityManager")
	private EntityManager entityManager;

	public Query createNamedQuery(String arg0) {
		return entityManager.createNamedQuery(arg0);
	}
	
	public Query createNativeQuery(String arg0) {
		return entityManager.createNativeQuery(arg0);
	}
	
	public void persist(Object arg0) {		
		entityManager.persist(arg0);
		entityManager.flush();		
	}
	
	public void persist(List<Object> arg0) {		
		entityManager.persist(arg0);
		entityManager.flush();		
	}

	public void remove(Object arg0) {
		entityManager.remove(arg0);
		entityManager.flush();
	}
	
	public void merge(Object arg0) {
		entityManager.merge(arg0);
		entityManager.flush();
	}

	public Query createQuery(String arg0) {
		return entityManager.createQuery(arg0);
	}
	
	public <T> T find(Class<T> arg0, Object arg1) {
		return entityManager.find(arg0, arg1);
	}
}
