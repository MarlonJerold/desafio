package br.com.padrao.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jboss.seam.annotations.security.management.RoleName;

/**
 * Roles do usuário para acesso ao sistema. O usuário pode ter roles diferentes
 * de acordo com a empresa.
 * 
 * @author leonardo
 * 
 */
@Entity
@Table(name = "BPDW.T_FUN")
public class Role {

	@Id
	@Column(name = "IS_FUN")
	private Long id;

	@RoleName
	@Column(name = "NM_ROLE")
	private String rolename;

	@Column(name = "NM_FUN")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
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
		Role other = (Role) obj;
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
