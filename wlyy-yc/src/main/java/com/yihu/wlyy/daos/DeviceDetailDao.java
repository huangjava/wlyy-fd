package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.device.DeviceDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DeviceDetailDao extends CrudRepository<DeviceDetail, Long> {
    @Query("select a from DeviceDetail a where a.deviceCode = ?1")
    Iterable<DeviceDetail> findByCode(String deviceCode);

}