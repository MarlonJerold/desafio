package br.com.padrao.util;

import org.hibernate.util.StringHelper;

public class CepUtil {
	
	 public static boolean validaCep(String cep)
     {
       /*  if (cep.Length == 8)
         {
             cep = cep.Substring(0, 5) + "-" + cep.Substring(5, 3);
             //txt.Text = cep;
         }
         return System.Text.RegularExpressions.Regex.IsMatch(cep, ("[0-9]{5}-[0-9]{3}"));*/
		 return true;
     }
	 
	 public static String retiraCaracteresCep(String cep)
     {      
		 if(StringHelper.isNotEmpty(cep)){
			 return cep.replace("-", "");
		 }
		 return null;		
     }

}
