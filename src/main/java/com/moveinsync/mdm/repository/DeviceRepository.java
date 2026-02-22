package com.moveinsync.mdm.repository;

import java.util.List;
import com.moveinsync.mdm.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByDeviceId(String deviceId);
    Optional<Device> findByImei(String imei);
    List<Device> findByRegionIgnoreCase(String region);
//    List<Device> findByRegion(String region);




}