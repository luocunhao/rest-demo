package xlink.rest.demo.rest;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.MessageObserver;

/**
 * Rest接口异常观察
 * @author XLINK
 *
 */
public class RestObserver extends MessageObserver {

	
	@Override
	protected void onException(Throwable exception, Request request, Response response) {
		// logger.error(String.format(" Observer by IP:[%s], Use Token:[%s]", request.getUrl(), exception));
		// funcGetIp(request), funcGetAccessToken(request)), exception);
	}

	// /**
	// * 获取请求IP
	// *
	// * @param request
	// * @return
	// */
	// private final String funcGetIp(Request request) {
	// String ip = request.getRemoteAddress().getHostString();
	// if ("127.0.0.1".equals(ip)) {
	// String nginx_proxy_ip = request.getHeader("X-Forwarded-For");
	// if (nginx_proxy_ip != null) {
	// return nginx_proxy_ip;
	// }
	// }
	// return ip;
	// }
	//
	// private final String funcGetAccessToken(Request request){
	// return request.getHeader("Access-Token");
	// }
	//

	/**
	 * 设置通用响应
	 * 
	 * @param response
	 */
	private void setting(Response response) {
		response.addHeader("Connection", "close");
		// TODO 支持跨域请求
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Access-Token,Corp-ID");
	}

	@Override
	protected void onReceived(Request request, Response response) {
		setting(response);
	}

	@Override
	protected void onComplete(Request request, Response response) {

	}

	@Override
	protected void onSuccess(Request request, Response response) {
	}
}
