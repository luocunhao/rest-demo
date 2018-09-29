package xlink.rest.demo.rest;

public class REST_URL {
	
	/*******************************************************************************/
    /******************************* TEST TEST TEST ********************************/
    /******************************* TEST TEST TEST ********************************/
    /******************************* TEST TEST TEST ********************************/
    /******************************* TEST TEST TEST ********************************/
    /*******************************************************************************/
	
	/**
	 * mytest
	 */
	public static final String MYTEST = "/v2/mytest";
	/*********************************************************************************/
    /******************************* 能源设备基础属性 ********************************/
    /******************************* 能源设备基础属性 ********************************/
    /******************************* 能源设备基础属性 ********************************/
    /******************************* 能源设备基础属性 ********************************/
    /*********************************************************************************/
	
	/**
	 * 基础信息采集
	 */
	public static final String BASIC_INFO = "/v1/energy_management/device";
	
	/**
	 * 设备用电统计
	 */
	public static final String POWER_DEVICE = "/v1/energy_management/power_device";
	
	/**
	 * 用电单位统计
	 */
	public static final String POWER_UNIT = "/v1/energy_management/power_unit";
	
	/**
	 * 用电设备计费交易额
	 */
	public static final String ELECTRIC_CHARGE_DEVICE = "/v1/energy_management/electric_charge_device";
	

	/**
	 * 用水设备计费交易额
	 */
	public static final String WATER_CHARGE_DEVICE = "/v1/energy_management/water_charge_device";
	
	/**
	 * 用电单位计费交易额
	 */
	public static final String ELECTRIC_CHARGE_UNIT = "/v1/energy_management/electric_charge_unit";
	
	/**
	 * 异常事件
	 */
	public static final String ANOMALOUS_EVENT = "/v1/energy_management/anomalous_event";
	
	/**
	 * 用水单位统计
	 */
	public static final String WATER_UNIT = "/v1/energy_management/water_unit";
	
	/**
	 * 用水单位计费交易额
	 */
	public static final String WATER_CHARGE_UNIT = "/v1/energy_management/water_charge_unit";
	
	/*********************************************************************************/
    /******************************* 智能电表设备基础属性 ******************************/
    /******************************* 智能电表设备基础属性 ******************************/
    /******************************* 智能电表设备基础属性 ******************************/
    /******************************* 智能电表设备基础属性 ******************************/
    /*********************************************************************************/
	
	/**
	 * 电表
	 */
	public static final String ELECTRICITY_METER = "/v1/intelligent_electricity_meter/electricity_meter";
	
	/**
	 * 水表
	 */
	public static final String WATER_METER = "/v1/intelligent_electricity_meter/water_meter";
	
	/*********************************************************************************/
    /******************************* 变配电设备基础属性 ******************************/
    /******************************* 变配电设备基础属性  ******************************/
    /******************************* 变配电设备基础属性  ******************************/
    /******************************* 变配电设备基础属性  ******************************/
    /*********************************************************************************/
	
	/**
	 * 高压进线柜 
	 */
	public static final String H_VOLTAGE_IN_CABINET = "/v1/power_distribution_system/h_voltage_in_cabinet";
	
	/**
	 * 高压出线柜
	 */
	public static final String H_VOLTAGE_OUT_CABINET = "/v1/power_distribution_system/h_voltage_out_cabinet";
	
	/**
	 * 直流屏 
	 */
	public static final String DC_SCREEN = "/v1/power_distribution_system/dc_screen";
	
	/**
	 * 高压功率补偿装置
	 */
	public static final String H_VOLATGE_POWER_COMPENSATION = "/v1/power_distribution_system/h_volatge_power_compensation";
	
	/**
	 * 变压器
	 */
	public static final String TRANSFORMER = "/v1/power_distribution_system/transformer";
	
	/**
	 * 报警
	 */
	public static final String ALERT = "/v1/power_distribution_system/alert";
	
	/**
	 * 配电线路
	 */
	public static final String OUTLINE = "/v1/power_distribution_system/outline";



	/*********************************门禁设备****************************************/
	/*********************************门禁设备****************************************/
	/*********************************门禁设备****************************************/
	/**
	 * 门禁控制器状态
	 * */
    public static final String ENTRANCE_GUARD_CONTROLLER="/v1/door/door_status";
    /**
	 * 开门记录
	 * */
	public static final String OPEN_RECORD="/v1/door/open_record";
	/**
	 * 门禁初始化
	 * */
	public static final String DOOR_INITIALIZE="/v1/door/door_initialize";
	/**
	 * 门禁控制
	 * */
	public static final String DOOR_CONTROLLER="/v1/door/door_control";
	/**
	 * 卡用户注册
	 * */
	public static final String USER_LOGIN="/v1/door/user_login";
    /**
	 * 卡用户注销
	 * */
    public static final String USER_LOGOUT="/v1/door/user_logout";
}
