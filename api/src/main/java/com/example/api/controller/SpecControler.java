package com.example.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecControler {

    @GetMapping("/transportation")
    public String transportation() {
        return "['서울특별시 강서구 방화동','서울특별시 강서구 공항동']";
    }
}
