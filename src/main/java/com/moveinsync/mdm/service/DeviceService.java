//package com.moveinsync.mdm.service;
//
//import java.util.*;
//import com.moveinsync.mdm.entity.Device;
//import com.moveinsync.mdm.repository.DeviceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.moveinsync.mdm.dto.UpdateStatusRequest;
//;
//import com.moveinsync.mdm.entity.DeviceUpdate;
//import com.moveinsync.mdm.entity.UpdateStatus;
//
//import com.moveinsync.mdm.repository.DeviceUpdateRepository;
//
//
//
//import java.time.LocalDateTime;
//
//import java.util.Optional;
//import com.moveinsync.mdm.dto.HeartbeatRequest;
//import com.moveinsync.mdm.dto.HeartbeatResponse;
//import com.moveinsync.mdm.entity.AppVersion;
//import com.moveinsync.mdm.repository.AppVersionRepository;
//import com.moveinsync.mdm.repository.DeviceUpdateRepository;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import com.moveinsync.mdm.dto.UpdateStatusRequest;
//import com.moveinsync.mdm.entity.DeviceUpdate;
//
//@Service
//public class DeviceService {
//    private final DeviceRepository deviceRepository;
//    private final AppVersionRepository appVersionRepository;
//
//
//    @Autowired
//    private DeviceUpdateRepository deviceUpdateRepository;
//
//    public DeviceService(DeviceRepository deviceRepository,
//                         AppVersionRepository appVersionRepository) {
//
//        this.deviceRepository = deviceRepository;
//        this.appVersionRepository = appVersionRepository;
//    }
//    public Device registerDevice(Device device) {
//
//        device.setCreatedAt(LocalDateTime.now());
//        device.setLastOpenTime(LocalDateTime.now());
//
//        return deviceRepository.save(device);
//    }
//
//    public Optional<Device> getDevice(String deviceId) {
//
//        return deviceRepository.findByDeviceId(deviceId);
//    }
//    public String checkVersion(String imei, String currentVersion) {
//
//        Optional<Device> optionalDevice = deviceRepository.findByImei(imei);
//
//        if (optionalDevice.isEmpty()) {
//            return "Device not registered";
//        }
//
//        Device device = optionalDevice.get();
//
//        String latestVersion = device.getAppVersion();
//
//        if (latestVersion.equals(currentVersion)) {
//            return "App is up to date";
//        } else {
//            return "Update required. Latest version: " + latestVersion;
//        }
//    }
//    public List<Device> getDevicesByRegion(String region) {
//        return deviceRepository.findByRegion(region);
//    }
//
//
//    public HeartbeatResponse heartbeat(HeartbeatRequest request) {
//        Device device = deviceRepository
//                .findByDeviceId(request.getDeviceId())
//                .orElseThrow(() -> new RuntimeException("Device not found"));
//
//        device.setLastOpenTime(LocalDateTime.now());
//        deviceRepository.save(device);
//
//        AppVersion schedule =
//                appVersionRepository.findByRegion(device.getRegion())
//                        .orElse(null);
//
//        if (schedule != null) {
//
//            if (!request.getAppVersion().equals(schedule.getToVersion())) {
//
//                // 🔹 CREATE device_update RECORD HERE
//                DeviceUpdate update = new DeviceUpdate();
//                update.setDeviceId(request.getDeviceId());
//                update.setFromVersion(request.getAppVersion());
//                update.setToVersion(schedule.getToVersion());
//                update.setStatus(UpdateStatus.SCHEDULED);
//                update.setUpdatedAt(LocalDateTime.now());
//
//                deviceUpdateRepository.save(update);
//
//                return new HeartbeatResponse(
//                        true,
//                        schedule.getToVersion(),
//                        schedule.isMandatory()
//                );
//            }
//        }
//
//        return new HeartbeatResponse(
//                false,
//                request.getAppVersion(),
//                false
//        );
//
//    }
//    public void updateStatus(UpdateStatusRequest request) {
//
//        DeviceUpdate update = deviceUpdateRepository
//                .findTopByDeviceIdAndToVersionOrderByUpdatedAtDesc(
//                        request.getDeviceId(),
//                        request.getToVersion())
//                .orElseThrow(() -> new RuntimeException("Update record not found"));
//
//        update.setStatus(UpdateStatus.valueOf(request.getStatus()));
//        update.setUpdatedAt(LocalDateTime.now());
//
//        deviceUpdateRepository.save(update);
//
//        if(request.getStatus().equals("SUCCESS")) {
//
//            Device device = deviceRepository
//                    .findByDeviceId(request.getDeviceId())
//                    .orElseThrow(() -> new RuntimeException("Device not found"));
//
//            device.setAppVersion(request.getToVersion());
//
//            deviceRepository.save(device);
//        }
//    }
//}
package com.moveinsync.mdm.service;

import com.moveinsync.mdm.dto.HeartbeatRequest;
import com.moveinsync.mdm.dto.HeartbeatResponse;
import com.moveinsync.mdm.dto.UpdateStatusRequest;
import com.moveinsync.mdm.entity.Device;
import com.moveinsync.mdm.entity.DeviceUpdate;
import com.moveinsync.mdm.entity.UpdateSchedule;
import com.moveinsync.mdm.entity.UpdateStatus;
import com.moveinsync.mdm.repository.DeviceRepository;
import com.moveinsync.mdm.repository.DeviceUpdateRepository;
import com.moveinsync.mdm.repository.UpdateScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UpdateScheduleRepository updateScheduleRepository;

    @Autowired
    private DeviceUpdateRepository deviceUpdateRepository;

    public Device registerDevice(Device device) {
        device.setCreatedAt(LocalDateTime.now());
        device.setLastOpenTime(LocalDateTime.now());
        return deviceRepository.save(device);
    }

    public Device getDevice(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
    }

    public List<Device> getDevicesByRegion(String region) {
        return deviceRepository.findByRegionIgnoreCase(region);
    }

    public String checkVersion(String imei, String version) {
        return "OK";
    }

    public HeartbeatResponse heartbeat(HeartbeatRequest request) {

        Device device = deviceRepository.findByDeviceId(request.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        device.setLastOpenTime(LocalDateTime.now());
        device.setAppVersion(request.getAppVersion());
        deviceRepository.save(device);

        UpdateSchedule schedule = updateScheduleRepository
                .findTopByRegionAndFromVersionOrderByScheduledAtDesc(device.getRegion(), request.getAppVersion())
                .orElse(null);

        if (schedule == null) {
            return new HeartbeatResponse(false, request.getAppVersion(), false);
        }

        if (request.getAppVersion().equals(schedule.getToVersion())) {
            return new HeartbeatResponse(false, request.getAppVersion(), false);
        }

        DeviceUpdate du = new DeviceUpdate();
        du.setDeviceId(request.getDeviceId());
        du.setFromVersion(request.getAppVersion());
        du.setToVersion(schedule.getToVersion());
        du.setStatus(UpdateStatus.SCHEDULED);
        du.setUpdatedAt(LocalDateTime.now());
        deviceUpdateRepository.save(du);

        return new HeartbeatResponse(true, schedule.getToVersion(), Boolean.TRUE.equals(schedule.getMandatory()));
    }

    public void updateStatus(UpdateStatusRequest request) {

        DeviceUpdate update = deviceUpdateRepository
                .findTopByDeviceIdAndToVersionOrderByUpdatedAtDesc(request.getDeviceId(), request.getToVersion())
                .orElseThrow(() -> new RuntimeException("Update record not found"));

        update.setStatus(UpdateStatus.valueOf(request.getStatus()));
        update.setUpdatedAt(LocalDateTime.now());
        deviceUpdateRepository.save(update);

        if ("SUCCESS".equals(request.getStatus())) {
            Device device = deviceRepository.findByDeviceId(request.getDeviceId())
                    .orElseThrow(() -> new RuntimeException("Device not found"));
            device.setAppVersion(request.getToVersion());
            deviceRepository.save(device);
        }
    }
}