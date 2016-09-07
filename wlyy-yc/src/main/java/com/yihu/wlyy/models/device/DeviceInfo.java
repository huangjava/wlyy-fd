package com.yihu.wlyy.models.device;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lingfeng on 2016/8/19.
 */
@Entity
@Table(name = "wlyy_devices_data_record")
public class DeviceInfo extends IdModel {
    private String deviceData;
    private String deviceType;
    private Timestamp pushDate;
    private String status;
    // Constructors

    /** default constructor */
    public DeviceInfo() {
    }

    /** full constructor */
    public DeviceInfo(String deviceData, String deviceType,
                      Timestamp pushDate, String status) {
        this.deviceData = deviceData;
        this.deviceType = deviceType;
        this.pushDate = pushDate;
        this.status = status;
    }

    @Column(name = "device_data")
    public String getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(String deviceData) {
        this.deviceData = deviceData;
    }

    @Column(name = "device_type")
    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @Column(name = "push_date")
    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Timestamp pushDate) {
        this.pushDate = pushDate;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
