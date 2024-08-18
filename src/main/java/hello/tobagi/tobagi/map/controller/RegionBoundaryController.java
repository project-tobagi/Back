package hello.tobagi.tobagi.map.controller;

import hello.tobagi.tobagi.map.entity.RegionBoundary;
import hello.tobagi.tobagi.map.service.RegionBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/regions")
public class RegionBoundaryController {

    @Autowired
    private RegionBoundaryService service;

    @GetMapping("/{name}/boundary")
    public Optional<RegionBoundary> getRegionBoundary(@PathVariable String name) {
        return service.getBoundaryByName(name);
    }
}
