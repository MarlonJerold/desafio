package br.com.padrao.security;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

/**
 * Auxiliar na montagem dos menus e restrição de acesso. Retorna as propriedades
 * disabled e rendered para os menus. Retorna a propriedade restrict para os
 * Controllers.
 * 
 * @author Leonardo
 * 
 */
@Name("acessoService")
@Scope(ScopeType.EVENT)
@AutoCreate
public class AcessoService {

	/**
	 * Retorna se o menu deve ser desabilitado.
	 * 
	 * @param role
	 * @return
	 */
	public boolean disabled(String role) {
		return !Identity.instance().hasRole(role);
	}

	/**
	 * Retorna se o acesso ao controller deve ser negado.
	 * 
	 * @param role
	 * @return
	 */
	public boolean restrict(String role) {
		return Identity.instance().hasRole(role);
	}

}
