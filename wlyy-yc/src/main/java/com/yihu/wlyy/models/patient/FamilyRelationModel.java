package com.yihu.wlyy.models.patient;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @created Airhead 2016/9/2.
 */
@Entity
@Table(name = "fd_family_relation")
public class FamilyRelationModel extends IdModel {
    private String familyCode;  //可以为空
    private String personCode;
    private String relationPersonCode;
    private String relation;
    private Date updateTime;
    private Integer status;

    @Column(name="family_code")
    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    @Column(name="person_code")
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
