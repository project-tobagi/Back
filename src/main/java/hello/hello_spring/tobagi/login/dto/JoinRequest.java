package hello.hello_spring.tobagi.login.dto;

import hello.hello_spring.tobagi.login.entity.Member;
import hello.hello_spring.tobagi.login.entity.MemberRole.MemberRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "ID를 입력하세요.")
    private String email;;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;
    public Member toEntity(){

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(MemberRole.USER)
                .build();
    }
}