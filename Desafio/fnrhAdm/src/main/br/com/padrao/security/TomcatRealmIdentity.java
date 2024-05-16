package br.com.padrao.security;

import java.security.Principal;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.security.Identity;
import org.jboss.seam.web.IdentityRequestWrapper;

@Name("org.jboss.seam.security.identity")
@Scope(ScopeType.SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
public class TomcatRealmIdentity extends Identity {

	private static final long serialVersionUID = 1L;
	
	public void create() {
		Identity.setSecurityEnabled(false);
		super.create();
	}

	private HttpServletRequest getHttpServletRequest() {
		IdentityRequestWrapper identityRequestWrapper = (IdentityRequestWrapper) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpServletRequest httpServletRequest = (HttpServletRequest) identityRequestWrapper
				.getRequest();

		return httpServletRequest;
	}

	@Override
	public Principal getPrincipal() {
		Principal currentUser = getHttpServletRequest().getUserPrincipal();
		return currentUser;
	}

	@Override
	public boolean hasRole(String arg0) {
		return getHttpServletRequest().isUserInRole(arg0);
	}

	@Override
	public boolean isLoggedIn() {
		boolean b = getPrincipal() != null;
		return b;
	}

	public String logoutTomcat() {
		super.logout();
		
		return "/";
	}

}
