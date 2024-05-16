package br.com.padrao.util;

import javax.naming.Context;
import javax.naming.InitialContext;


public class TomcatUtil {

	public static String obterValorPorChave(String chave) {
		try {
			Context ctx = new InitialContext ();
			Context envCtx = (Context) ctx.lookup("java:comp/env");			
			return (String) envCtx.lookup(chave);			
		} catch (Exception e) {
			return null;
		}
	}
	
/*	public static String obterValorPorChave(String chave) {
		try {			
			//return ""+ TomcatUtil.obterValorPorChave("wsAddress") + "";
			return "172.16.1.154";
		} catch (Exception e) {
			return null;
		}
	}*/

}