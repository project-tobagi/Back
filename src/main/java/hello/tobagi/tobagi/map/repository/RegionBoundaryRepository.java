package hello.tobagi.tobagi.map.repository;

import hello.tobagi.tobagi.map.entity.RegionBoundary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionBoundaryRepository extends JpaRepository<RegionBoundary, Long> {
    RegionBoundary findByName(String name);
}
