package xlink.rest.demo.utils;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class PoolHttpConnectionManager {
	private static final PoolHttpConnectionManager singleton = new PoolHttpConnectionManager();


	private final CloseableHttpClient httpClient;
	private final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	public static final PoolHttpConnectionManager instance() {
		return singleton;
	}

	public void init(int max, int per) {
		cm.setMaxTotal(max);
		cm.setDefaultMaxPerRoute(per);
	}

	private PoolHttpConnectionManager() {
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}
}
