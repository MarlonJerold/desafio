package br.com.padrao.security;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.management.UserPassword;
import org.jboss.seam.annotations.security.management.UserPrincipal;
import org.jboss.seam.annotations.security.management.UserRoles;

/**
 * Usuário do sistema. Como componente do Seam tem escopo de sessão e é
 * instanciado por EmpresaService.authenticate()
 * 
 * Contém annotations relativas ao IdentityStore. Controle de
 * autenticação/autorização provido pelo Seam.
 * 
 * @author leonardo
 * 
 */
@Name("usuario")
@Scope(ScopeType.SESSION)
@Entity
@Table(name = "BPDW.T_USU")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IS_USU")
	private Long id;
	
	@UserPrincipal
	@Column(name = "NM_USU_LOG")
	private String login;

	@UserPassword(hash = "md5")
	@Column(name = "NM_USU_SEN")
	private String senha;
	
	@Column(name = "NM_USU")
	@NotNull
	private String nome;
	
	@UserRoles
	@OneToMany
	@JoinTable(name = "BPDW.T_USF", joinColumns = @JoinColumn(name = "IS_USU"), 
			inverseJoinColumns = @JoinColumn(name = "IS_FUN"))
	private List<Role> roleList;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (id != null) {
			result += id.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
