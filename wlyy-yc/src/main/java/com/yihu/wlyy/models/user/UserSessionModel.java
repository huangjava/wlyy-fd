package com.yihu.wlyy.models.user;

import com.yihu.wlyy.models.common.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @created Airhead 2016/9/2.
 */
@Entity
@Table(name="fd_user_session")
public class UserSessionModel extends IdEntity {
    private String userCode;
    private String token;
    private String tokenRef;
    private Date expireTime;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenRef() {
        return tokenRef;
    }

    public void setTokenRef(String tokenRef) {
        this.tokenRef = tokenRef;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
