package com.yihu.wlyy.services.device;


import com.yihu.wlyy.daos.DeviceCategoryDao;
import com.yihu.wlyy.daos.DeviceDao;
import com.yihu.wlyy.models.device.Device;
import com.yihu.wlyy.models.device.DeviceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备管理
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DeviceService  {


	@Autowired
	private DeviceCategoryDao deviceCategoryDao;

	@Autowired
	private DeviceDao deviceDao;


	/**
	 * 查询所有的设备类型
	 * @return
	 */
	public List<DeviceCategory> findAllCategory() {
		return deviceCategoryDao.findAll();
	}


	/**
	 * 查询设备信息
	 */
	public List<Device> findDeviceByCategory(String categoryCode) {
		return deviceDao.findByCategoryCode(categoryCode);
	}

	/**
	 * 获取设备信息
     */
	public Device findById(String id)
	{
		return deviceDao.findOne(Long.valueOf(id));
	}
}
