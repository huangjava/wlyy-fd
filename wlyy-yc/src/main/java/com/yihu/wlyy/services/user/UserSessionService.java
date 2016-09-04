package com.yihu.wlyy.services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.daos.UserSessionDao;
import com.yihu.wlyy.models.user.UserSessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserSessionService {
    @Autowired
    private UserSessionDao userSessionDao;
    @Autowired
    private UserDao userDao;

    public String loginInfo(String userCode) {
        UserSessionModel userSessionModel = userSessionDao.findOne(userCode);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        if (userSessionModel == null){

        }

        return null;
    }

    public String login(String userName, String password) {

        return null;
    }

    public String loginCallback() {
        return null;
    }
}
