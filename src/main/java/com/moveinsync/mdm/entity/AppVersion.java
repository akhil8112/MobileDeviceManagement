package com.moveinsync.mdm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_version")
public class AppVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "version_name", nullable = false)
    private String versionName;

    @Column(name = "version_code", nullable = false)
    private Integer versionCode;

    private String region;

    private Boolean mandatory;

    private LocalDateTime releaseTime;

    public AppVersion() {}

    public Long getId() { return id; }

    public String getVersionName() { return versionName; }
    public void setVersionName(String versionName) { this.versionName = versionName; }

    public Integer getVersionCode() { return versionCode; }
    public void setVersionCode(Integer versionCode) { this.versionCode = versionCode; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Boolean getMandatory() { return mandatory; }
    public void setMandatory(Boolean mandatory) { this.mandatory = mandatory; }

    public LocalDateTime getReleaseTime() { return releaseTime; }
    public void setReleaseTime(LocalDateTime releaseTime) { this.releaseTime = releaseTime; }
}