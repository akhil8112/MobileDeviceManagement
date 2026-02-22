package com.moveinsync.mdm.repository;

import com.moveinsync.mdm.entity.DeviceUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface DeviceUpdateRepository
        extends JpaRepository<DeviceUpdate, Long> {

    List<DeviceUpdate> findByDeviceId(String deviceId);

    Optional<DeviceUpdate> findTopByDeviceIdAndToVersionOrderByUpdatedAtDesc(String deviceId, String toVersion);

}