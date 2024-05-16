package br.com.beachpark.fnrhAdm.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.padrao.repo.TimeService;

/**
 * Artifício para obter a data a partir do Oracle.
 * 
 * @see TimeService
 * @author LesteTI
 *
 */
@Entity
@Table(name = "DUAL")
@NamedQuery(name = "currentTimestamp", 
		query = "select current_timestamp from Time")		
public class Time implements Serializable {

	private static final long serialVersionUID = 1062231705006014767L;
	
	@Id
	private String dummy;

	public String getDummy() {
		return dummy;
	}

	public void setDummy(String dummy) {
		this.dummy = dummy;
	}

}
