package xlink.rest.demo.config;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlink.rest.demo.utils.ContainerGetter;
import xlink.rest.demo.utils.PropertiesLoadUtils;
import xlink.rest.demo.utils.StringTools;
import xlink.rest.demo.utils.tuple.TowTuple;
import xlink.rest.demo.utils.tuple.Tuple;

/**
 * MongoDB配置类
 * 
 */
public class MongoConfig extends BaseConfig {
	private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

	public static int connectionsPerHost;
	public static int threadsAllowedToBlockForConnectionMultiplier;
	public static int connectTimeout;
	public static int maxWaitTime;
	public static boolean autoConnectRetry;
	public static boolean socketKeepAlive;
	public static int socketTimeout;
	public static String username;
	public static String password;
	public static int regionId;
	public static List<TowTuple<String, Integer>> hosts = ContainerGetter.copyOnWriteArrayList();
	public static String replicaSet = null;

	public static void initConfig(String db_properties) {
		try {
			Properties properties = PropertiesLoadUtils.loadProperties(db_properties);
			funcParseHost(properties);
			connectionsPerHost = StringTools.getInt(getProperties(properties, "mongo.connectionsPerHost"));
			threadsAllowedToBlockForConnectionMultiplier = StringTools.getInt(properties
					.getProperty("mongo.threadsAllowedToBlockForConnectionMultiplier"));
			connectTimeout = StringTools.getInt(getProperties(properties, "mongo.connectTimeout"));
			maxWaitTime = StringTools.getInt(getProperties(properties, "mongo.maxWaitTime"));
			autoConnectRetry = StringTools.getBoolean(getProperties(properties, "mongo.autoConnectRetry"));
			socketKeepAlive = StringTools.getBoolean(getProperties(properties, "mongo.socketKeepAlive"));
			socketTimeout = StringTools.getInt(getProperties(properties, "mongo.socketTimeout"));
			username = getProperties(properties, "mongo.username");
			password = getProperties(properties, "mongo.password");
			regionId = StringTools.getInt(getProperties(properties, "mongo.regionId"));
			replicaSet = getPropertiesAllowNull(properties, "mongo.replicaSet");

		} catch (Exception e) {
			logger.error("mongo config init failed ..", e);
			System.exit(-1);
		}
	}

	/**
	 * 解析host
	 * 
	 * @param properties
	 */
	private static void funcParseHost(Properties properties) {

		String host = properties.getProperty("mongo.host");
		;
		int port = StringTools.getInt(properties.getProperty("mongo.port"));
		if (host != null && port != 0) {
			hosts.add(Tuple.tuple(host, port));
		}
		String hostsString = properties.getProperty("mongo.shard.hosts");
		if (hostsString != null) {
			for (String host_port : hostsString.split(",")) {
				hosts.add(Tuple.tuple(host_port.split(":")[0], StringTools.getInt(host_port.split(":")[1])));
			}
		}
		if (hosts.isEmpty()) {
			throw new UnsupportedOperationException("host config is empty");
		}
	}

	public static void initConfig() {
		initConfig("config.properties");
	}

}
