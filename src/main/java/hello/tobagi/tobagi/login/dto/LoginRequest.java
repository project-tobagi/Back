package hello.tobagi.tobagi.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class LoginRequest {
    private String loginId;
    private String password;

    // Getter for loginId
    public String getLoginId() {
        return loginId;
    }

    // Setter for loginId
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}