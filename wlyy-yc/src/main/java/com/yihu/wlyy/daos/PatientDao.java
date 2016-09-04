package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.patient.PatientModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/2.
 */
@Transactional
public interface PatientDao extends CrudRepository<PatientModel, Long> {
}
