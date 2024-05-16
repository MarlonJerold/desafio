package br.com.padrao.util;

import java.util.Random;

/**
 * Criptografa e descriptografa strings de acordo com os algoritmos originais do
 * sistema de vendas.
 * 
 * @author leonardo.brasileiro
 * 
 */
// CHECKSTYLE:OFF
public class Cript {
	
	public static final int LENGTH_NUMERO_BILHETE = 14;
	
	public static final int LENGTH_CODIGO_DIGITACAO = 10;

	private final static byte[] chave = { 122, 121, 120, 119, 118, 117, 116,
			115, 114, 113, 112, 111, 110, 109 };

	private final static Random random = new Random();

	/**
	 * Criptografa e descriptografa uma string.
	 * 
	 * @param senha
	 * @return String
	 */
	public static String cript(String senha) {
		if (senha == null)
			return "";

		senha = senha.toUpperCase();
		if (senha.length() > 14)
			senha = senha.substring(0, 14);
		byte[] bytesSenha = senha.getBytes();
		byte[] bytesCript = new byte[bytesSenha.length];
		for (int n = 0; n < bytesSenha.length; n++) {
			int i = (int) chave[n] ^ (int) bytesSenha[n];
			bytesCript[n] = (byte) i;
		}
		String senhaCript = new String(bytesCript);
		return senhaCript;
	}

	/**
	 * Gera uma string com uma quantidade de caracteres numéricos pré definidos.
	 * 
	 * @param length
	 *            quantidade de caracteres.
	 * @return string de caracteres numéricos.
	 */
	public static String geraNumeroAleatorio(int length) {
		StringBuffer numero = new StringBuffer();
		for (int i = 0; i < length; i++) {
			numero.append(random.nextInt(10));
		}

		return numero.toString();
	}

}
// CHECKSTYLE:ON