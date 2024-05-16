package br.com.padrao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Classe utilitária para datas.
 * 
 * @author leonardo
 * 
 */
public class DateUtil {

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	//private static final Locale BRAZIL_LOCALE = new Locale("pt_BR");
	
	private static DateFormat dfCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static DateFormat dfProtheus = new SimpleDateFormat("yyyyMMdd");

	/**
	 * Formata a data para hh:mm
	 */
	private static DateFormat dfHoraMinuto = new SimpleDateFormat("HH:mm");

	protected DateUtil() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retorna o primeiro dia do mês e ano passados como parâmetro
	 * 
	 * @param mes
	 *            Mês de acordo com o campo Calendar.MONTH, sendo o inteiro 0
	 *            para Janeiro e 11 para Dezembro.
	 * @param ano
	 * @return Date
	 * @throws Exception
	 */

	public static Date formataDataInicial(int mes, int ano) {
		Calendar cal = new GregorianCalendar(ano, mes, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Retorna o último dia do mês e ano passados como parâmetro
	 * 
	 * @param mes
	 *            Mês de acordo com o campo Calendar.MONTH, sendo o inteiro 0
	 *            para Janeiro e 11 para Dezembro.
	 * @param ano
	 * @return Date
	 * @throws Exception
	 */
	public static Date formataDataFinal(int mes, int ano) {
		Calendar cal = new GregorianCalendar(ano, mes, 1, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * Retorna a quantidade de dias do mês da data passada.
	 * 
	 * @param data
	 * @return int
	 */
	public static int quantidadeDiasMes(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna uma data a partir do dia, mês e ano passados como parâmetros.
	 * 
	 * @param dia
	 * @param mes
	 *            Mês de acordo com o campo Calendar.MONTH, sendo o inteiro 0
	 *            para Janeiro e 11 para Dezembro.
	 * @param ano
	 * @return Date
	 * @throws ParseException
	 */
	public static Date formataData(int dia, int mes, int ano)
			throws ParseException {
		Calendar cal = new GregorianCalendar(ano, mes, dia, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Converte Date para String
	 * 
	 * @param data
	 * @return String
	 */
	public static String formataData(Date data) {
		return df.format(data);
	}
	
	public static String formataDataPt(Date data) {
		return dfProtheus.format(data);
	}
	
	public static String formataDataCompleto(Date data) {
		return dfCompleto.format(data);
	}
	
	public static String formataData(Date data,String formato) {
		DateFormat df = new SimpleDateFormat(formato);
		return df.format(data);
	}

	/**
	 * Retorna o campo dia de uma data.
	 * 
	 * @param data
	 * @return int
	 */
	public static int getDia(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		int dia = cal.get(Calendar.DATE);
		return dia;
	}

	/**
	 * Retorna o campo mês de uma data.
	 * 
	 * @param data
	 * @return dia
	 */
	public static int getMes(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		int dia = cal.get(Calendar.MONTH);
		return dia;
	}

	/**
	 * Retorna o campo ano de uma data.
	 * 
	 * @param data
	 * @return int
	 */
	public static int getAno(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		int dia = cal.get(Calendar.YEAR);
		return dia;
	}

	/**
	 * Informa se uma determinada data é sábado ou domingo.
	 * 
	 * @param data
	 * @return boolean
	 */
	public static boolean diaUtil(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		return (!(((cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (cal
				.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))));
	}

	/**
	 * 
	 * @param data1
	 * @param data2
	 * @return int
	 */
	public static int comparaData(Date data1, Date data2) {
		return data1.compareTo(data2);
	}

	/**
	 * Diferença em dias
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return int
	 */
	public static int diferencaData(Date dataInicial, Date dataFinal) {
		final int hora = 24;
		final int minuto = 60;
		final int segundo = 60;
		final int mili = 1000;

		Long data = dataFinal.getTime() - dataInicial.getTime();
		int dias = (data.intValue() / (hora * minuto * segundo * mili));
		return dias;
	}

	/**
	 * Cria array de datas de acordo com o intervalo
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return Date[] 
	 */
	public static Date[] datasIntermediarias(Date dataInicial, Date dataFinal) {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = new GregorianCalendar();
		cal.setTime(dataInicial);
		while (cal.getTime().compareTo(dataFinal) <= 0) {
			list.add(cal.getTime());
			cal.add(Calendar.DATE, 1);
		}
		return list.toArray(new Date[0]);
	}

	/**
	 * Passar de String para Date no formato Ano/Mês/Dia
	 * 
	 * @param data
	 *            em String
	 * 
	 * @return Date
	 */

	public static Date stringAnoMesDiaParaDate(String data)
			throws ParseException {
		final int barra = 4;
		if (data.charAt(barra) == '-') {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			data += " ";
			int fim = data.indexOf(" ");
			String parteData = data.substring(0, fim);
			return sdf.parse(parteData);
		}

		return df.parse(data);
	}

	/**
	 * Verifica se a data inicial é menor ou igual a data final.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return Se dataInicial é menor ou igual a dataFinal retorna true senão
	 *         retorna false.
	 */
	public static boolean validaDataInicialFinal(Date dataInicial,
			Date dataFinal) {
		return (dataInicial.compareTo(dataFinal) <= 0);
	}

	/**
	 * Verifica se a data é menor ou igual a a data da posição.
	 * 
	 * @param data
	 * @param dataPosicao
	 * @return Se data é menor ou igual a dataPosicao retorna true senão retorna
	 *         false.
	 */
	public static boolean validaDataPosicao(Date data, Date dataPosicao) {
		return (data.compareTo(dataPosicao) <= 0);
	}

	public static String formatMesAno(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		return sdf.format(date);
	}

	public static Date dataFinalMesAnterior(Date data) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		int mes, ano;
		if (cal.get(Calendar.MONTH) == 0) {
			mes = Calendar.DECEMBER + 1;
			ano = cal.get(Calendar.YEAR) - 1;
		} else {
			mes = cal.get(Calendar.MONTH);
			ano = cal.get(Calendar.YEAR);
		}
		Date dataAnterior = DateUtil.formataDataFinal(mes - 1, ano);
		return dataAnterior;
	}

	public static Date addDay(Date data, int numero) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(data);
		cal.add(Calendar.DATE, numero);
		return cal.getTime();
	}

	/**
	 * Seta para 0 a hora, minuto, segundo e milisegundo da data. Deixa apenas a
	 * parte inteira da data.
	 * 
	 * @param data
	 * @return Date
	 */
	public static Date converteDataSemHora(Date data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Formata a data para hh:mm
	 * 
	 * @param data
	 * @return String
	 */
	public static String converteHoraMinuto(Date data) {
		return dfHoraMinuto.format(data);
	}
	
	public static Long converteEmMiliSegundos(String unidadeMedida,Long valor){
		Long milisegundos= null;
		Long fator=1000L;
		if(unidadeMedida.equals("h")){
			fator=fator*60*60;
		}else if(unidadeMedida.equals("m")){
			fator=fator*60;
		} else {
			fator=fator*60*60;
		}
		
		milisegundos=fator*valor;
		
		return milisegundos;
	}

	public static Long converteHoraEmMinutos(String hora) {

		String hr = hora.substring(0, 2);
		String mn = hora.substring(3, 5);

		Long horaFmt = Long.parseLong(hr);
		Long minFmt = Long.parseLong(mn);

		if (horaFmt == 0) {
			horaFmt = 24L;
		}

		horaFmt = horaFmt * 60;

		Long minTotais = horaFmt + minFmt;
		return minTotais;
	}
	
	public static java.sql.Date obtemDataSql(java.util.Date dataUtil){
		Calendar cal = new GregorianCalendar();
		cal.setTime(dataUtil);		
		
		java.sql.Date dataSql = new java.sql.Date(cal.getTimeInMillis());		
		return dataSql;
	}	
	
	public static java.sql.Date converteTimestampToDate(java.sql.Timestamp timeStamp){		
		return new java.sql.Date(timeStamp.getTime());
		
	}
	
	public static Date getDataSomada(Date data, int dias) {
		GregorianCalendar c = new GregorianCalendar(new Locale("pt", "BR"));

		c.setTime(data);
		c.add(GregorianCalendar.DAY_OF_MONTH, dias);

		return c.getTime();
	}
	
	public static int calculaIdade(java.util.Date dataNasc){
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);
        
        // Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();
        
        // Obtém a idade baseado no ano
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        
        dateOfBirth.add(Calendar.YEAR, age);
        
        //se a data de hoje é antes da data de Nascimento, então diminui 1(um)
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;        
    }
	
	


}
