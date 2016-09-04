package com.yihu.wlyy.models.patient;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/2.
 */
@Transactional
public interface FamilyRelationDao extends CrudRepository<FamilyRelationModel, Long> {
}
