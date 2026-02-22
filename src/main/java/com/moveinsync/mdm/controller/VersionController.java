package com.moveinsync.mdm.controller;

import com.moveinsync.mdm.entity.Version;
import com.moveinsync.mdm.service.VersionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/version")
public class VersionController {

    private final VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @PostMapping("/release")
    public Version release(@RequestBody Version version) {
        return versionService.releaseVersion(version);
    }

    @GetMapping("/latest")
    public Version latest(@RequestParam String region) {
        return versionService.getLatestVersion(region);
    }
}