package com.hx.model.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Clase de utilidades para el proyecto Highexplosive.
 * 
 * @author Luis Ollero
 * 
 */
public class HXUtils {

	/**
	 * Devuelve una fecha formateada en {@link String} a partir de un
	 * {@link Date}
	 * 
	 * @param date
	 * @return
	 */
	public String dateToString(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(date.getTime());
		return cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH)
				+ "/" + cal.get(Calendar.YEAR);
	}

}
