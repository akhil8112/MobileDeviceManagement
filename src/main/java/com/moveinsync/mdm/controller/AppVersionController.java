package com.moveinsync.mdm.controller;

import com.moveinsync.mdm.entity.AppVersion;
import com.moveinsync.mdm.service.AppVersionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/version")
public class AppVersionController {

    private final AppVersionService service;

    public AppVersionController(AppVersionService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public AppVersion addVersion(@RequestBody AppVersion version) {
        return service.addVersion(version);
    }

    @GetMapping("/latest/{region}")
    public AppVersion latest(@PathVariable String region) {
        return service.getLatestVersion(region);
    }
}