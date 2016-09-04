package com.yihu.wlyy.models.user;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @created Airhead 2016/9/2.
 */
@Entity
@Table(name="fd_user")
public class UserModel  extends IdModel {
    private String code;
    private String name;
    private String mobile;
    private String salt;
    private Date createTime;
    private String photo;
    private String remark;
    private Integer gender;
    private String biography;
    private String personCode;
    private String externalIdentity;
    private Integer points;
    private String wechatOpenId;
    private Integer status;

    @Column(name="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name="salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name="create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Column(name = "biography")
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Column(name = "person_code")
    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    @Column(name = "external_identity")
    public String getExternalIdentity() {
        return externalIdentity;
    }

    public void setExternalIdentity(String externalIdentity) {
        this.externalIdentity = externalIdentity;
    }

    @Column(name = "points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Column(name = "wechat_open_id")
    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
