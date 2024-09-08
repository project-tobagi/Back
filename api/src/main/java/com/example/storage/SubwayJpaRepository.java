package com.example.storage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubwayJpaRepository extends JpaRepository<SubwayInfo, Long> {
    List<SubwayInfo> findByDongNameContainingAndDongEval(String dongName, String dongEval);
}