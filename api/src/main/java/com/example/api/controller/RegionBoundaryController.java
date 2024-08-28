package com.example.api.controller;

import com.example.api.service.GetBoundaryByNameService;
import com.example.api.service.RegionBoundaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionBoundaryController {

    private final GetBoundaryByNameService service;

    @GetMapping("{name}/boundary")
    public RegionBoundaryResponse getRegionBoundary(@PathVariable String name) {
        return service.execute(name);
    }
}
