package xlink.rest.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatTools {
	
	public static final String funcGetDateWithFormat(java.util.Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static final java.util.Date funcGetDateWithFormat(String date, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
}
