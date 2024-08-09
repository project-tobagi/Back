package hello.hello_spring.tobagi.controller;


import hello.hello_spring.tobagi.data.CrimeData;
import hello.hello_spring.tobagi.service.CrimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crime")
public class CrimeController {

    private final CrimeService crimeService;

    public CrimeController(CrimeService crimeService) {
        this.crimeService = crimeService;
    }

    @GetMapping
    public List<CrimeData> getCrimeData() {
        return crimeService.getCrimeData();
    }
}
