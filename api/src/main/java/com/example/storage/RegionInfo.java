package com.example.storage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;


@Entity
@Table(name = "legal_dong_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, name = "dong_nm")
    private String dongName;

    @Column(nullable = false, name = "dong_cd")
    private String dongCode;

}