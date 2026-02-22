package com.moveinsync.mdm.service;

import java.util.*;
import com.moveinsync.mdm.entity.Device;
import com.moveinsync.mdm.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import com.moveinsync.mdm.dto.HeartbeatRequest;
import com.moveinsync.mdm.dto.HeartbeatResponse;
import com.moveinsync.mdm.entity.AppVersion;
import com.moveinsync.mdm.repository.AppVersionRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final AppVersionRepository appVersionRepository;
    @Autowired

    public DeviceService(DeviceRepository deviceRepository,
                         AppVersionRepository appVersionRepository) {

        this.deviceRepository = deviceRepository;
        this.appVersionRepository = appVersionRepository;
    }
    public Device registerDevice(Device device) {

        device.setCreatedAt(LocalDateTime.now());
        device.setLastOpenTime(LocalDateTime.now());

        return deviceRepository.save(device);
    }

    public Optional<Device> getDevice(String deviceId) {

        return deviceRepository.findByDeviceId(deviceId);
    }
    public String checkVersion(String imei, String currentVersion) {

        Optional<Device> optionalDevice = deviceRepository.findByImei(imei);

        if (optionalDevice.isEmpty()) {
            return "Device not registered";
        }

        Device device = optionalDevice.get();

        String latestVersion = device.getAppVersion();

        if (latestVersion.equals(currentVersion)) {
            return "App is up to date";
        } else {
            return "Update required. Latest version: " + latestVersion;
        }
    }
    public List<Device> getDevicesByRegion(String region) {
        return deviceRepository.findByRegion(region);
    }


    public HeartbeatResponse heartbeat(HeartbeatRequest request) {

        Optional<Device> optional = deviceRepository.findByDeviceId(request.getDeviceId());

        Device device;

        if(optional.isPresent()) {

            device = optional.get();
            device.setAppVersion(request.getAppVersion());
            device.setLastOpenTime(LocalDateTime.now());

        } else {

            device = new Device();

            device.setDeviceId(request.getDeviceId());
            device.setImei(request.getImei());
            device.setAppVersion(request.getAppVersion());
            device.setRegion(request.getRegion());
            device.setOs(request.getOs());
            device.setOsVersion(request.getOsVersion());
            device.setModel(request.getModel());
            device.setCreatedAt(LocalDateTime.now());
            device.setLastOpenTime(LocalDateTime.now());
        }

        deviceRepository.save(device);

        AppVersion latest = appVersionRepository
                .findTopByRegionOrderByVersionCodeDesc(request.getRegion())
                .orElse(null);

        if(latest == null)
            return new HeartbeatResponse(false, request.getAppVersion(), false);

        boolean updateAvailable =
                Integer.parseInt(request.getAppVersion().replace(".", ""))
                        < latest.getVersionCode();

        return new HeartbeatResponse(
                updateAvailable,
                latest.getVersionName(),
                latest.getMandatory()
        );
    }
}