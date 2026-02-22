package com.moveinsync.mdm.service;

import com.moveinsync.mdm.entity.UpdateSchedule;
import com.moveinsync.mdm.repository.UpdateScheduleRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UpdateScheduleService {

    private final UpdateScheduleRepository repository;

    public UpdateScheduleService(UpdateScheduleRepository repository) {
        this.repository = repository;
    }

    public UpdateSchedule scheduleUpdate(UpdateSchedule schedule) {

        int from = Integer.parseInt(schedule.getFromVersion().replace(".", ""));
        int to = Integer.parseInt(schedule.getToVersion().replace(".", ""));

        if(to <= from)
            throw new RuntimeException("Downgrade not allowed");

        schedule.setScheduledAt(LocalDateTime.now());

        return repository.save(schedule);
    }

    public UpdateSchedule getSchedule(String region, String version) {

        return repository
                .findTopByRegionAndFromVersionOrderByScheduledAtDesc(
                        region, version)
                .orElse(null);
    }
}