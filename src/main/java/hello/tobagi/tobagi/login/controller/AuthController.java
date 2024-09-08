//package hello.tobagi.tobagi.login.controller;
//
//import hello.tobagi.tobagi.login.entity.Member;
//import com.example.storage.MemberJpaRepository;
//import hello.tobagi.tobagi.login.entity.MemberRole.MemberRole;
//
//import io.github.cdimascio.dotenv.Dotenv;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.core.ParameterizedTypeReference;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
//    private final Dotenv dotenv;
//    private final com.example.storage.MemberJpaRepository memberRepository;
//    private final RestTemplate restTemplate;
//    private final JwtTokenGenerator jwtTokenGenerator;
//
//    @Autowired
//    public AuthController(Dotenv dotenv, MemberJpaRepository memberRepository, JwtTokenGenerator jwtTokenGenerator) {
//        this.dotenv = dotenv;
//        this.memberRepository = memberRepository;
//        this.restTemplate = new RestTemplate();
//        this.jwtTokenGenerator = jwtTokenGenerator;
//    }
//
//    @Operation(summary = "Google OAuth 2.0 Callback", description = "Handles Google OAuth 2.0 callback after user authentication.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Map.class)) }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = @Content)
//    })
//    @GetMapping("/google/callback")
//    public ResponseEntity<?> googleCallback(@RequestParam("code") String code) {
//        return handleCallback(code, "google");
//    }
//
//    @Operation(summary = "Naver OAuth 2.0 Callback", description = "Handles Naver OAuth 2.0 callback after user authentication.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Map.class)) }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = @Content)
//    })
//    @GetMapping("/naver/callback")
//    public ResponseEntity<?> naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
//        return handleCallback(code, "naver", state);
//    }
//
//    @Operation(summary = "Kakao OAuth 2.0 Callback", description = "Handles Kakao OAuth 2.0 callback after user authentication.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Successful operation",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Map.class)) }),
//            @ApiResponse(responseCode = "401", description = "Unauthorized",
//                    content = @Content)
//    })
//    @GetMapping("/kakao/callback")
//    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) {
//        return handleCallback(code, "kakao");
//    }
//
//    private ResponseEntity<?> handleCallback(String code, String provider) {
//        return handleCallback(code, provider, null);
//    }
//
//    private ResponseEntity<?> handleCallback(String code, String provider, String state) {
//        String accessToken;
//        try {
//            accessToken = requestAccessToken(code, provider, state);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve access token");
//        }
//
//        Map<String, Object> userInfo;
//        try {
//            userInfo = requestUserInfo(accessToken, provider);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user info");
//        }
//
//        Member member = createOrUpdateMember(userInfo, provider);
//        String jwtToken = jwtTokenGenerator.generateToken(member.getLoginId());
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("token", jwtToken);
//        result.put("user", userInfo);
//
//        return ResponseEntity.ok(result);
//    }
//
//    private String requestAccessToken(String code, String provider, String state) {
//        String clientId = dotenv.get(provider.toUpperCase() + "_CLIENT_ID");
//        String clientSecret = dotenv.get(provider.toUpperCase() + "_CLIENT_SECRET");
//        String redirectUri = dotenv.get(provider.toUpperCase() + "_REDIRECT_URI");
//        String accessTokenUrl = getAccessTokenUrl(provider);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.put("client_id", Collections.singletonList(clientId));
//        params.put("client_secret", Collections.singletonList(clientSecret));
//        params.put("code", Collections.singletonList(code));
//        params.put("redirect_uri", Collections.singletonList(redirectUri));
//        params.put("grant_type", Collections.singletonList("authorization_code"));
//        if (provider.equals("naver")) {
//            params.put("state", Collections.singletonList(state));
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
//                accessTokenUrl,
//                HttpMethod.POST,
//                entity,
//                new ParameterizedTypeReference<Map<String, Object>>() {}
//        );
//
//        Map<String, Object> responseBody = response.getBody();
//        if (responseBody == null || !responseBody.containsKey("access_token")) {
//            throw new IllegalStateException("Failed to retrieve access token");
//        }
//
//        return (String) responseBody.get("access_token");
//    }
//
//    private String getAccessTokenUrl(String provider) {
//        switch (provider) {
//            case "google":
//                return "https://oauth2.googleapis.com/token";
//            case "naver":
//                return "https://nid.naver.com/oauth2.0/token";
//            case "kakao":
//                return "https://kauth.kakao.com/oauth/token";
//            default:
//                throw new IllegalArgumentException("Unknown provider: " + provider);
//        }
//    }
//
//    private Map<String, Object> requestUserInfo(String accessToken, String provider) {
//        String userInfoUrl = getUserInfoUrl(provider);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
//                userInfoUrl,
//                HttpMethod.GET,
//                entity,
//                new ParameterizedTypeReference<Map<String, Object>>() {}
//        );
//
//        Map<String, Object> responseBody = response.getBody();
//        if (responseBody == null) {
//            throw new IllegalStateException("Failed to retrieve user info");
//        }
//
//        if ("naver".equals(provider)) {
//            // Extract the nested "response" object for Naver
//            responseBody = (Map<String, Object>) responseBody.get("response");
//        }
//        return responseBody;
//    }
//
//    private String getUserInfoUrl(String provider) {
//        switch (provider) {
//            case "google":
//                return "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
//            case "naver":
//                return "https://openapi.naver.com/v1/nid/me";
//            case "kakao":
//                return "https://kapi.kakao.com/v2/user/me";
//            default:
//                throw new IllegalArgumentException("Unknown provider: " + provider);
//        }
//    }
//
//    private Member createOrUpdateMember(Map<String, Object> userInfo, String provider) {
//        String loginId = provider + "_" + userInfo.get("id");
//        String name = (String) userInfo.get("name");
//        String email = (String) userInfo.get("email");
//
//        return createOrUpdateMember(loginId, name, email);
//    }
//
//    private Member createOrUpdateMember(String loginId, String name,String email) {
//        Member build = Member.builder()
//                .loginId(loginId)
//                .name(name)
//                .email(email)
//                .role(MemberRole.USER)
//                .build();
//        com.example.storage.Member member = com.example.storage.Member.builder()
//                .loginId(build.getLoginId())
//                .password(build.getPassword())
//                .name(build.getName())
//                .email(build.getEmail())
//                .role(com.example.storage.MemberRole.MemberRole.USER)
//                .build();
//        memberRepository.save(member);
//        return build;
//    }
//}
