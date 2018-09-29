package xlink.rest.demo.rest;

import io.netty.handler.codec.http.HttpMethod;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.restexpress.RestExpress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SuperURLMappingManager {
	public static class URLMapping {
		private final String url;
		private final RestController controller;
		private final int permission;

		public URLMapping(String url, RestController controller, int permission) {
			super();
			this.url = url;
			this.controller = controller;
			this.permission = permission;
		}

		public String getUrl() {
			return url;
		}

		public RestController getController() {
			return controller;
		}

		public int getPermission() {
			return permission;
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(SuperURLMappingManager.class);
	protected static Map<String, URLMapping> urlAndMapping = new ConcurrentHashMap<String, URLMapping>();;

	public SuperURLMappingManager() {
		try {
			init();
		} catch (Exception e) {
			logger.error("", e);
			System.exit(-1);
		}
	}

	protected abstract void init() throws Exception;

	/**
	 * 注册接口
	 * 
	 * @param url
	 * @param controller
	 * @param permission
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected void register(String url, Class<? extends RestController> controller, int permission) throws InstantiationException,
			IllegalAccessException {
		if (urlAndMapping.containsKey(url)) {
			logger.error("URL Repeat!! URL:" + url);
			System.exit(-1);
		}
		urlAndMapping.put(url, new URLMapping(url, controller.newInstance(), permission));
	}

	public void initMapping(RestExpress server) {
		try {
			for (Iterator<URLMapping> iterator = urlAndMapping.values().iterator(); iterator.hasNext();) {
				URLMapping mapping = iterator.next();
				server.uri(mapping.getUrl(), mapping.getController()).action("handleTemplate", HttpMethod.GET)
						.action("handleTemplate", HttpMethod.POST).action("handleTemplate", HttpMethod.PUT)
						.action("handleTemplate", HttpMethod.DELETE).action("handleTemplate", HttpMethod.OPTIONS);
			}
		} catch (Exception e) {
			logger.error("URL Init Mapping ", e);
			System.exit(-1);
		}
	}

	/**
	 * 判断该URL是否是本系统的Rest接口
	 * 
	 * @param url
	 * @return
	 */
	public boolean isUrlRegister(String url) {
		return urlAndMapping.containsKey(url);
	}

	/**
	 * 获得URL的访问角�?
	 * 
	 * @param url
	 * @return
	 */
	public int getPermission(String url) {
		return urlAndMapping.get(url).getPermission();
	}

}
