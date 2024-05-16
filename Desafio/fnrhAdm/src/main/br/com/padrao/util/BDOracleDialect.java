package br.com.padrao.util;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.Oracle10gDialect;

public class BDOracleDialect extends Oracle10gDialect {
	
	public BDOracleDialect(){
		super();
		registerColumnType(Types.CHAR,"char($1)");
		registerHibernateType(Types.CHAR,Hibernate.STRING.getName());
	}

}
