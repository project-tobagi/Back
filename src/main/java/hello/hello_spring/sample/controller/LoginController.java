package hello.hello_spring.sample.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "redirect:https://tobagi.netlify.app/home";
    }

    @GetMapping("/error")
    public String handleError() {
        // 여기서 사용자 정의 오류 페이지로 리다이렉트 또는 메시지를 처리
        return "error"; // resources/templates/error.html 파일을 렌더링
    }
}
