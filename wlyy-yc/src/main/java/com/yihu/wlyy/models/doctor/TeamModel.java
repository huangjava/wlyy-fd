package com.yihu.wlyy.models.doctor;

import java.util.List;

/**
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/9/5.
 */
public class TeamModel {

    private String name;
    private String code;
    private String icon;//团队图标（暂定）
    private String description;//团队介绍
    private String address;//团队地址（暂定）
    private List<DoctorModel> doctorList;//团队成员

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<DoctorModel> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<DoctorModel> doctorList) {
        this.doctorList = doctorList;
    }
}

