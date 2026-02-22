package com.moveinsync.mdm.service;

import com.moveinsync.mdm.entity.DeviceUpdate;
import com.moveinsync.mdm.entity.UpdateStatus;
import com.moveinsync.mdm.repository.DeviceUpdateRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceUpdateService {

    private final DeviceUpdateRepository repository;

    public DeviceUpdateService(DeviceUpdateRepository repository) {
        this.repository = repository;
    }

    public DeviceUpdate createUpdate(
            String deviceId,
            String fromVersion,
            String toVersion) {

        DeviceUpdate update = new DeviceUpdate();

        update.setDeviceId(deviceId);
        update.setFromVersion(fromVersion);
        update.setToVersion(toVersion);
        update.setStatus(UpdateStatus.SCHEDULED);
        update.setUpdatedAt(LocalDateTime.now());

        return repository.save(update);
    }

    public DeviceUpdate updateStatus(
            Long id,
            UpdateStatus status,
            String reason) {

        DeviceUpdate update =
                repository.findById(id).orElseThrow();

        update.setStatus(status);
        update.setUpdatedAt(LocalDateTime.now());

        if(status == UpdateStatus.FAILED)
            update.setFailureReason(reason);

        return repository.save(update);
    }

    public List<DeviceUpdate> getDeviceUpdates(String deviceId) {

        return repository.findByDeviceId(deviceId);
    }
}