package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.patient.PatientModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/2.
 */
@Transactional
public interface PatientDao extends CrudRepository<PatientModel, Long> {

    // 根據患者標識查詢患者信息
    @Query("select p from PatientModel p where p.code=?1 and p.status=1")
    PatientModel findByCode(String code);
}
