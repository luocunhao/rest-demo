package xlink.rest.demo.rest.controller.smartmeter;

import java.util.HashMap;
import java.util.Map;

import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.xlink.iot.sdk.XlinkIot;
import cn.xlink.iot.sdk.datastruct.XlinkIotPublishModel;
import cn.xlink.iot.sdk.datastruct.XlinkIotPublishResult;
import cn.xlink.iot.sdk.future.iotPublishFuture.XlinkIotPublishResultFuture;
import cn.xlink.iot.sdk.operator.XlinkIotPublish;
import xlink.rest.demo.rest.ERROR_CODE;
import xlink.rest.demo.rest.InitParam;
import xlink.rest.demo.rest.OperationMode;
import xlink.rest.demo.rest.RestController;
import xlink.rest.demo.rest.config.ConfigData;
import xlink.rest.demo.rest.exception.Rest400StatusException;

public class ElectricityMeterController extends RestController {

	private static final Logger logger = LoggerFactory.getLogger(ElectricityMeterController.class);

	static String serviceId = "intelligent_electricity_meter";
	static String objectName = "electricity_meter";
	// static String productId = "1607d4b6099900011607d4b609992e01";
	static String productId = ConfigData.SMARTMETER_PRODUCT_ID;
	/*
	 * private String msg = null; private Integer code = null;
	 */

	@Override
	protected void check_post(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
		super.check_post(request, response);
	}

	@Override
	protected Object post(Request request, Response response) throws Exception {

		XlinkIot xlinkIotClient = InitParam.getXlinkIotClient();

		// 1. 创建一个publish实例
		XlinkIotPublish xlinkIotPublish = new XlinkIotPublish(xlinkIotClient);

		// 获取参数
		String queryParam = getQueryParam(request, "action");

		XlinkIotPublishModel publishModel = new XlinkIotPublishModel();
		publishModel.setAppId(InitParam.appId);
		publishModel.setServiceId(serviceId);
		publishModel.setObjectName(objectName);
		publishModel.setOperation(OperationMode.getOperationMode(queryParam));
		// 可选，设置产品ID，由服务端提供，用于关联物联平台的相关信息
		publishModel.setProductId(productId);
		/*
		 * // response的结果 JSONObject json = new JSONObject();
		 */
		// 解析数据
		JSONArray jsonArray = getJsonArray(request);
		for (int index = 0, size = jsonArray.size(); index < size; index++) {

			JSONObject jsonObj = jsonArray.getJSONObject(index);
			// 获取device_id
			String device_id = jsonObj.getString("device_id");
			if (device_id == null) {
				throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device identification can not be empty");
			}
			// 记录ID
			String id = jsonObj.getString("id");
			// 获取在线状态
			Integer online_status = jsonObj.getInteger("online_status");
			// 电表当前读数
			Float reading = jsonObj.getFloat("reading");
			// 添加数据时间
			String date = jsonObj.getString("date");

			// 2. 构建publish数据
			// 具体上报的数字
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("device_id", device_id);
			if (id != null) {
				data.put("id", id);
			}
			data.put("online_status", online_status);
			data.put("reading", reading);
			data.put("date", date);
			publishModel.setData(data);

			// 上报数据
			XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
			XlinkIotPublishResult result = future.get();
			logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());
			/*
			 * msg = result.getErrorMessage(); code = result.getCode();
			 * json.put("device_id=" + device_id + " result", "code:" + code + ",msg:" +
			 * msg);
			 */
		}

		// response的结果
		JSONObject json = new JSONObject();
		json.put("code:", 200);
		return json;
	}

}
