package com.example.api.controller;

import com.example.api.service.GetRegionService;
import com.example.storage.RegionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class RegionInforController {
    private final GetRegionService getRegionService;
    private final RestTemplate restTemplate;

    @GetMapping("/region")
    public String getRegionInfor(@RequestParam String name) {
        RegionInfo regionInfo = getRegionService.execute(name);
        String dongCode = regionInfo.getDongCode();
        String modifiedDongCode = dongCode.substring(0, dongCode.length() - 2);

        String url = "https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_C_ADEMD_INFO&key=D2AE01EC-AFA5-3784-B701-8D224DDF3E24&domain=www.to-bagi.com&attrFilter=emd_cd:=:" + modifiedDongCode;

        String response = restTemplate.getForObject(url, String.class);
        // Handle the response as needed

        return response;
    }
}
