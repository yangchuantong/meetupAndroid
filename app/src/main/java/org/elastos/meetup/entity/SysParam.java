package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class SysParam implements Serializable {

    private String configKey;

    private String configValue;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey == null ? null : configKey.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    private static Map<String,String> sysmap;

    public static Map<String, String> getSysmap() {
        return sysmap;
    }

    public static void setSysmap(Map<String, String> sysmap) {
        SysParam.sysmap = sysmap;
    }
}