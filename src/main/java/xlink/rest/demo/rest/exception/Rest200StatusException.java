package xlink.rest.demo.rest.exception;

import io.netty.handler.codec.http.HttpResponseStatus;

public final class Rest200StatusException extends RestStatusException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5445993483193037967L;

	private final String msg;
	public Rest200StatusException(String msg) {
		super(0, null);
		this.msg = msg;
	}


	@Override
	public String getMessage() {
		return msg;
	}
	
	@Override
	public HttpResponseStatus getHttpStatus() {
		return HttpResponseStatus.OK;
	}

}
