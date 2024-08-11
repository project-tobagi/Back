package hello.hello_spring.tobagi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity

public class UserEntity {
    @Id
    private Long id;
    private String provider;
    private String providerId;
    private String loginId;
    private String name;
    private String password;
    private String nickname;
    private UserRole role;

    public enum UserRole {
        USER, ADMIN;
    }
    public String provider() {
        return provider;
    }
    public void providerId(String name) {
        this.providerId = name;
    }

    // Getters and Setters
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }

}