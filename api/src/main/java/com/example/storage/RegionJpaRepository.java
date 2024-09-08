package com.example.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionJpaRepository extends JpaRepository<RegionInfo, Long> {
    Optional<RegionInfo> findByDongName(String dongName);
}
