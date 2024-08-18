package hello.tobagi.tobagi.map.service;


import hello.tobagi.tobagi.map.entity.RegionBoundary;
import hello.tobagi.tobagi.map.repository.RegionBoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionBoundaryService {

    @Autowired
    private RegionBoundaryRepository repository;

    public Optional<RegionBoundary> getBoundaryByName(String name) {
        return Optional.ofNullable(repository.findByName(name));
    }
}