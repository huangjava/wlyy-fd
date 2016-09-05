package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.system.DictModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/4.
 */
@Transactional
public interface DictDao extends CrudRepository<DictModel, Long> {
    @Query("select a from DictModel a where a.code = ?1 and a.status = 1")
    DictModel findOne(String dictCode);
}
