package xlink.rest.demo.rest.controller;

import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import xlink.rest.demo.rest.RestController;

public class TestMyController extends RestController {
	private static final Logger logger = LoggerFactory.getLogger(TestMyController.class);

	@Override
	protected Object get(Request request, Response response) throws Exception {
		String test = getQueryParam(request, "test");
		JSONObject retJson = new JSONObject();
		retJson.put("msg", test);
		return retJson;
	}

	@Override
	protected Object post(Request request, Response response) throws Exception {
		
		JSONArray parseArray = getJsonArray(request);
		//String string = bodyJson.toJSONString();
		//JSONArray parseArray = JSONObject.parseArray(string);
		System.out.println(parseArray);
		for (int index = 0,size=parseArray.size(); index < size; index++) {
			JSONObject jsonObject = parseArray.getJSONObject(index);
			String test = jsonObject.getString("test");
			System.out.println(test);
		}
		JSONObject retJson = new JSONObject();
		retJson.put("msg", "ok");
		return retJson;
	}

	@Override
	protected void check_post(Request request, Response response) throws Exception {
		
		super.check_post(request, response);
	}
	
}
