package com.yihu.wlyy.controllers.patient.device;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.device.PatientDevice;
import com.yihu.wlyy.services.device.PatientDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 患者端：设备管理控制类
 * @author George
 *
 */
@Controller
@RequestMapping(value = "patient/device")
@Api(value = "患者设备管理", description = "患者设备管理")
public class PatientDeviceController extends BaseController {

	@Autowired
	private PatientDeviceService patientDeviceService;


	@ApiOperation("设备保存接口")
	@RequestMapping(value = "SavePatientDevice", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String saveDevice(@ApiParam(name="json",value="设备数据json",defaultValue = "{\"deviceId\": \"2\",\"deviceName\": \"血压计-优瑞恩\",\"deviceSn\": \"123456\",\"categoryCode\": \"2\",\"userType\": \"1\"}")
							  @RequestParam(value="json",required = true) String json) {
		try {
			PatientDevice device = objectMapper.readValue(json,PatientDevice.class);
			// 设置患者标识
			device.setUser(getUID());

			patientDeviceService.saveDevice(device);

			return success("设备保存成功！");
		}
		catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}

	/**
	 *  设备列表获取
	 * @return 操作结果
	 */
	@ApiOperation("患者设备列表获取")
	@RequestMapping(value = "PatientDeviceList", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	@ResponseBody
	public String getDeviceByPatient(@ApiParam(name="id",value="分页起始id",defaultValue = "0")
									  @RequestParam(value="id",required = true) long id,
									 @ApiParam(name="pagesize",value="每页条数",defaultValue = "10")
									 @RequestParam(value="pagesize",required = true) int pagesize) {
		try {
			//TODO demo数据
			String demo ="[{\"id\":456,\"deviceId\":1,\"deviceSn\":\"cc3321xxxccc1211\",\"deviceName\":\"血压计-康为A206G\",\"user\":\"CS20160830001\",\"categoryCode\":\"2\",\"userType\":\"2\",\"userIdcard\":\"350204194810272040\",\"czrq\":\"2016-09-02 14:50:58\"},{\"id\":448,\"deviceId\":3,\"deviceSn\":\"xxx1112221\",\"deviceName\":\"血糖仪-爱奥乐G-777G\",\"user\":\"CS20160830001\",\"categoryCode\":\"1\",\"userType\":\"-1\",\"userIdcard\":\"350204194810272040\",\"czrq\":\"2016-09-02 14:34:14\"}]";
//			List list = objectMapper.readValue(demo,List.class);

			Page<PatientDevice> list = patientDeviceService.findByPatient(getUID(), id, pagesize);
			return write(200, "查询成功", "list", list);
		} catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}


	@ApiOperation("获取患者设备信息")
	@RequestMapping(value = "PatientDeviceInfo", produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
	@ResponseBody
	public String getPatientDeviceInfo(@ApiParam(name="id",value="患者设备ID",defaultValue = "146")
									 @RequestParam(value="id",required = true) String id) {
		try {
			//TODO 示例数据
			String demo = "{\"id\":448,\"deviceId\":3,\"deviceSn\":\"xxx1112221\",\"deviceName\":\"血糖仪-爱奥乐G-777G\",\"user\":\"CS20160830001\",\"categoryCode\":\"1\",\"userType\":\"-1\",\"userIdcard\":\"350204194810272040\",\"czrq\":\"2016-09-02 14:34:14\"}";
//			PatientDevice device = objectMapper.readValue(demo,PatientDevice.class);

			PatientDevice device = patientDeviceService.findById(id);
			return write(200, "查询成功", "data", device);
		} catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}

	/**
	 *  通过sn码获取设备绑定情况
	 */
	@ApiOperation("通过sn码获取设备绑定情况")
	@RequestMapping(value = "PatientDeviceIdcard", produces = "application/json;charset=UTF-8",method = RequestMethod.GET)
	@ResponseBody
	public String getDeviceUser(@ApiParam(name="type",value="设备类型",defaultValue = "1")
									     @RequestParam(value="type",required = true) String type,
										 @ApiParam(name="device_sn",value="设备SN码",defaultValue = "15L000002")
										 @RequestParam(value="device_sn",required = true) String deviceSn) {
		try {
			List<Map<String,String>> list = patientDeviceService.getDeviceUser(getUID(),deviceSn,type);
			return write(200, "获取设备绑定信息成功！", "data",list);
		} catch (Exception ex) {
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}

	/**
	 * 设备删除
	 */
	@ApiOperation("设备删除")
	@RequestMapping(value = "DeletePatientDevice", produces = "application/json;charset=UTF-8",method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@ApiParam(name="id",value="删除设备关联ID")
						  @RequestParam(value="id",required = true) String id) {
		try {
			PatientDevice pd = patientDeviceService.findById(id);
			if(pd!=null)
			{
				if (!StringUtils.equals(pd.getUser(), getUID())) {
					return error(-1, "只允许删除自己的设备！");
				}
				// 删除设备
				patientDeviceService.deleteDevice(id);
				return success("设备已删除！");
			}
			else{
				return error(-1, "不存在该设备！");
			}
		} catch (Exception ex) {
			return invalidUserException(ex, -1,ex.getMessage());
		}
	}
}
