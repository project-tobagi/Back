package com.example.api.service;

import com.example.common.RegionBoundaryDto;
import com.example.common.RegionBoundaryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBoundaryByNameService {
    private final RegionBoundaryReader repository;

    public RegionBoundaryResponse execute(final String name) {
        final RegionBoundaryDto boundary = repository.findByName(name);
        return RegionBoundaryResponse.from(boundary);
    }
}
