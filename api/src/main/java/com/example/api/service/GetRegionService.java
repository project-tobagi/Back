package com.example.api.service;

import com.example.storage.RegionInfo;
import com.example.storage.RegionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetRegionService {
    private final RegionJpaRepository repository;

    public RegionInfo execute(final String name) {
        Optional<RegionInfo> byDongName = repository.findByDongName(name);
        if (byDongName.isPresent()) {
            return byDongName.get();
        }
        return new RegionInfo(1L, "none", "none");
    }
}
