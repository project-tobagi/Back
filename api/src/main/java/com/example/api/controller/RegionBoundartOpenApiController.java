//package com.example.api.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//@RestController
//public class RegionBoundartOpenApiController {
//
//
//    @Autowired
//    private AaService aaService;
//
//    @Autowired
//    private OpenAiService openAiService;
//
//    @GetMapping("/getDongCode")
//    public ResponseEntity<Map<String, String>> getDongCode(@RequestParam String dongName) {
//        // 1. 동 명칭으로 동코드 조회
//        String dongCode = aaService.getDongCodeByDongName(dongName);
//
//        // 2. 동코드로 OpenAI API 호출
//        Map<String, String> response = new HashMap<>();
//        if (dongCode != null) {
//            String openAiResponse = openAiService.callOpenAi(dongCode);
//            response.put("dongCode", dongCode);
//            response.put("openAiResponse", openAiResponse);
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("error", "Dong code not found.");
//            return ResponseEntity.status(404).body(response);
//        }
//    }
//}
//
