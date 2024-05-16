package br.com.padrao.util;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * Utilitário para internacionalizar o rich:calendar.
 * 
 * @author leonardo
 *
 */
@Name("richCalendarUtil")
@Scope(ScopeType.APPLICATION)
public class RichCalendarUtil {

	private static final String[] WEEK_DAY_LABELS = { "Domingo", "Segunda",
			"Terça", "Quarta", "Quinta", "Sexta", "Sábado" };

	private static final String[] WEEK_DAY_LABELS_SHORT = { "Dom", "Seg", "Ter",
			"Qua", "Qui", "Sex", "Sáb" };

	private static final String[] MONTH_LABELS = { "Janeiro", "Fevereiro",
			"Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
			"Outubro", "Novembro", "Dezembro" };

	private static final String[] MONTH_LABELS_SHORT = { "Jan", "Fev", "Mar",
			"Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };

	public String[] getWeekDayLabels() {
		return WEEK_DAY_LABELS;
	}

	public String[] getWeekDayLabelsShort() {
		return WEEK_DAY_LABELS_SHORT;
	}

	public String[] getMonthLabels() {
		return MONTH_LABELS;
	}

	public String[] getMonthLabelsShort() {
		return MONTH_LABELS_SHORT;
	}

}
