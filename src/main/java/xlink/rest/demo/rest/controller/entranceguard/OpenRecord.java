package xlink.rest.demo.rest.controller.entranceguard;

import cn.xlink.iot.sdk.XlinkIot;
import cn.xlink.iot.sdk.datastruct.XlinkIotPublishModel;
import cn.xlink.iot.sdk.datastruct.XlinkIotPublishResult;
import cn.xlink.iot.sdk.future.iotPublishFuture.XlinkIotPublishResultFuture;
import cn.xlink.iot.sdk.operator.XlinkIotPublish;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xlink.rest.demo.rest.*;
import xlink.rest.demo.rest.exception.Rest400StatusException;
import xlink.rest.demo.rest.exception.Rest503StatusException;
import xlink.rest.demo.utils.CalendarTools;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 开门记录
 */
public class OpenRecord extends RestController {
    private static final Logger logger = LoggerFactory.getLogger(OpenRecord.class);
    private String serviceId = "door";
    private String objectName = "open_record";


    protected Object post(Request request, Response response) throws Exception {
        JSONObject json = new JSONObject();
        XlinkIot xlinkIotClient = EntranceGuardConfig.getXlinkIotClient();

        // 1. 创建一个publish实例
        XlinkIotPublish xlinkIotPublish = new XlinkIotPublish(xlinkIotClient);

        // 获取参数
        String queryParam = getQueryParam(request, "action");
        XlinkIotPublishModel publishModel = new XlinkIotPublishModel();
        publishModel.setAppId(InitParam.appId);
        publishModel.setServiceId(serviceId);
        publishModel.setObjectName(objectName);
        publishModel.setMessageId(1);
        publishModel.setOperation(OperationMode.getOperationMode(queryParam));
        // 可选，设置产品ID，由服务端提供，用于关联物联平台的相关信息
        publishModel.setProductId(EntranceGuardConfig.productId);
        // 解析数据
        JSONArray jsonArray = getJsonArray(request);
        for (int index = 0; index < jsonArray.size(); index++) {
            JSONObject jsonObject = jsonArray.getJSONObject(index);
            if (jsonObject.getString("device_id") == null || " ".equals(jsonObject.getString("device_id"))) {
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device_id identification can not be empty");
            }
            if (jsonObject.getInteger("open_mode") > 9 || " ".equals(jsonObject.getInteger("open_mode"))) {
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "open_mode identification can not be empty");
            }
            if (jsonObject.getDate("open_time") == null || " ".equals(jsonObject.getDate("open_time"))) {
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "open_mode identification can not be empty");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", jsonObject.getString("id"));
            map.put("device_id", jsonObject.getString("device_id"));
            map.put("open_mode", jsonObject.getInteger("open_mode"));
            map.put("open_desc", jsonObject.getString("open_desc"));
            String open_time = jsonObject.getString("open_time");
            map.put("open_time", open_time);
            map.put("card_id", jsonObject.getString("card_id"));
            map.put("usercode", jsonObject.getString("usercode"));
            map.put("username", jsonObject.getString("username"));
            map.put("open_fault", jsonObject.getString("open_fault"));
            publishModel.setData(map);

            // 上报数据
            XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
            XlinkIotPublishResult result = future.get();
            int resultcode = result.getCode();

                json.put("code", resultcode);
                json.put("msg", result.getErrorMessage());
           if(resultcode!=200) {
                throw new Rest503StatusException(ERROR_CODE.SERVICE_EXCEPTION,result.getErrorMessage());
            }
            logger.debug("result code: " + resultcode + " errorMsg: " + result.getErrorMessage());
        }
        // response的结果
        return json;
    }

}