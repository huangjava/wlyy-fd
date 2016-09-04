package com.yihu.wlyy.models.user;

import com.yihu.wlyy.models.common.IdEntity;

import javax.persistence.Column;
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

    @Column(name="user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name="token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name="token_ref")
    public String getTokenRef() {
        return tokenRef;
    }

    public void setTokenRef(String tokenRef) {
        this.tokenRef = tokenRef;
    }

    @Column(name="expire_time")
    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
