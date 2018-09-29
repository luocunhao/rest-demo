package xlink.rest.demo;

import org.restexpress.RestExpress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xlink.rest.demo.args.DemoArgs;
import xlink.rest.demo.config.DemoConfig;
import xlink.rest.demo.config.Log4jConfig;
import xlink.rest.demo.rest.*;

public class DemoServer extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(DemoServer.class);

	private final int port;

	public DemoServer(int port) {
		super();
		this.port = port;
	}

	@Override
	public void run() {
		RestExpress server = new RestExpress();
		RestExpress.setDefaultSerializationProvider(new SerializationProvider());
		server.setMaxContentSize(1024 * 1024 * 12);
		server.setKeepAlive(true);
		server.setUseTcpNoDelay(true);
		server.setConnectTimeoutMillis(1000);
		//server.setIoThreadCount(32);
		//server.setExecutorThreadCount(64);
		server.setIoThreadCount(Runtime.getRuntime().availableProcessors() * 4);
		server.setExecutorThreadCount(Runtime.getRuntime().availableProcessors() * 8);
		logger.info("IoThreadCount="+Runtime.getRuntime().availableProcessors() * 4+"-----------ExecutorThreadCount="+Runtime.getRuntime().availableProcessors() * 8);
		server.addMessageObserver(new RestObserver());
		server.addPreprocessor(new RestPreprocessor());
		server.addPostprocessor(new RestPostprecessor());
		RestURLMappingManager.instance().initMapping(server);
		server.bind(port);
		server.awaitShutdown();
	}

	/**
	 * 启动参数为 
	 * -id=1000 -port=8887 -config=/config.properties -log4j=/log4j.properties
	 * @param args
	 */
	public static void main(String[] args) {
		DemoArgs.load(args);

		Log4jConfig.configLog4j(DemoArgs.log4jPath);
		//DemoConfig.initConfig(DemoArgs.configPath);
		logger.info("system init.....");
		
		EntranceGuardConfig.initBasic();
		logger.info("iot-sdk init.....");
		
		//MongoConfig.initConfig(DemoArgs.configPath);
		//MongoDB.init();
		//logger.info("mongodb init.....");

		DemoServer server = new DemoServer(DemoArgs.port);
		server.start();
		logger.info("demo server start up......");
	}
}
