package br.com.beachpark.fnrhAdm.repo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.security.Identity;

import br.com.padrao.base.EM;
import br.com.padrao.security.Usuario;
import br.com.padrao.util.StringUtil;

@Name("usuarioRepo")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class UsuarioRepo implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L; 
	
	@In
	private EM em;

	@In
	private Identity identity;

	@SuppressWarnings("unchecked")
	public List<Usuario> getAll() {
		return em.createNamedQuery("usuario.getAll").getResultList();
	}

	public Usuario getByID(String id) {
		return em.find(Usuario.class, Long.parseLong(id));
	}

	/**
	 * 
	 * @return Usario logado da sessão atual. <br>
	 *         User logged of current session.</br>
	 */
	public Usuario getUsuarioLogado() {
		String login = getLoginUsuarioLogado();	
		
		try{
			Usuario usuario = getPorLogin(login);
			return usuario;
		}catch (NoResultException e) {
			return null;
		}	
	}

	public String getLoginUsuarioLogado() {
		return identity.getPrincipal().getName();
	}

	public Usuario getPorLogin(String login) {
		StringBuffer hql = new StringBuffer();

		hql.append(" from Usuario u ");
		hql.append(" where u.login = :login ");

		Query query = em.createQuery(hql.toString());

		query.setParameter("login", login);

		return (Usuario) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosTemSolicitacao() {
		StringBuffer hql = new StringBuffer();
		hql.append(" select distinct u from Usuario u, ");
		hql.append(" Solicitacao s ");
		hql.append(" where s.operador.id = u.id ");

		Query query = em.createQuery(hql.toString());

		return (List<Usuario>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuarios(String nomeUsuario, String login) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from Usuario u ");
		hql.append(" where 1=1 ");

		if (StringUtil.isNullOrEmpty(nomeUsuario)
				&& StringUtil.isNullOrEmpty(login)) {
			return null;
		}

		if (!StringUtil.isNullOrEmpty(nomeUsuario)) {
			hql.append(" and upper(u.nome) like :nome ");
		}

		if (!StringUtil.isNullOrEmpty(login)) {
			hql.append(" and upper(u.login) like :login ");
		}

		Query query = em.createQuery(hql.toString());

		if (!StringUtil.isNullOrEmpty(nomeUsuario)) {
			query.setParameter("nome", "%" + nomeUsuario.toUpperCase() + "%");
		}

		if (!StringUtil.isNullOrEmpty(login)) {
			query.setParameter("login", "%" + login.toUpperCase() + "%");
		}

		return (List<Usuario>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosAcessos(String nomeUsuario, String login) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from Usuario u ");
		hql.append(" where 1=1 ");

		if (!StringUtil.isNullOrEmpty(nomeUsuario)) {
			hql.append(" and upper(u.nome) like :nome ");
		}

		if (!StringUtil.isNullOrEmpty(login)) {
			hql.append(" and upper(u.login) like :login ");
		}
		hql.append(" and u.acessos is not empty");
		hql.append(" order by u.nome ");
		Query query = em.createQuery(hql.toString());

		if (!StringUtil.isNullOrEmpty(nomeUsuario)) {
			query.setParameter("nome", "%" + nomeUsuario.toUpperCase() + "%");
		}

		if (!StringUtil.isNullOrEmpty(login)) {
			query.setParameter("login", "%" + login.toUpperCase() + "%");
		}
		
		return (List<Usuario>) query.getResultList();
	}

}
