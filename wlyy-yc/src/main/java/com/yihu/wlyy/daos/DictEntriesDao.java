package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.system.DictEntriesModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/4.
 */
@Transactional
public interface DictEntriesDao extends CrudRepository<DictEntriesModel, Long> {
}
