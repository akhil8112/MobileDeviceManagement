package com.moveinsync.mdm.service;

import com.moveinsync.mdm.entity.AppVersion;
import com.moveinsync.mdm.repository.AppVersionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AppVersionService {

    private final AppVersionRepository repository;

    public AppVersionService(AppVersionRepository repository) {
        this.repository = repository;
    }

    public AppVersion addVersion(AppVersion version) {
        version.setReleaseTime(LocalDateTime.now());
        return repository.save(version);
    }

    public AppVersion getLatestVersion(String region) {
        return repository.findTopByRegionOrderByVersionCodeDesc(region)
                .orElse(null);
    }
}