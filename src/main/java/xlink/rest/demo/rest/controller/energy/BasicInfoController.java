package xlink.rest.demo.rest.controller.energy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.restexpress.Request;
import org.restexpress.Response;

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

public class BasicInfoController extends RestController {

	static String serviceId = "energy_management";
	static String objectName = "device";
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
		/*// response的结果
		JSONObject json = new JSONObject();*/
		// 解析数据
		JSONArray jsonArray = getJsonArray(request);
		for (int index = 0, size = jsonArray.size(); index < size; index++) {

			JSONObject jsonObj = jsonArray.getJSONObject(index);
			// 获取id
			String id = jsonObj.getString("id");
			if (id == null || "".equals(id)) {
				throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device identification can not be empty");
			}
			// 获取在线状态
			Integer online_status = jsonObj.getInteger("online_status");
			// 设备名称
			String device_name = jsonObj.getString("device_name");
			// 设备类型
			String device_type = jsonObj.getString("device_type");
			// 更新数据时间
			String date = jsonObj.getString("date");

			// 2. 构建publish数据
			
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			data.put("online_status", online_status);
			data.put("device_name", device_name);
			data.put("device_type", device_type);
			data.put("date", date);
			publishModel.setData(data);

			// 上报数据
		
				XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
				XlinkIotPublishResult result = future.get();
				logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());
				/*msg = result.getErrorMessage();
				code = result.getCode();
				json.put("id=" + id + " result", "code:" + code + ",msg:" + msg);*/
			
		}
		// response的结果
		JSONObject json = new JSONObject();
		json.put("code:",200);
		return json;
	}

	/*private static XlinkIotPublishModel createModel(String id, Integer online_status, String device_name,
			String device_type, String date, XlinkIotPublishModel publishModel) {
		
		// 具体上报的数字
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", id);
		data.put("online_status", online_status);
		data.put("device_name", device_name);
		data.put("device_type", device_type);
		data.put("date", date);
		publishModel.setData(data);
		return publishModel;
	}*/

}
