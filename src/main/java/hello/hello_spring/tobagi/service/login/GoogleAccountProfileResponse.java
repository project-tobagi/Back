package hello.hello_spring.tobagi.service.login;


import lombok.Data;

@Data
public class GoogleAccountProfileResponse {
    private String id;
    private String email;
    private String name;
    private String picture;
}