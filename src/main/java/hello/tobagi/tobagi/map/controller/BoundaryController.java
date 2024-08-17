package hello.tobagi.tobagi.map.controller;

import com.fasterxml.jackson.databind.JsonNode;
import hello.tobagi.tobagi.map.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoundaryController {

    @Autowired
    private BoundaryService boundaryService;

    @GetMapping("/boundary")
    public JsonNode getBoundary(@RequestParam double latitude, @RequestParam double longitude) {
        System.out.println("latitude: " + latitude + ", longitude: " + longitude);
        try {
            return boundaryService.findBoundaryByCoordinate(latitude, longitude);
        } catch (Exception e) {
            // Log the exception and return an appropriate response
            e.printStackTrace();
            // You can return a custom error response here
            return null;
        }
    }
}