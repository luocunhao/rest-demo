package xlink.rest.demo.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlink.rest.demo.utils.PropertiesLoadUtils;

public class DemoConfig extends BaseConfig {
	private static final Logger logger = LoggerFactory.getLogger(DemoConfig.class);

	/**
	 * Web Api 服务器地址
	 */
	public static String apiHost;
	/**
	 * Web Api访问的access token
	 */
	public static String apiAccessToken;
	/**
	 * 配置文件路径,保存起来
	 */
	public static String configPath;
	
	public static void initConfig(String initConfigPath) {
		try {
			Properties properties = PropertiesLoadUtils.loadProperties(initConfigPath);
			apiHost = getProperties(properties, "api.access.host");
			apiAccessToken = getProperties(properties, "api.access.token");
			configPath = initConfigPath;
		} catch (Exception e) {
			logger.error("config init failed ..", e);
			System.exit(-1);
		}
	}
}
