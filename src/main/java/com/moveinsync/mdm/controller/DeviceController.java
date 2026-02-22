package com.moveinsync.mdm.controller;
import com.moveinsync.mdm.dto.UpdateStatusRequest;
import com.moveinsync.mdm.entity.Device;
import com.moveinsync.mdm.service.DeviceService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.moveinsync.mdm.dto.HeartbeatRequest;
import com.moveinsync.mdm.dto.HeartbeatResponse;
import java.util.Optional;


import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/register")
    public Device registerDevice(@RequestBody Device device) {

        return deviceService.registerDevice(device);
    }

//    @GetMapping("/{deviceId}")
//    public Optional<Device> getDevice(@PathVariable String deviceId) {
//
//        return deviceService.getDevice(deviceId);
//    }
@GetMapping("/{deviceId}")
public Device getDevice(@PathVariable String deviceId) {
    return deviceService.getDevice(deviceId);
}
    @GetMapping("/check-version/{imei}/{version}")
    public ResponseEntity<String> checkVersion(
            @PathVariable String imei,
            @PathVariable String version) {

        String result = deviceService.checkVersion(imei, version);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/region/{region}")
    public List<Device> getDevicesByRegion(@PathVariable String region) {
        return deviceService.getDevicesByRegion(region);
    }


    @PostMapping("/heartbeat")
    public HeartbeatResponse heartbeat(@RequestBody HeartbeatRequest request) {

        return deviceService.heartbeat(request);

    }
    @PostMapping("/update-status")
    public ResponseEntity<String> updateStatus(
            @RequestBody UpdateStatusRequest request) {

        deviceService.updateStatus(request);

        return ResponseEntity.ok("Status updated successfully");
    }
}