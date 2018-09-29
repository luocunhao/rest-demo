package xlink.rest.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author shenweiran
 */
public final class StringTools {

	public final static String IPREGREX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";

	private final static Pattern DIGITAL_PATTERN = Pattern.compile("[0-9]*");

	private StringTools() {
	}

	public final static String getString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public final static long getLong(Object str) {
		if (str == null) {
			return 0;
		}
		return getLong(str.toString());
	}

	public final static int getInt(Object str) {
		if (str == null) {
			return 0;
		}
		return getInt(str.toString());
	}

	public final static int getInt(Object str, int default_value) {
		if (str == null) {
			return default_value;
		}
		return getInt(str.toString());
	}

	public final static float getFloat(Object str) {
		if (str == null) {
			return 0;
		}
		return getFloat(str.toString());
	}

	public final static double getDouble(Object str) {
		if (str == null) {
			return 0;
		}
		return getDouble(str.toString());
	}

	public final static boolean getBoolean(Object str) {
		if (str == null) {
			return false;
		}
		return getBoolean(str.toString());
	}

	public final static short getShort(Object str) {
		return getShort(str.toString());
	}

	public final static long getLong(String str) {
		return (long) getDouble(str);
	}

	public final static int getInt(String str) {
		return (int) getDouble(str);
	}

	public final static float getFloat(String str) {
		return (float) getDouble(str);
	}

	public final static double getDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public final static boolean getBoolean(String str) {
		return Boolean.valueOf(str);
	}

	public final static short getShort(String str) {
		return Short.parseShort(str);
	}

	public final static boolean isEmpty(String str) {
		return (null == str) ? true : ((str.trim().length() == 0) ? true : false);
	}

	public final static boolean isboolIp(String ipAddress) {
		if (isEmpty(ipAddress)) {
			return false;
		}
		Pattern pattern = Pattern.compile(IPREGREX);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();
	}

	/**
	 * 用户dynamodb不允许空字符串,必须有长度
	 * 
	 * @param str
	 * @return
	 */
	public final static String ifEmptySetNull(String str) {
		if (isEmpty(str)) {
			return " ";
		}
		return str;
	}

	/**
	 * 把空字符串转化为null
	 * 
	 * @author linsida
	 * @date 2016年10月12日 上午11:14:28
	 *
	 * @param str
	 * @return
	 */
	public final static String emptyString2Null(String str) {
		if (isEmpty(str)) {
			return null;
		}
		return str;
	}

	public final static boolean isDigit(String str) {
		Matcher m = DIGITAL_PATTERN.matcher(str);
		return m.matches();
	}
	
	public final static boolean equals(String str1, String str2) {
		return (str1 == null) ? (str2 == null) : ((str2 == null) ? false : str1.equals(str2));
	}
	
	public final static String toLowerCase(String str){
		if (str== null){
			return null;
		}
		return str.toLowerCase();
	}
}
