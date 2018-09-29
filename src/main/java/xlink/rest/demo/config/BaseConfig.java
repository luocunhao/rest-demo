package xlink.rest.demo.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseConfig {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseConfig.class);
	
	protected static final String getProperties(Properties prop, String key) {
		if (prop == null) {
			logger.error("properties is null ");
			System.exit(-1);
		}
		if (prop.containsKey(key) == false) {
			logger.error(String.format("properties have no key \"%s\" !", key));
			System.exit(-1);
		}
		return prop.getProperty(key);
	}
	
	protected static final String getPropertiesAllowNull(Properties prop, String key) {
		if (prop == null) {
			logger.error("properties is null ");
			System.exit(-1);
		}
		if (prop.containsKey(key) == false) {
			logger.info(String.format("properties have no key \"%s\" !", key));
			return null;
		}
		return prop.getProperty(key);
	}
	
	protected static final String getProperties(Properties prop, String key, String def) {
		if (prop == null) {
			logger.error("db properties is null ");
			System.exit(-1);
		}
		if (prop.containsKey(key) == false) {
			logger.warn(String.format("properties have no key \"%s\" !", key));
		}
		String ret = prop.getProperty(key);
		if (ret == null && def != null) {
			ret = def;
		}
		return ret;
	}
}
