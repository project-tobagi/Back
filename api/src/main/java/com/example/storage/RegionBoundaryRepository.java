package com.example.storage;

import com.example.common.RegionBoundaryDto;
import com.example.common.RegionBoundaryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegionBoundaryRepository implements RegionBoundaryReader {

    private final RegionBoundaryJpaRepository repository;

    @Override
    public RegionBoundaryDto findByName(String name) {
        GeojsonFeature byName = repository.findByName(name);
        return new RegionBoundaryDto(byName.getEmdKorNm(), byName.getBoundingBox());
    }
}