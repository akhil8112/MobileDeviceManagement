package com.moveinsync.mdm.repository;

import com.moveinsync.mdm.entity.UpdateSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UpdateScheduleRepository
        extends JpaRepository<UpdateSchedule, Long> {

    Optional<UpdateSchedule>
    findTopByRegionAndFromVersionOrderByScheduledAtDesc(
            String region, String fromVersion);

}