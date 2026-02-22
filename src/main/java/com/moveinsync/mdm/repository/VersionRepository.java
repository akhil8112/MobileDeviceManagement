package com.moveinsync.mdm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moveinsync.mdm.entity.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

    Version findTopByOrderByIdDesc();

}