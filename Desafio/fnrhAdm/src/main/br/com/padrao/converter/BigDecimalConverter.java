package br.com.padrao.converter;

import java.math.BigDecimal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
/***
 * 
 * @author IsraelCaxilé
 *
 */

public class BigDecimalConverter extends NumberConverter {
	
	public static final String CONVERTER_ID = "BigDecimalConverter";
	public static final int SEIS = 6;
	
	public BigDecimalConverter() {
		super();
		setMaxFractionDigits(SEIS);
		setMinFractionDigits(SEIS);
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
