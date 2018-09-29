package xlink.rest.demo.rest.controller.distribution;

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

public class AlertController extends RestController {

	private static final Logger logger = LoggerFactory.getLogger(AlertController.class);
	static String serviceId = "power_distribution_system";
	static String objectName = "alert";
	// static String productId = "1607d2b6099900011607d2b609990001";
	static String productId = ConfigData.DISTRIBUTION_PRODUCT_ID;
	/*
	 * private String msg = null; private Integer code = null;
	 */

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
			// 获取id
			String device_id = jsonObj.getString("device_id");
			if (device_id == null || "".equals(device_id)) {
				throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device identification can not be empty");
			}
			// 记录ID
			String id = jsonObj.getString("id");
			// 欠电压报警
			Integer undervoltage = jsonObj.getInteger("undervoltage");
			// 高温报警
			Integer high_temperature = jsonObj.getInteger("high_temperature");
			// 过负载报警
			Integer overload = jsonObj.getInteger("overload");
			// 低油压报警
			Integer low_oil_pressure = jsonObj.getInteger("low_oil_pressure");
			// 漏电报警
			Integer electric_leakage = jsonObj.getInteger("electric_leakage");
			// 添加数据时间
			String date = jsonObj.getString("date");

			// 2. 构建publish数据
			// 具体上报的数字
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("device_id", device_id);
			if (id != null) {
				data.put("id", id);
			}
			data.put("undervoltage", undervoltage);
			data.put("high_temperature", high_temperature);
			data.put("overload", overload);
			data.put("low_oil_pressure", low_oil_pressure);
			data.put("electric_leakage", electric_leakage);
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
