package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.health.DevicePatientHealthIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface DevicePatientHealthIndexDao
		extends PagingAndSortingRepository<DevicePatientHealthIndex, Long>, JpaSpecificationExecutor<DevicePatientHealthIndex> {
	
	/**
	 * 按录入时间和患者标识查询健康记录
	 * @param patientCode
	 * @param date
	 * @return
	 */
	@Query("select a from DevicePatientHealthIndex a where a.user = ?1 and type = ?2 and a.recordDate = ?3 and a.del = '1'")
	Iterable<DevicePatientHealthIndex> findByPatienDate(String patientCode, int type, Date date);
	
	@Query("select a from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate between ?3 and ?4 and a.del = '1' group by recordDate order by recordDate asc")
	Iterable<DevicePatientHealthIndex> findByPatient(String user, int type, Date begin, Date end);
	
	@Query("select a.value1 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value1 > 0 order by recordDate desc")
	Page<String> findValue1ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value2 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value2 > 0 order by recordDate desc")
	Page<String> findValue2ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value3 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value3 > 0 order by recordDate desc")
	Page<String> findValue3ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value4 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value4 > 0 order by recordDate desc")
	Page<String> findValue4ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value5 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value5 > 0 order by recordDate desc")
	Page<String> findValue5ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value6 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value6 > 0 order by recordDate desc")
	Page<String> findValue6ByPatient(String user, int type, Date recordDate, Pageable pageRequest);
	
	@Query("select a.value7 from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate < ?3 and a.value7 > 0 order by recordDate desc")
	Page<String> findValue7ByPatient(String user, int type, Date recordDate, Pageable pageRequest);

	@Query("select a from DevicePatientHealthIndex a where a.user = ?1 and a.type = ?2 and a.recordDate >= ?3 and a.recordDate <= ?4 and a.del = '1' order by recordDate desc")
	Page<DevicePatientHealthIndex> findIndexByPatient(String patient, int type, Date start, Date end, Pageable pageRequest);

	@Query("SELECT a FROM DevicePatientHealthIndex a where a.user = ?1 order by a.recordDate desc ")
	Iterable<DevicePatientHealthIndex> findRecentByPatient(String patient);

	@Query("select DATE_FORMAT(a.recordDate,'%Y-%m-%d') from DevicePatientHealthIndex a where a.user = ?1 and a.recordDate >= ?2 and a.recordDate <= ?3 and a.del = '1' group by DATE_FORMAT(a.recordDate,'%Y-%m-%d') order by DATE_FORMAT(a.recordDate,'%Y-%m-%d') desc")
	List<String> findDateList(String patient, Date start, Date end, Pageable pageRequest);

	@Query("select a from DevicePatientHealthIndex a where a.user = ?1 and DATE_FORMAT(a.recordDate,'%Y-%m-%d') = ?2 order by a.recordDate asc")
	List<DevicePatientHealthIndex> findByDate(String patient, String date);
}
