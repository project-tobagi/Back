//package com.example.api.controller;
//
//import com.example.api.service.GetBoundaryByNameService;
//import com.example.api.service.RegionBoundaryResponse;
//import com.example.api.util.UrlEncoderUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/regions")
//public class RegionBoundaryController {
//
//    private final GetBoundaryByNameService service;
//
//    public RegionBoundaryController(GetBoundaryByNameService service) {
//        this.service = service;
//    }
//
//    @GetMapping("/boundary")
//    public RegionBoundaryResponse getRegionBoundary(@RequestParam String name) {
//        String encodedName = UrlEncoderUtil.encodeValue(name);
//        return service.execute(encodedName);
//    }
//}
