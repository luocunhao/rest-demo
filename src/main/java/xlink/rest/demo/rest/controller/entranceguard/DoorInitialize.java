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

import java.util.HashMap;
import java.util.Map;

/**门禁初始化*/
public class DoorInitialize extends RestController {
    private static final Logger logger = LoggerFactory.getLogger(DoorInitialize.class);
    private String serviceId="door";
    private String objectName="door_initialize";



    protected Object post(Request request, Response response) throws Exception{
        XlinkIot xlinkIotClient = EntranceGuardConfig.getXlinkIotClient();

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
        publishModel.setProductId(EntranceGuardConfig.productId);
        // 解析数据
        JSONArray jsonArray = getJsonArray(request);
        for(int index=0;index<jsonArray.size();index++){
            JSONObject jsonObject=jsonArray.getJSONObject(index);
            if(jsonObject.getString("id")==null || " ".equals(jsonObject.getString("id")) ){
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "id identification can not be empty");
            }

            if(jsonObject.getString("name")==null || " ".equals(jsonObject.getString("name")) ){
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "name identification can not be empty");
            }

            if(jsonObject.getString("device_type")==null || " ".equals(jsonObject.getString("device_type")) ){
                throw new Rest400StatusException(ERROR_CODE.ID_MUST_NOT_NULL, "device_type identification can not be empty");
            }
            Map<String,Object> map=new HashMap<>();
            map.put("id",jsonObject.getString("id"));
            map.put("online_status",jsonObject.getString("name"));
            map.put("open_status",jsonObject.getString("device_type"));
            map.put("open_mode",jsonObject.getString("open_mode"));

            publishModel.setData(map);

            // 上报数据
            XlinkIotPublishResultFuture future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
            XlinkIotPublishResult result = future.get();
            logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());

        }
        // response的结果
        JSONObject json = new JSONObject();
        json.put("code:", 200);
        return json;
    }
}
