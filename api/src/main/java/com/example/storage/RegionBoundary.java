package com.example.storage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionBoundary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "feature_type")
    private String featureType;

    @Column(columnDefinition = "geometry(MultiPolygon, 3857)")
    private MultiPolygon geometry;

    @Column(columnDefinition = "jsonb")
    private String properties;
}