package com.example.storage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "subway_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubwayInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, name = "subway_line_nm")
    private String subwayLineName;

    @Column(nullable = false, name = "subway_station_nm")
    private String stationName;

    @Column(nullable = false, name = "subway_dong_nm")
    private String dongName;

    @Column(nullable = false, name = "subway_dong_cnt")
    private Long dongCount;

    @Column(nullable = false, name = "subway_dong_eval")
    private String dongEval;

}