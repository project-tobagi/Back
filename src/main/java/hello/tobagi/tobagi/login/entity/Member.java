package hello.tobagi.tobagi.login.entity;

import hello.tobagi.tobagi.login.entity.MemberRole.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private MemberRole role;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {this.loginId = loginId;}

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}

