package xlink.rest.demo.config;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlink.rest.demo.utils.PropertiesLoadUtils;
import xlink.rest.demo.utils.StringTools;

/**
 * Redis 配置类
 */
public class RedisConfig extends BaseConfig {
	public static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	/**
	 * ip地址
	 */
	public static String ip;
	/**
	 * 端口
	 */
	public static int port;
	/**
	 * 密码
	 */
	public static String pass;
	/**
	 * 最大连接数
	 */
	public static int maxTotal;
	/**
	 * 最大空闲连接数
	 */
	public static int maxIdle;
	/**
	 * 等待超时时间
	 */
	public static int maxWaitMillis;
	public static boolean testOnBorrow;
	public static boolean testOnReturn;
	public static boolean testWhileIdle;
	/**
	 * 哨兵连接地址
	 */
	public static Set<String> sentinels = new HashSet<String>();
	/**
	 * 系统是否启用哨兵
	 */
	public static boolean isSentinel = false;
	/**
	 * redis哨兵masterName
	 */
	public static String sentinelMasterName;

	/**
	 * 默认读取 db.properties
	 */
	public static void initConfig() {
		initConfig("configproperties");
	}

	public static void initConfig(String db_properties) {
		try {
			
			Properties properties = PropertiesLoadUtils.loadProperties(db_properties);
			funcParseSentinel(properties);
			pass = getProperties(properties, "redis.pass");
			maxTotal = StringTools.getInt(getProperties(properties, "redis.pool.maxTotal"));
			maxIdle = StringTools.getInt(getProperties(properties, "redis.pool.maxIdle"));
			maxWaitMillis = StringTools.getInt(getProperties(properties, "redis.pool.maxWaitMillis"));
			testOnBorrow = StringTools.getBoolean(getProperties(properties, "redis.pool.testOnBorrow"));
			testOnReturn = StringTools.getBoolean(getProperties(properties, "redis.pool.testOnReturn"));
			testWhileIdle = StringTools.getBoolean(getProperties(properties, "redis.pool.testWhileIdle"));
		} catch (Exception e) {
			logger.error("redis config init failed ..", e);
			System.exit(-1);
		}
	}

	/**
	 * 解析sentinel哨兵地址
	 * 
	 * @param properties
	 */
	private static void funcParseSentinel(Properties properties) {
		String sentinelsString = properties.getProperty("redis.sentinel.hosts");
		if (!StringTools.isEmpty(sentinelsString)) {
			for (String sentinel : sentinelsString.split(",")) {
				sentinels.add(sentinel);
			}

			if (sentinels.isEmpty()) {
				throw new UnsupportedOperationException("sentinels config is empty");
			}
			isSentinel = true;
			sentinelMasterName = getProperties(properties, "redis.sentinel.mastername", "master");
		} else {
			ip = getProperties(properties, "redis.host");
			port = StringTools.getInt(getProperties(properties, "redis.port"));
		}
	}

}
