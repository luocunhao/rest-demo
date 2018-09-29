package xlink.rest.demo.rest;

import cn.xlink.iot.sdk.XlinkIot;
import cn.xlink.iot.sdk.XlinkIotBuilder;
import cn.xlink.iot.sdk.exception.XlinkIotException;
import xlink.rest.demo.rest.exception.Rest404StatusException;

public class EntranceGuardConfig {
    public static String appId="3207d4b719cc2600";//"3207d4b736ae9800";
    public static String appSecret="7246c8b191d06f65a9e95fe34f99bcd1";//"c635e023ae90544756b8bb3ad4880017";
    public static String endpoint="iotlink.einwin.com:21883";//"iotcmtest.bgycc.com:21883";
    public static String productId="1607d4b7436300011607d4b743634a01";//"1607d4b736ad00011607d4b736adb601";
    static XlinkIot xlinkIotClient = null;




    public static XlinkIot getXlinkIotClient() {
        return xlinkIotClient;
    }

    public static void setXlinkIotClient(XlinkIot xlinkIotClient) {
        InitParam.xlinkIotClient = xlinkIotClient;
    }




    public static void initBasic(){
        // 创建一个客户端构造器
        XlinkIotBuilder builder = new XlinkIotBuilder();
        // 设置凭证和地址
        builder.setAppId(appId).setAppSecret(appSecret).setEndPoint(endpoint);
        // 设置心跳周期,单位是秒
        builder.setKeepAlive(120);
        // 创建一个Mqtt连接的客户端

        try {
            xlinkIotClient = builder.buildXlinkIotMqttClient();
        } catch (XlinkIotException e) {
            throw new Rest404StatusException(ERROR_CODE.XLINKIOTCLIENT_NOT_EXIST, "the xlink-iot-client does not exist");
        }
    }
}
