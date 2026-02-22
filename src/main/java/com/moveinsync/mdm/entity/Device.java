package com.moveinsync.mdm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    private String imei;

    @Column(name = "app_version")
    private String appVersion;

    private String model;

    private String os;

    @Column(name = "os_version")
    private String osVersion;

    private String region;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_open_time")
    private LocalDateTime lastOpenTime;

    public Device() {}

    public Long getId() { return id; }

    public String getDeviceId() { return deviceId; }

    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getImei() { return imei; }

    public void setImei(String imei) { this.imei = imei; }

    public String getAppVersion() { return appVersion; }

    public void setAppVersion(String appVersion) { this.appVersion = appVersion; }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getOs() { return os; }

    public void setOs(String os) { this.os = os; }

    public String getOsVersion() { return osVersion; }

    public void setOsVersion(String osVersion) { this.osVersion = osVersion; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastOpenTime() { return lastOpenTime; }

    public void setLastOpenTime(LocalDateTime lastOpenTime) { this.lastOpenTime = lastOpenTime; }
}