package com.yihu.wlyy.models.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @created Airhead 2016/9/4.
 */
@Transactional
public interface UserDao extends CrudRepository<UserModel, Long> {
    @Query("select a from UserModel a where a.name = ?1")
    UserModel findOne(String userName);
}
