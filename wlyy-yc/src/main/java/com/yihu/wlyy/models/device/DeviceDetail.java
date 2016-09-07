package com.yihu.wlyy.models.device;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lingfeng on 2016/8/19.
 */
@Entity
@Table(name = "wlyy_devices")
public class DeviceDetail extends IdModel {
    private String deviceName;
    private String deviceModel;
    private String deviceCode;
    private String manufacturer;

    /** default constructor */
    public DeviceDetail() {
    }

    /** full constructor */
    public DeviceDetail(String deviceName, String deviceModel,
                        String deviceCode, String manufacturer) {
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
        this.deviceCode = deviceCode;
        this.manufacturer = manufacturer;
    }

    @Column(name = "device_name")
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Column(name = "device_model")
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    @Column(name = "device_code")
    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
