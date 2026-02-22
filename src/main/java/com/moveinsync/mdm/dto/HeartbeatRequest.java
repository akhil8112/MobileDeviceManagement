package com.moveinsync.mdm.dto;

public class HeartbeatRequest {

    private String deviceId;
    private String imei;
    private String appVersion;
    private String region;
    private String os;
    private String osVersion;
    private String model;

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public String getAppVersion() { return appVersion; }
    public void setAppVersion(String appVersion) { this.appVersion = appVersion; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }

    public String getOsVersion() { return osVersion; }
    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}