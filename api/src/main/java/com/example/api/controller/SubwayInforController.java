package com.example.api.controller;

import com.example.api.service.GetSubwayService;
import com.example.storage.SubwayInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubwayInforController {
    private final GetSubwayService getSubwayService;

    @GetMapping("/subway")
    public List<SubwayInfo> getSubwayInfor(@RequestParam String name, @RequestParam String eval) {
        return getSubwayService.execute(name, eval);
    }
}