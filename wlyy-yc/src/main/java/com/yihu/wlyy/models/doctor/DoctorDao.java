package com.yihu.wlyy.models.doctor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;

/**
 * @created Airhead 2016/9/2.
 */
@Transactional
public interface DoctorDao extends CrudRepository<DoctorModel, Long> {
}
