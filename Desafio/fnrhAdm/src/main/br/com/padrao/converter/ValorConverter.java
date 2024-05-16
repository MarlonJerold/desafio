package br.com.padrao.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;

/**
 * Converte um Double ou Long fornecido pelo jsf 
 * para BigDecimal. Locale pt_BR
 * 
 */
public class ValorConverter extends NumberConverter {
	
	public static final String CONVERTER_ID = "ValorConverter";
	
	public ValorConverter() {
		super();
		setMaxFractionDigits(2);
		setMinFractionDigits(2);
	}
	
    @Override
	public Object getAsObject(FacesContext context, 
			UIComponent component, String string) {
        Object value = super.getAsObject(context, component, string); 
        if (value instanceof Long) {
        	return BigDecimal.valueOf((Long) value);
        }
        if (value instanceof Double) {
        	return BigDecimal.valueOf((Double) value);
        }
        return value;
    }
	
}
