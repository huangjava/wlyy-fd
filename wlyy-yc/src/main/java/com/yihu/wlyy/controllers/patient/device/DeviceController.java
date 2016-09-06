package com.yihu.wlyy.controllers.patient.device;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.device.Device;
import com.yihu.wlyy.models.device.DeviceCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/common/device")
@Api(description = "设备管理")
public class DeviceController extends BaseController {
	


	@ApiOperation("获取设备分类")
	@RequestMapping(value = "DeviceCategory", produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
	@ResponseBody
	public String getDeviceCategory() {
		try {
			List<DeviceCategory> list = new ArrayList<DeviceCategory>();
			return write(200,"获取设备分类成功！","data",list);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "获取设备分类失败！");
		}
	}

	@ApiOperation("获取设备列表")
	@RequestMapping(value = "DeviceList", produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
	@ResponseBody
	public String getDeviceList(@ApiParam(name="category_code",value="设备类型代码",defaultValue = "1")
								 @RequestParam(value="category_code",required = true) String categoryCode) {
		try {
			//TODO demo数据
			String demo ="";
			if ("1".equals(categoryCode)){
				demo="[{\"id\":2,\"categoryCode\":\"1\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-07-12/497280_1468291541996.png\",\"brands\":\"康为\",\"model\":\"G-426-3\",\"isMultiUser\":\"0\",\"multiUser\":\"{\\\"默认\\\":\\\"-1\\\"}\",\"name\":\"康为G-426-3\",\"czrq\":\"2016-08-26\",\"del\":\"1\"},{\"id\":3,\"categoryCode\":\"1\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-07-12/497276_1468291512115.png\",\"brands\":\"爱奥乐\",\"model\":\"G-777G\",\"isMultiUser\":\"0\",\"multiUser\":\"{\\\"默认\\\":\\\"-1\\\"}\",\"name\":\"爱奥乐G-777G\",\"czrq\":\"2016-08-26\",\"del\":\"1\"}]";
			}else if ("2".equals(categoryCode)){
				demo="[{\"id\":1,\"categoryCode\":\"2\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-06-06/441671_1465177502594.png\",\"brands\":\"康为\",\"model\":\"A206G\",\"isMultiUser\":\"1\",\"multiUser\":\"{\\\"1\\\":\\\"1\\\",\\\"2\\\":\\\"2\\\"}\",\"name\":\"康为A206G\",\"czrq\":\"2016-08-26\",\"del\":\"1\"},{\"id\":4,\"categoryCode\":\"2\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-07-11/513080_1468201026156.png\",\"brands\":\"优瑞恩\",\"model\":\"U80EH\",\"isMultiUser\":\"1\",\"multiUser\":\"{\\\"1\\\":\\\"爸爸\\\",\\\"2\\\":\\\"妈妈\\\"}\",\"name\":\"优瑞恩U80EH\",\"czrq\":\"2016-09-05\",\"del\":\"1\"}]";
			}

			List list = objectMapper.readValue(demo,List.class);
			return write(200,"获取设备列表成功！","list",list);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "获取设备列表失败！");
		}
	}

	@ApiOperation("获取设备信息")
	@RequestMapping(value = "DeviceInfo", produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
	@ResponseBody
	public String getDeviceInfo(@ApiParam(name="id",value="设备ID",defaultValue = "19")
									   @RequestParam(value="id",required = true) String id) {
		try {
			//TODO demo示例
			String demo ="";
			if ("1".equals(id)){
				demo ="{\"id\":1,\"categoryCode\":\"2\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-06-06/441671_1465177502594.png\",\"brands\":\"康为\",\"model\":\"A206G\",\"isMultiUser\":\"1\",\"multiUser\":\"{\\\"1\\\":\\\"1\\\",\\\"2\\\":\\\"2\\\"}\",\"name\":\"康为A206G\",\"czrq\":\"2016-08-26\",\"del\":\"1\"}";
			}else if ("3".equals(id)){
				demo ="{\"id\":2,\"categoryCode\":\"1\",\"photo\":\"http://f1.yihuimg.com/TFS/upfile/doctor_honor/2016-07-12/497280_1468291541996.png\",\"brands\":\"康为\",\"model\":\"G-426-3\",\"isMultiUser\":\"0\",\"multiUser\":\"{\\\"默认\\\":\\\"-1\\\"}\",\"name\":\"康为G-426-3\",\"czrq\":\"2016-08-26\",\"del\":\"1\"}";
			}

			Device device = objectMapper.readValue(demo,Device.class);

			return write(200, "查询成功", "data", device);
		} catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}
}
