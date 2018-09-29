package xlink.rest.demo.rest;

import cn.xlink.iot.sdk.XlinkIot;
import cn.xlink.iot.sdk.XlinkIotBuilder;
import cn.xlink.iot.sdk.exception.XlinkIotException;
import xlink.rest.demo.rest.exception.Rest404StatusException;

public class InitParam {
	
	
	/*public static String appId = "3207d4b609964a00";
	static String appSecret = "82b010b9a5951dce4dea9eccb266b26b";
	static String endpoint = "iotcmtest.bgycc.com:21883";*/
	public static String appId = "3207d4b6a11c1400";
	static String appSecret = "f00915e51a3357a8ee1d16be847ad45f";
	static String endpoint = "iotcm.bgycc.com:21883";
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
