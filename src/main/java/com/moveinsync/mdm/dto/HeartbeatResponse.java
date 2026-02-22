package com.moveinsync.mdm.dto;

public class HeartbeatResponse {

    private boolean updateAvailable;
    private String latestVersion;
    private boolean mandatory;

    public HeartbeatResponse(boolean updateAvailable, String latestVersion, boolean mandatory) {
        this.updateAvailable = updateAvailable;
        this.latestVersion = latestVersion;
        this.mandatory = mandatory;
    }

    public boolean isUpdateAvailable() { return updateAvailable; }
    public String getLatestVersion() { return latestVersion; }
    public boolean isMandatory() { return mandatory; }
}