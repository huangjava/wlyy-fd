package com.yihu.wlyy.services.device;


import com.yihu.wlyy.daos.PatientDeviceDao;
import com.yihu.wlyy.models.device.PatientDevice;
import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.CollectionUtil;
import com.yihu.wlyy.util.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.Clock;

import java.util.*;

@Component
@Transactional(rollbackFor = Exception.class)
public class PatientDeviceService  {

	private Clock clock = Clock.DEFAULT;

	@Autowired
	private PatientDeviceDao patientDeviceDao;

	/**
	 * 保存患者设备
	 */
	public boolean saveDevice(PatientDevice patientDevice) throws Exception {
		//判断sn码是否被使用
		String sn = patientDevice.getDeviceSn();
		String type =  patientDevice.getCategoryCode();
		String userType = patientDevice.getUserType();
		if(userType == null)
		{
			userType = "-1";
			patientDevice.setUserType("-1");
		}

		boolean needVerify = true;
		//修改操作
		if(patientDevice.getId()!=null)
		{
			PatientDevice deviceOld =  patientDeviceDao.findOne(patientDevice.getId());
			if(deviceOld!=null)
			{
				if(deviceOld.getDeviceSn().equals(sn))
				{
					needVerify = false;
				}
			}
			else{
				throw new Exception("不存在该条记录！");
			}
		}

		//校验sn码是否被使用
		if(needVerify) {
			PatientDevice device = patientDeviceDao.findByDeviceSnAndCategoryCodeAndUserType(sn, type, userType);
			if (device != null && !device.getId().equals(patientDevice.getId())) {
				throw new Exception("sn码" + sn + "已被使用！");
			}
		}
		patientDevice.setCzrq(clock.getCurrentDate());
		//当前用户的身份证
//		patientDevice.setUserIdcard(patient.getIdCard());
		patientDeviceDao.save(patientDevice);

		return true;
	}

	/**
	 * 删除患者设备
	 */
	public void deleteDevice(String id) {
		patientDeviceDao.delete(Long.valueOf(id));
	}

	/**
	 * 患者设备列表接口（分页）
	 */
	public Page<PatientDevice> findByPatient(String patientCode, long id, int pageSize) {
		if (id < 0) {
			id = 0;
		}
		if (pageSize <= 0) {
			pageSize = 10;
		}
		// 排序
		Sort sort = new Sort(Direction.DESC, "id");
		// 分页信息
		PageRequest pageRequest = new PageRequest(0, pageSize, sort);

		// 设置查询条件
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		filters.put("patient", new SearchFilter("user", Operator.EQ, patientCode));
		if(id > 0){
			filters.put("id", new SearchFilter("id", Operator.LT, id));
		}

		Specification<PatientDevice> spec = DynamicSpecifications.bySearchFilter(filters.values(), PatientDevice.class);

		return patientDeviceDao.findAll(spec, pageRequest);
	}

	/**
	 * 查询患者已拥有的设备
	 * @param patient
	 * @return
	 */
	public Iterator<PatientDevice> findPatientHave(String patient) {
		return patientDeviceDao.findByUser(patient).iterator();
	}

	/**
	 * 获取患者设备信息
	 **/
	public PatientDevice findById(String id) {
		return patientDeviceDao.findOne(Long.valueOf(id));
	}



	/**
	 * 通过sn码获取设备绑定情况
	 **/
	public List<Map<String,String>> getDeviceUser(String openId, String user,String deviceSn,String type) throws Exception {
		List<Map<String,String>> re = new ArrayList<Map<String, String>>();
		List<PatientDevice> list =patientDeviceDao.findByDeviceSnAndCategoryCode(deviceSn,type);
		if(list!=null)
		{
			for(PatientDevice item : list)
			{
				Map<String,String> map = new HashMap<String, String>();
				map.put("type",item.getUserType());
				String userCode = item.getUser();
				if(user.equals(userCode))
				{
					map.put("others","0");
				}
				else{
					map.put("others","1");
				}
				Map<String,Object> result = new HashMap<>();
				//TODO 调用东软接口返回数据
//				openId = "OCEF9T2HW1GBY0KINQK0NEL_ZOSK";
				String userInfoXml = NeuSoftWebService.getSignDetailInfo(openId);
				List<Map<String,Object>> patientInfo = XMLUtil.xmltoList(userInfoXml);
				if (CollectionUtil.isNotEmpty(patientInfo)) {
					Map<String, Object> obj = patientInfo.get(0);
					//获取姓名
					map.put("name", (String) obj.get("SELFNAME"));
				}
				re.add(map);
			}
		}

		return re;
	}
}
