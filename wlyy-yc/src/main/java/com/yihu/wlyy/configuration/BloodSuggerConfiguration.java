package com.yihu.wlyy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @created Airhead 2016/8/1.
 */
@Configuration
public class BloodSuggerConfiguration {
    @Value("${bloodSugger.fasting}")
    private String fasting;
    @Value("${bloodSugger.afterBreakfast}")
    private String afterBreakfast;
    @Value("${bloodSugger.beforeLunch}")
    private String beforeLunch;
    @Value("${bloodSugger.afterLunch}")
    private String afterLunch;
    @Value("${bloodSugger.beforeDinner}")
    private String beforeDinner;
    @Value("${bloodSugger.afterDinner}")
    private String afterDinner;
    @Value("${bloodSugger.beforeSleep}")
    private String beforeSleep;

    public String getFasting() {
        return fasting;
    }

    public void setFasting(String fasting) {
        this.fasting = fasting;
    }

    public String getAfterBreakfast() {
        return afterBreakfast;
    }

    public void setAfterBreakfast(String afterBreakfast) {
        this.afterBreakfast = afterBreakfast;
    }

    public String getBeforeLunch() {
        return beforeLunch;
    }

    public void setBeforeLunch(String beforeLunch) {
        this.beforeLunch = beforeLunch;
    }

    public String getAfterLunch() {
        return afterLunch;
    }

    public void setAfterLunch(String afterLunch) {
        this.afterLunch = afterLunch;
    }

    public String getBeforeDinner() {
        return beforeDinner;
    }

    public void setBeforeDinner(String beforeDinner) {
        this.beforeDinner = beforeDinner;
    }

    public String getAfterDinner() {
        return afterDinner;
    }

    public void setAfterDinner(String afterDinner) {
        this.afterDinner = afterDinner;
    }

    public String getBeforeSleep() {
        return beforeSleep;
    }

    public void setBeforeSleep(String beforeSleep) {
        this.beforeSleep = beforeSleep;
    }
}
