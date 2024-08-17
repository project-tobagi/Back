package hello.tobagi.tobagi.login.controller;

import hello.tobagi.tobagi.login.entity.Member;
import hello.tobagi.tobagi.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/google")
public class AuthController {

    private final Dotenv dotenv;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public AuthController(Dotenv dotenv, MemberRepository memberRepository, JwtTokenGenerator jwtTokenGenerator) {
        this.dotenv = dotenv;
        this.memberRepository = memberRepository;
        this.restTemplate = new RestTemplate();
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Operation(summary = "Google OAuth 2.0 Callback", description = "Handles Google OAuth 2.0 callback after user authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @GetMapping("/callback")
    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
        String accessToken;
        try {
            accessToken = requestAccessToken(code);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve access token");
        }

        Map<String, Object> userInfo;
        try {
            userInfo = requestUserInfo(accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user info");
        }

        Member member = createOrUpdateMember(userInfo);
        String jwtToken = jwtTokenGenerator.generateToken(member.getLoginId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", jwtToken);
        result.put("user", userInfo);

        return ResponseEntity.ok(result);
    }
//    @PostMapping("/login")
//    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> request) {
//        String loginId = request.get("loginId");
//        String name = request.get("name");
//
//        Member member = createOrUpdateMember(loginId, name);
//        String jwtToken = jwtTokenGenerator.generateToken(loginId);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("token", jwtToken);
//        result.put("user", member);
//
//        return ResponseEntity.ok(result);
//    }

    private String requestAccessToken(String code) {
        String clientId = dotenv.get("GOOGLE_CLIENT_ID");
        String clientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");
        String redirectUri = dotenv.get("GOOGLE_REDIRECT_URI");
        String accessTokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("client_id", Collections.singletonList(clientId));
        params.put("client_secret", Collections.singletonList(clientSecret));
        params.put("code", Collections.singletonList(code));
        params.put("redirect_uri", Collections.singletonList(redirectUri));
        params.put("grant_type", Collections.singletonList("authorization_code"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                accessTokenUrl,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null || !responseBody.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token");
        }

        return (String) responseBody.get("access_token");
    }

    private Map<String, Object> requestUserInfo(String accessToken) {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        Map<String, Object> responseBody = response.getBody();
        if (responseBody == null) {
            throw new IllegalStateException("Failed to retrieve user info");
        }

        return responseBody;
    }

    private Member createOrUpdateMember(Map<String, Object> userInfo) {
        String loginId = (String) userInfo.get("id");
        String name = (String) userInfo.get("name");

        return createOrUpdateMember(loginId, name);
    }

    private Member createOrUpdateMember(String loginId, String name) {
        Optional<Member> existingMember = Optional.ofNullable(memberRepository.findByLoginId(loginId));
        Member member;
        if (existingMember.isPresent()) {
            member = existingMember.get();
            member.setName(name);
        } else {
            member = new Member();
            member.setLoginId(loginId);
            member.setName(name);
        }

        memberRepository.save(member);
        return member;
    }
}
