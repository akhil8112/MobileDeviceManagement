package com.moveinsync.mdm.controller;

import com.moveinsync.mdm.entity.UpdateSchedule;
import com.moveinsync.mdm.service.UpdateScheduleService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/update")
public class UpdateScheduleController {

    private final UpdateScheduleService service;

    public UpdateScheduleController(UpdateScheduleService service) {
        this.service = service;
    }

    @PostMapping("/schedule")
    public UpdateSchedule schedule(
            @RequestBody UpdateSchedule schedule) {

        return service.scheduleUpdate(schedule);
    }

}