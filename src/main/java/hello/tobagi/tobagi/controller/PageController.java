package hello.tobagi.tobagi.controller;

import hello.tobagi.tobagi.map.entity.RegionBoundary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import hello.tobagi.tobagi.map.repository.RegionBoundaryRepository;

@Controller
public class PageController {

    private final Dotenv dotenv;
//    private final RegionBoundaryRepository administrativeDistrictRepository;
    @Autowired
    public PageController(Dotenv dotenv, RegionBoundaryRepository administrativeDistrictRepository) {
        this.dotenv = dotenv;
//        this.administrativeDistrictRepository = administrativeDistrictRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
        String googleRedirectUri = dotenv.get("GOOGLE_REDIRECT_URI");
        model.addAttribute("googleClientId", googleClientId);
        model.addAttribute("googleRedirectUri", googleRedirectUri);
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        String googleClientId = dotenv.get("GOOGLE_CLIENT_ID");
        String googleRedirectUri = dotenv.get("GOOGLE_REDIRECT_URI");
        model.addAttribute("googleClientId", googleClientId);
        model.addAttribute("googleRedirectUri", googleRedirectUri);
        return "login";
    }
//    @GetMapping("/boundary")
//    public String map() {
//        return "boundary";
//    }
    @GetMapping("/map")
    public String map() {
        return "map";
    }
    @GetMapping("/success")
    public String success(@AuthenticationPrincipal UserDetails userDetails, Model model) {
            model.addAttribute("user", userDetails);
        return "redirect:https://tobagi-dev.netlify.app/";
    }

    @GetMapping("/error")
    public String handleError() {
        return "error";
    }

//    @GetMapping("/getBoundary")
//    public String getBoundary(@RequestParam("name") String name, Model model) {
//        RegionBoundary district = administrativeDistrictRepository.findByName(name);
//        if (district != null) {
//            model.addAttribute("boundaryCoordinates", district.getBoundaryData());
//        } else {
//            model.addAttribute("boundaryCoordinates", "Not found");
//        }
//        return "boundary";
//    }

}