package com.yihu.wlyy.controllers.common;

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
@RequestMapping(value = "/common/device")
@Api(description = "公共设备管理")
public class CommonDeviceController extends BaseController {


	@Autowired
	private DeviceService deviceService;

	@ApiOperation("获取设备分类")
	@RequestMapping(value = "deviceCategory", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String getDeviceCategory() {
		try {
			List<DeviceCategory> list = deviceService.findAllCategory();
			return write(200,"获取设备分类成功！","data",list);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "获取设备分类失败！");
		}
	}

	@ApiOperation("获取设备列表")
	@RequestMapping(value = "deviceList", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String getDeviceList(@ApiParam(name="category_code",value="设备类型代码",defaultValue = "1")
								@RequestParam(value="category_code",required = true) String categoryCode) {
		try {
			//TODO demo数据
			List<Device> list = deviceService.findDeviceByCategory(categoryCode);
			return write(200,"获取设备列表成功！","list",list);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "获取设备列表失败！");
		}
	}

	@ApiOperation("获取设备信息")
	@RequestMapping(value = "deviceInfo", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String getDeviceInfo(@ApiParam(name="id",value="设备ID",defaultValue = "19")
								@RequestParam(value="id",required = true) String id) {
		try {
			//TODO demo示例
			Device device = deviceService.findById(id);
			return write(200, "查询成功", "data", device);
		} catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}
}
