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

public class OutLineController extends RestController {
	private static final Logger logger = LoggerFactory.getLogger(OutLineController.class);

	static String serviceId = "power_distribution_system";
	static String objectName = "outline";
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
			String id = jsonObj.getString("id");
			if (id == null || "".equals(id)) {
				throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device identification can not be empty");
			}
			// A相电流
			Float electric_a = jsonObj.getFloat("electric_a");
			// B相电流
			Float electric_b = jsonObj.getFloat("electric_b");
			// C相电流
			Float electric_c = jsonObj.getFloat("electric_c");
			// A相电压
			Float volatge_a = jsonObj.getFloat("volatge_a");
			// B相电压
			Float volatge_b = jsonObj.getFloat("volatge_b");
			// C相电压
			Float volatge_c = jsonObj.getFloat("volatge_c");
			// 有功功率
			Float active_power = jsonObj.getFloat("active_power");
			// 无功功率
			Float reactive_power = jsonObj.getFloat("reactive_power");
			// 视在功率
			Float apparent_power = jsonObj.getFloat("apparent_power");
			// System.out.println(device_apparent_power);
			// 功率因数
			Float power_factor = jsonObj.getFloat("power_factor");
			// 总有功电度累积量
			Float active_power_ele = jsonObj.getFloat("active_power_ele");
			// 总无功电度累积量
			Float reactive_power_ele = jsonObj.getFloat("reactive_power_ele");
			// 开关状态
			Integer switch_status = jsonObj.getInteger("switch_status");
			// 更新数据时间
			String date = jsonObj.getString("date");
			// 2. 构建publish数据
			// 具体上报的数字
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", id);
			data.put("electric_a", electric_a);
			data.put("electric_b", electric_b);
			data.put("electric_c", electric_c);
			data.put("volatge_a", volatge_a);
			data.put("volatge_b", volatge_b);
			data.put("volatge_c", volatge_c);
			data.put("active_power", active_power);
			data.put("reactive_power", reactive_power);
			data.put("apparent_power", apparent_power);
			data.put("power_factor", power_factor);
			data.put("active_power_ele", active_power_ele);
			data.put("reactive_power_ele", reactive_power_ele);
			data.put("switch_status", switch_status);
			data.put("date", date);
			publishModel.setData(data);
			
			// 上报数据
			XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
			XlinkIotPublishResult result = future.get();
			logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());
			/*
			 * msg = result.getErrorMessage(); code = result.getCode(); json.put("id=" + id
			 * + " result", "code:" + code + ",msg:" + msg);
			 */
		}

		// response的结果
		JSONObject json = new JSONObject();
		json.put("code:", 200);
		return json;
	}

}
