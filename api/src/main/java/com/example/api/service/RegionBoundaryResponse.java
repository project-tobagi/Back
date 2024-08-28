package com.example.api.service;

import com.example.common.RegionBoundaryDto;

public record RegionBoundaryResponse(String name, String boundaryData) {
    public static RegionBoundaryResponse from(RegionBoundaryDto regionBoundary) {
        return new RegionBoundaryResponse(regionBoundary.getName(), regionBoundary.getBoundaryData());
    }
}