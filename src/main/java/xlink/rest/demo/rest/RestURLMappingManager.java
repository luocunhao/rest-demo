package xlink.rest.demo.rest;

import xlink.rest.demo.rest.controller.TestMyController;
import xlink.rest.demo.rest.controller.distribution.AlertController;
import xlink.rest.demo.rest.controller.distribution.DcScreenController;
import xlink.rest.demo.rest.controller.distribution.InCabinetController;
import xlink.rest.demo.rest.controller.distribution.OutCabinetController;
import xlink.rest.demo.rest.controller.distribution.OutLineController;
import xlink.rest.demo.rest.controller.distribution.PowerCompensationController;
import xlink.rest.demo.rest.controller.distribution.TransformerController;
import xlink.rest.demo.rest.controller.energy.AnomalousEventController;
import xlink.rest.demo.rest.controller.energy.BasicInfoController;
import xlink.rest.demo.rest.controller.energy.ElectricChargeDeviceController;
import xlink.rest.demo.rest.controller.energy.ElectricChargeUnitController;
import xlink.rest.demo.rest.controller.energy.PowerDeviceController;
import xlink.rest.demo.rest.controller.energy.PowerUnitController;
import xlink.rest.demo.rest.controller.energy.WaterChargeDeviceController;
import xlink.rest.demo.rest.controller.energy.WaterChargeUnitController;
import xlink.rest.demo.rest.controller.energy.WaterUnitController;
import xlink.rest.demo.rest.controller.entranceguard.*;
import xlink.rest.demo.rest.controller.smartmeter.ElectricityMeterController;
import xlink.rest.demo.rest.controller.smartmeter.WaterMeterController;

public class RestURLMappingManager extends SuperURLMappingManager {
	private static final RestURLMappingManager singleton = new RestURLMappingManager();

	public static final RestURLMappingManager instance() {
		return singleton;
	}

	private RestURLMappingManager() {

	}

	protected void init() throws Exception {
		register(REST_URL.MYTEST, TestMyController.class, RestPermissionType.ANYBODY);
		/**
		 * 能源设备基础属性
		 */
		register(REST_URL.BASIC_INFO, BasicInfoController.class, RestPermissionType.ANYBODY);
		register(REST_URL.POWER_DEVICE, PowerDeviceController.class, RestPermissionType.ANYBODY);
		register(REST_URL.POWER_UNIT, PowerUnitController.class, RestPermissionType.ANYBODY);
		register(REST_URL.ELECTRIC_CHARGE_DEVICE, ElectricChargeDeviceController.class, RestPermissionType.ANYBODY);
		register(REST_URL.ELECTRIC_CHARGE_UNIT, ElectricChargeUnitController.class, RestPermissionType.ANYBODY);
		register(REST_URL.ANOMALOUS_EVENT, AnomalousEventController.class, RestPermissionType.ANYBODY);
		register(REST_URL.WATER_UNIT, WaterUnitController.class, RestPermissionType.ANYBODY);
		register(REST_URL.WATER_CHARGE_UNIT, WaterChargeUnitController.class, RestPermissionType.ANYBODY);
		register(REST_URL.WATER_CHARGE_DEVICE, WaterChargeDeviceController.class, RestPermissionType.ANYBODY);
		
		/**
		 * 智能电表设备基础属性
		 */
		register(REST_URL.ELECTRICITY_METER, ElectricityMeterController.class, RestPermissionType.ANYBODY);
		register(REST_URL.WATER_METER, WaterMeterController.class, RestPermissionType.ANYBODY);
		
		
		/**
         * 变配电设备基础属性
         */
        register(REST_URL.H_VOLTAGE_IN_CABINET, InCabinetController.class, RestPermissionType.ANYBODY);
        register(REST_URL.H_VOLTAGE_OUT_CABINET, OutCabinetController.class, RestPermissionType.ANYBODY);
        register(REST_URL.DC_SCREEN, DcScreenController.class, RestPermissionType.ANYBODY);
        register(REST_URL.H_VOLATGE_POWER_COMPENSATION, PowerCompensationController.class, RestPermissionType.ANYBODY);
        register(REST_URL.TRANSFORMER, TransformerController.class, RestPermissionType.ANYBODY);
        register(REST_URL.ALERT, AlertController.class, RestPermissionType.ANYBODY);
        register(REST_URL.OUTLINE, OutLineController.class, RestPermissionType.ANYBODY);



        /**
		 * 门禁设备
		 * */
		register(REST_URL.ENTRANCE_GUARD_CONTROLLER, EntranceGuardControllerStatus.class, RestPermissionType.ANYBODY);
		register(REST_URL.OPEN_RECORD, OpenRecord.class, RestPermissionType.ANYBODY);
		register(REST_URL.DOOR_INITIALIZE, DoorInitialize.class, RestPermissionType.ANYBODY);
		register(REST_URL.DOOR_CONTROLLER, DoorController.class, RestPermissionType.ANYBODY);
		register(REST_URL.USER_LOGIN, UserLogin.class, RestPermissionType.ANYBODY);
		register(REST_URL.USER_LOGOUT, UserLogout.class, RestPermissionType.ANYBODY);
	}

}
