package xlink.rest.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarTools {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";

	public static Date funcGetDate(String dateStr, String datePattern) {
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date funcGetDate(String dateStr) {
		return funcGetDate(dateStr, DEFAULT_FORMAT);
	}

	public static String funcTransfer2String(Date date, String datePattern) {
		SimpleDateFormat format = new SimpleDateFormat(datePattern);
		return format.format(date);
	}

	public static String funcTransfer2String(Date date) {
		return funcTransfer2String(date, DEFAULT_FORMAT);
	}
}
