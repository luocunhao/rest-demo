package xlink.rest.demo.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.netty.handler.codec.http.HttpMethod;
import xlink.rest.demo.rest.exception.Rest400StatusException;
import xlink.rest.demo.rest.exception.Rest403StatusException;
import xlink.rest.demo.rest.exception.Rest404StatusException;
import xlink.rest.demo.rest.exception.Rest503StatusException;
import xlink.rest.demo.rest.exception.RestStatusException;

public abstract class RestController {
	protected static final Logger logger = LoggerFactory.getLogger(RestController.class);

	protected static final int DEFAULT_OFFSET = 0;
	protected static final int DEFAULT_LIMIT = 10;

	/**
	 * 列表查询最多查1万条
	 */
	protected static final int LIMIT_MAX = 10000;

	private void funcCheck(Request request, Response response) throws Exception {
		String urlPattern = request.getResolvedRoute().getPattern();
		if (RestURLMappingManager.instance().isUrlRegister(urlPattern) == false) {
			throw new Rest404StatusException(ERROR_CODE.URL_NOT_FOUND, "url not found");
		}

		int urlPermission = RestURLMappingManager.instance().getPermission(urlPattern);
		if ((RestPermissionType.ANYBODY & urlPermission) > 0) {
			return;
		}
		if (request.getHttpMethod() == HttpMethod.OPTIONS || request.getHttpMethod() == HttpMethod.HEAD) {
			return;
		}
		
		if (request.getHttpMethod() == HttpMethod.GET) {
			check_get(request, response);
		}
		if (request.getHttpMethod() == HttpMethod.POST) {
			check_post(request, response);
		}
		if (request.getHttpMethod() == HttpMethod.PUT) {
			check_put(request, response);
		}
		if (request.getHttpMethod() == HttpMethod.DELETE) {
			check_delete(request, response);
		}
	}

	protected Object get(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "invalid access. POST " + request.getUrl() + " Unavailable.");
	}

	protected Object post(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "invalid access. POST " + request.getUrl() + " Unavailable.");
	}

	protected Object put(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "invalid access. PUT " + request.getUrl() + " Unavailable.");
	}

	protected Object delete(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.INVALID_ACCESS, "invalid access. DELETE " + request.getUrl() + " Unavailable.");
	}
	
	protected void check_get(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.ACCESS_TOKEN_INVALID, "Access-Token permission invalid");
	}

	protected void check_post(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.ACCESS_TOKEN_INVALID, "Access-Token permission invalid");
	}

	protected void check_put(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.ACCESS_TOKEN_INVALID, "Access-Token permission invalid");
	}

	protected void check_delete(Request request, Response response) throws Exception {
		throw new Rest403StatusException(ERROR_CODE.ACCESS_TOKEN_INVALID, "Access-Token permission invalid");
	}

	/**
	 * 参数字段不为空判断
	 * 
	 * @param field
	 * @param str
	 */
	protected final void funcValidStringNoNull(String field, Object str) {
		if (str == null) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_MUST_NOT_NULL, String.format("[%s] must not null ", field));
		}
	}

	/**
	 * 获取请求实体
	 * 
	 * @param request
	 * @return
	 */
	protected final JSONObject getBodyJson(Request request) {
		JSONObject bodyJson = null;
		try {
			bodyJson = JSONObject.parseObject(new String(request.getBody().copy().array()));
		} catch (Exception e) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_VALID_ERROR, "request content format must be json ");
		}

		if (bodyJson == null) {
			return new JSONObject();
		}
		return bodyJson;
	}
	
	
	/**
	 * 获取请求数组
	 * 
	 * @param request
	 * @return
	 */
	protected final JSONArray getJsonArray(Request request) {
		JSONArray jsonArray = null;
		try {
			jsonArray = JSONArray.parseArray(new String(request.getBody().copy().array()));
		} catch (Exception e) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_VALID_ERROR, "request content format must be json ");
		}

		if (jsonArray == null) {
			return new JSONArray();
		}
		return jsonArray;
	}
	
	
	/**
	 * 获取请求参数
	 * 
	 * @param request
	 * @return
	 */
	protected final String getQueryParam(Request request,String paramName) {
		
		String queryParam = request.getHeader(paramName);
		if(queryParam == null) {
			throw new Rest400StatusException(ERROR_CODE.URL_PARAM_MUST_NOT_NULL, "request parameter can not be empty");
		}
		return queryParam;
	}

	/**
	 * 验证请求查询偏移量
	 * 
	 * @param offset
	 */
	protected final void validOffset(int offset) {
		if (offset < 0) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_VALID_ERROR, "param offset error");
		}
	}

	/**
	 * 验证查询列表上限值
	 * 
	 * @param limit
	 */
	protected final void validLimit(int limit) {
		if (limit <= 0 || limit > LIMIT_MAX) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_VALID_ERROR, "param limit error");
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	protected final String getDate(Date date) {
		if (date == null) {
			return null;
		}
		String format = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public final Object handleTemplate(Request request, Response response) {
		Object resp = null;
		long now = System.currentTimeMillis();
		String callback = request.getHeader("Callback");
		try {
			funcCheck(request, response);
			if (request.getHttpMethod() == HttpMethod.GET) {
				resp = get(request, response);
			}
			if (request.getHttpMethod() == HttpMethod.POST) {
				resp = post(request, response);
			}
			if (request.getHttpMethod() == HttpMethod.PUT) {
				resp = put(request, response);
			}
			if (request.getHttpMethod() == HttpMethod.DELETE) {
				resp = delete(request, response);
			}
			Object ret = encodeCallBack(resp, callback);
			
			logger.warn(String.format("process: cost %d ms, method:[%s], url:[%s]", (System.currentTimeMillis() - now), request.getHttpMethod()
					.toString(), request.getResolvedRoute().getPattern()));
			
			return ret;
		} catch (RestStatusException e) {

			throw e;
		} catch (Exception e) {
			Rest503StatusException ee = new Rest503StatusException(ERROR_CODE.SERVICE_EXCEPTION, "service unavailable");
			logger.error("", e);
			throw ee;
		}
	}

	/**
	 * 验证字符长度是否符合要求,不能为空
	 * 
	 * @param field
	 * @param str
	 * @param min
	 * @param max
	 */
	protected final void validStringNotNull(String field, String str) {
		if (str == null) {
			throw new Rest400StatusException(ERROR_CODE.PARAM_MUST_NOT_NULL, String.format("[%s] must not null ", field));
		}
	}

	private final Object encodeCallBack(Object ret_content, String callback) {
		if (callback != null) {
			return String.format("%s(%s)", callback, ret_content.toString());
		}
		return ret_content;
	}

}
