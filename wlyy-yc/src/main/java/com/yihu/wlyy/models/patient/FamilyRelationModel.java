package com.yihu.wlyy.models.patient;

import java.util.Date;

/**
 * @created Airhead 2016/9/2.
 */
public class FamilyRelationModel {
    private Integer id;
    private String familyCode;  //可以为空
    private String personCode;
    private String relationPersonCode;
    private String relation;
    private Date updateTime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    public String getRelationPersonCode() {
        return relationPersonCode;
    }

    public void setRelationPersonCode(String relationPersonCode) {
        this.relationPersonCode = relationPersonCode;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
