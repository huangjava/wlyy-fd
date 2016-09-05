package com.yihu.wlyy.services.user;

import com.yihu.wlyy.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
}
