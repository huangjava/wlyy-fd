package com.yihu.wlyy.models.doctor;

import com.yihu.wlyy.models.common.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @created Airhead 2016/9/2.
 */
@Entity
@Table(name = "fd_doctor")
public class DoctorModel extends IdEntity {
//    private Integer id;
    private String personCode;
    private String orgName;
    private String orgCode;
    private String techTitle;
    private String deptName;
    private String deptCode;
    private String workHistory;
    private String educationalBackgroud;
    private String expertise;
    private String biography;

    @Column(name = "person_code")
    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
    }

    @Column(name = "org_name")
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Column(name = "org_code")
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "tech_title")
    public String getTechTitle() {
        return techTitle;
    }

    public void setTechTitle(String techTitle) {
        this.techTitle = techTitle;
    }

    @Column(name="dept_name")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name="dept_code")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name="work_history")
    public String getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(String workHistory) {
        this.workHistory = workHistory;
    }

    @Column(name="eductional_backgroud")
    public String getEducationalBackgroud() {
        return educationalBackgroud;
    }

    public void setEducationalBackgroud(String educationalBackgroud) {
        this.educationalBackgroud = educationalBackgroud;
    }

    @Column(name="expertise")
    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @Column(name="biography")
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
