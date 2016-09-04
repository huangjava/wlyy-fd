package com.yihu.wlyy.models.system;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/4.
 */
@Transactional
public interface DictEntriesDao extends CrudRepository<DictEntriesModel, Long> {
}
