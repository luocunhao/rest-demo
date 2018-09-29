package xlink.rest.demo.rest.exception;

import org.restexpress.exception.ServiceException;

import com.alibaba.fastjson.JSONObject;

public abstract class RestStatusException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8672946041107197737L;

	protected final int errorcode;
	protected final String msg;
	protected String callback; 
		

	public RestStatusException(int errorcode, String msg) {
		this.errorcode = errorcode;
		this.msg = msg;
	}

	public RestStatusException(int errorcode, String msg, Exception e) {
		super();
		this.errorcode = errorcode;
		this.msg = msg;
	}
	

	public void setCallback(String callback) {
		this.callback = callback;
	}

	@Override
	public String getMessage() {
		JSONObject root = new JSONObject();
		JSONObject error_json = new JSONObject();
		error_json.put("code", errorcode);
		error_json.put("msg", msg);
		root.put("error", error_json);
		String ret = root.toJSONString();
		if (callback != null) {
			return String.format("%s(%s)", callback, ret);
		}
		return ret;
	}

}
