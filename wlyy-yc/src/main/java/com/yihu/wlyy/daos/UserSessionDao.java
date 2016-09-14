package com.yihu.wlyy.daos;

import com.yihu.wlyy.models.user.UserSessionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/4.
 */
@Transactional
public interface UserSessionDao extends CrudRepository<UserSessionModel, Long> {

    @Query("select a from UserSessionModel a where a.userCode = ?1")
    UserSessionModel findOne(String userCode);
}
