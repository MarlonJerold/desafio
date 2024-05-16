package br.com.padrao.util;

import java.util.UUID;

public class GeradorUtil {
	
	public static String geraCodidoUUID(){
		return String.valueOf(UUID.randomUUID());
	}

}
