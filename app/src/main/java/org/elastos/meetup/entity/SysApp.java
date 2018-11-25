package org.elastos.meetup.entity;

import java.io.Serializable;

public class SysApp implements Serializable {
    private static SysApp sysApp;
    public static SysApp getInstance(){
        if(sysApp==null){
            sysApp = new SysApp();
        }
        return sysApp;
    }
    private String id;

    private String appType;

    private String appName;

    private String versionName;

    private String versionCode;

    private String forceUpgrade;

    private String description;

    private String downloadUrl;


    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode == null ? null : versionCode.trim();
    }

    public String getForceUpgrade() {
		return forceUpgrade;
	}

	public void setForceUpgrade(String forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }




}