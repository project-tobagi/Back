package hello.hello_spring.tobagi.service.login;

import lombok.Data;

@Data
public class GoogleAccessTokenResponse {
    private String accessToken;
    private String tokenType;
    private String expiresIn;
    private String refreshToken;
    private String scope;
}