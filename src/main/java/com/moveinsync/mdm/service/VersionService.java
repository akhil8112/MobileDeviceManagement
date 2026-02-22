package com.moveinsync.mdm.service;

import com.moveinsync.mdm.entity.Version;
import com.moveinsync.mdm.repository.VersionRepository;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    private final VersionRepository versionRepository;

    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    public Version releaseVersion(Version version) {
        return versionRepository.save(version);
    }

    public Version getLatestVersion(String region) {
        return versionRepository.findTopByOrderByIdDesc();
    }
}