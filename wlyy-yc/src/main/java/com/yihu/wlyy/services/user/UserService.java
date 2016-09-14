package com.yihu.wlyy.services.user;

import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.models.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserModel get(String userCode) {
        return userDao.findOneByCode(userCode);
    }
}
