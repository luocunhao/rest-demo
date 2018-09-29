package xlink.rest.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoadUtils {

	/**
	 * 加载properties配置文件
	 * 
	 * @param name
	 * @return
	 * @throws IOException 
	 */
	public static Properties loadProperties(String name) throws Exception {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			try {
				in = PropertiesLoadUtils.class.getResourceAsStream(name);
			} catch (Exception e) {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ie) {
						ie.printStackTrace();
					}
				}
				in = readAbsolute(name);
			}
			
			if(null == in){
				in = readAbsolute(name);
			}
			
			prop.load(in);
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	private static InputStream readAbsolute(String name) throws Exception{
		InputStream in = null;
		File file = new File(name);
		if(file.isFile() && file.canRead()){
			in = new FileInputStream(file);
		}
		return in;
	}
	
}
