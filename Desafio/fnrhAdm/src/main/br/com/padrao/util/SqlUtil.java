package br.com.padrao.util;
/**
 * Classe utilitária para consultas sql
 * @author Sabrina
 * 
 */
public class SqlUtil {
	
	protected SqlUtil() {
		
	}
	/**
	 * Retorna string a ser adicionada no sql para busca fonética.
	 * @param campo
	 * @param operador
	 * 			 
	 * @return String
	 */
	public static String buscaFonetica(String campo, String operador) {
		return "upper(translate(" + campo 
		+ ",'ÃÕÜÂÊÎÔÛÀÇÁÉÍÓÍÚ','AOUAEIOUACAEIOIU')) " + operador
		+ " upper(translate(?,'ÃÕÜÂÊÎÔÛÀÇÁÉÍÓÍÚ','AOUAEIOUACAEIOIU'))";
	}
}
