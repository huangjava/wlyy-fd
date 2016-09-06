package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.device.PatientDevice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

;

public interface PatientDeviceDao extends PagingAndSortingRepository<PatientDevice, Long>, JpaSpecificationExecutor<PatientDevice> {

    @Query("select a from PatientDevice a where a.user = ?1")
	Iterable<PatientDevice> findByUser(String user);

	List<PatientDevice> findByDeviceSnAndCategoryCode(String deviceSn, String categoryCode);

	PatientDevice findByDeviceSnAndCategoryCodeAndUserType(String deviceSn, String categoryCode, String userType);
}
