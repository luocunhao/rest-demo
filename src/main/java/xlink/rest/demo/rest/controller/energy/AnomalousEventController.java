package xlink.rest.demo.rest.controller.energy;

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

public class AnomalousEventController extends RestController {

	private static final Logger logger = LoggerFactory.getLogger(AnomalousEventController.class);

	static String serviceId = "energy_management";
	static String objectName = "anomalous_event";
	//static String productId = "1607d2b619f000011607d2b619f06601";
	static String productId = ConfigData.ENERY_PRODUCT_ID;
	/*private String msg = null;
	private Integer code = null;*/

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
			// 分组标识
			String group_id = jsonObj.getString("group_id");
			// 用电异常
			Integer electrical_anomaly = jsonObj.getInteger("electrical_anomaly");
			// 用电异常描述
			String electrical_description = jsonObj.getString("electrical_description");
			// 用水异常
			Integer water_anomaly = jsonObj.getInteger("water_anomaly");
			// 用水异常描述
			String water_description = jsonObj.getString("water_description");
			// 添加数据时间
			String date = jsonObj.getString("date");
			// 2. 构建publish数据
			// 具体上报的数字
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("device_id", device_id);
			if (id != null) {
				data.put("id", id);
			}
			data.put("group_id", group_id);
			data.put("electrical_anomaly", electrical_anomaly);
			data.put("electrical_description", electrical_description);
			data.put("water_anomaly", water_anomaly);
			data.put("water_description", water_description);
			data.put("date", date);

			publishModel.setData(data);

			// 上报数据
			
			XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
			XlinkIotPublishResult result = future.get();
			logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());
			/*msg = result.getErrorMessage();
			code = result.getCode();*/

				

			
		}
		// response的结果
		JSONObject json = new JSONObject();
		json.put("code:", 200);
		return json;
	}

	/*private static XlinkIotPublishModel createModel(String device_id, String id, String group_id,
			Integer electrical_anomaly,String electrical_description,Integer water_anomaly, String water_description, String date, String queryParam) {
		
	
		return publishModel;
	}*/
}
