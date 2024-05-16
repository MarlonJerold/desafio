package br.com.padrao.util;

import java.util.Date;

public class DataSqlUtil {
	
	private static Long FORMATO_DD_MM_YYYY = 103L;	
	
	public static String convertStringSmalldatetime(Date date){
		return "convert(smalldatetime,"+date+","+FORMATO_DD_MM_YYYY+")";
	}
	
	
	

}
