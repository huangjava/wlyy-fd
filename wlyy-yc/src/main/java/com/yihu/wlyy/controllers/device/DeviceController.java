package com.yihu.wlyy.controllers.device;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.common.ResultModel;
import com.yihu.wlyy.models.device.Device;
import com.yihu.wlyy.models.device.DeviceCategory;
import com.yihu.wlyy.services.device.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/device")
@Api(description = "设备接口调用")
public class DeviceController extends BaseController {


	@Autowired
	private DeviceService deviceService;

	@ApiOperation("新增设备信息")
	@RequestMapping(value = "/data", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultModel pushData(
			@ApiParam(name="deviceData",value="设备数据")
			@RequestParam(value = "deviceData") String deviceData,
			@ApiParam(name="deviceType",value="设备类型")
			@RequestParam(value = "deviceType") String deviceType) {
		try {
			return deviceService.pushData(deviceData, deviceType);
		} catch (Exception e) {
			return ResultModel.error("Device data incoming failure");
		}
	}
	@ApiOperation("获取设备信息")
	@RequestMapping(value = "/data", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultModel getData(
			@ApiParam(name="deviceData",value="设备编码")
			@RequestParam(value = "deviceCode", required = false) String deviceCode) {
		try {
			return deviceService.getData(deviceCode);
		} catch (Exception e) {
			return  ResultModel.error("Device data acquisition failure");
		}
	}
}
