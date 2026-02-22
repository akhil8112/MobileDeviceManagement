package com.moveinsync.mdm.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "update_schedule")
public class UpdateSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromVersion;

    private String toVersion;

    private String region;

    private LocalDateTime scheduledAt;

    private Boolean mandatory;

    public UpdateSchedule() {}

    public Long getId() { return id; }

    public String getFromVersion() { return fromVersion; }
    public void setFromVersion(String fromVersion) { this.fromVersion = fromVersion; }

    public String getToVersion() { return toVersion; }
    public void setToVersion(String toVersion) { this.toVersion = toVersion; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Boolean getMandatory() { return mandatory; }
    public void setMandatory(Boolean mandatory) { this.mandatory = mandatory; }
}