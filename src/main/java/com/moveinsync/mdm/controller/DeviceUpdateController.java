package com.moveinsync.mdm.controller;

import com.moveinsync.mdm.entity.DeviceUpdate;
import com.moveinsync.mdm.entity.UpdateStatus;
import com.moveinsync.mdm.service.DeviceUpdateService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-update")
public class DeviceUpdateController {

    private final DeviceUpdateService service;

    public DeviceUpdateController(DeviceUpdateService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public DeviceUpdate create(
            @RequestParam String deviceId,
            @RequestParam String fromVersion,
            @RequestParam String toVersion) {

        return service.createUpdate(deviceId, fromVersion, toVersion);
    }

    @PutMapping("/status")
    public DeviceUpdate updateStatus(
            @RequestParam Long id,
            @RequestParam UpdateStatus status,
            @RequestParam(required = false) String reason) {

        return service.updateStatus(id, status, reason);
    }

    @GetMapping("/{deviceId}")
    public List<DeviceUpdate> getUpdates(
            @PathVariable String deviceId) {

        return service.getDeviceUpdates(deviceId);
    }
}