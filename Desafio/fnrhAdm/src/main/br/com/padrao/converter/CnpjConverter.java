package br.com.padrao.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
/**
 * Converte campo de Cnpj para ser mostrado formatado
 * Exemplo: No banco 07374998000133 mostrará 07.374.998/0001-33
 * @author Sabrina
 */
public class CnpjConverter extends NumberConverter {
	
	private DecimalFormat fmt = new DecimalFormat("00000000000000");
	
	public BigDecimal getAsObject(FacesContext context, UIComponent component,
			String value) {
		/*
		 * Converte CNPJ formatado para um n. sem pontos, traço e barra.
		 * Ex.: 07.374.998/0001-33 torna-se 07374998000133.
		 */
		String cnpj = value;
		if (value != null && !value.equals("")) {
			cnpj = value.replaceAll("\\.", "").replaceAll("\\-", "")
					.replaceAll("/", "");
			return new BigDecimal(cnpj);
		}
		return null;
	}
	//CHECKSTYLE:OFF
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		/*
		 * Converte CNPJ não formatado para um com pontos, traço e barra.
		 * Ex.: 07374998000133 torna-se 07.374.998/0001-33.
		 */
		String cnpj = fmt.format((BigDecimal) value);
		if (cnpj == null) {
			return "";
		}

		while (cnpj.length() < 14) {
			cnpj = "0" + cnpj;
		}

		cnpj = cnpj.substring(0, 2) + "."
				+ cnpj.substring(2, 5) + "."
				+ cnpj.substring(5, 8) + "/"
				+ cnpj.substring(8, 12) + "-"
				+ cnpj.substring(12, 14);
		return cnpj.trim();
	}
	//CHECKSTYLE:ON
}
