package com.example.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionBoundaryJpaRepository extends JpaRepository<GeojsonFeature, Long> {
    @Query(
            value = "SELECT id, " +
                    "properties->>'EMD_KOR_NM' AS emd_kor_nm, " +
                    "ST_AsText(ST_Envelope(ST_Collect(geometry))) AS bounding_box " +
                    "FROM geojson_features " +
                    "WHERE properties->>'EMD_KOR_NM' = :name " +
                    "GROUP BY id, properties->>'EMD_KOR_NM'",
            nativeQuery = true
    )
    GeojsonFeature findByName(@Param("name") String name);
}
