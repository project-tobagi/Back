package hello.tobagi.tobagi.login.entity;

import hello.tobagi.tobagi.login.entity.MemberRole.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String email;
    private MemberRole role;
}

