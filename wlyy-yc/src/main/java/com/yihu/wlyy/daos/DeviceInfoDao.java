package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.device.DeviceInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeviceInfoDao extends CrudRepository<DeviceInfo, Long> {
}