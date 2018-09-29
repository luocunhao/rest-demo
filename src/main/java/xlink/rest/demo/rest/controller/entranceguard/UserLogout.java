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
import xlink.rest.demo.rest.EntranceGuardConfig;
import xlink.rest.demo.rest.InitParam;
import xlink.rest.demo.rest.OperationMode;
import xlink.rest.demo.rest.RestController;

import java.util.HashMap;
import java.util.Map;

public class UserLogout extends RestController {
    private static final Logger logger = LoggerFactory.getLogger(UserLogout.class);
    private String serviceId="door";
    private String objectName="user_logout";



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

            Map<String,Object> map=new HashMap<>();
            map.put("id",jsonObject.getString("id"));
            map.put("card_id",jsonObject.getString("card_id"));
            map.put("open_mode",jsonObject.getInteger("open_mode"));

            publishModel.setData(map);

            // 上报数据
            XlinkIotPublishResultFuture future = null;
            future = xlinkIotPublish.publishToXlinkIotAsync(publishModel);
            XlinkIotPublishResult result = future.get();
            logger.debug("result code: " + result.getCode() + " errorMsg: " + result.getErrorMessage());
            if(result.getCode()!=200){
                JSONObject json = new JSONObject();
                json.put("error",result.getErrorMessage());
                return json;
            }
        }
        // response的结果
        JSONObject json = new JSONObject();
        json.put("code:", 200);
        return json;
    }
}
