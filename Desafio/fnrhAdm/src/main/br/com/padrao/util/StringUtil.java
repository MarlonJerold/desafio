package br.com.padrao.util;

import org.hibernate.util.StringHelper;

public class StringUtil {

	public static boolean isNullOrEmpty(String s) {
		return (s == null || (s != null && s.equals("")));
	}

	public static String obtemString(String var, String substitui, int tamanho) {
		if (isNullOrEmpty(var)) {
			return substitui;
		} else {
			return StringHelper.truncate(var, tamanho);
		}
	}
	
	public static String reticaCaracteresEspeciais(String var,String caracter){
		return var.replace(caracter,"");
	}

}
