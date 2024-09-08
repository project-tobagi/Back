//package com.example.storage;
//
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import org.locationtech.jts.geom.MultiPolygon;
//
//
//@Entity
//@Table(name = "geojson_features")
//
//@Getter
//@Setter
//public class GeojsonFeature {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
////    @Column(columnDefinition = "geometry(MultiPolygon, 3857)")
////    private MultiPolygon geometry;
////
////    @Column(columnDefinition = "jsonb")
////    private String properties;
//
//    @Column(name = "bounding_box", columnDefinition = "jsonb")
//    private String boundingBox; // Store as JSON string
//
//    @Column(name = "emd_kor_nm")
//    private String name;
//
//
//    public String getEmdKorNm() {
//        // Parse properties JSON and return the correct field
//        return name;
//    }
//
//    public String getBoundingBox() {
//        return boundingBox;
//    }
//
//
//}