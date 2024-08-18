//package hello.tobagi.tobagi.login.dto;
//
//import hello.tobagi.tobagi.login.entity.Member;
//import hello.tobagi.tobagi.login.entity.MemberRole.MemberRole;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Builder
//public class JoinRequest {
//
//    private MemberRole role;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "ID를 입력하세요.")
//    private String email;
//
//    @NotBlank(message = "비밀번호를 입력하세요.")
//    private String password;
//    private String passwordCheck;
//
//    @NotBlank(message = "이름을 입력하세요.")
//    private String name;
//
//    public JoinRequest(MemberRole role, String email, String password, String passwordCheck, String name) {
//        this.role = role;
//        this.email = email;
//        this.password = password;
//        this.passwordCheck = passwordCheck;
//        this.name = name;
//    }
//
//
////    public Member toEntity() {
////        return Member.builder()
////                .email(email)
////                .password(password)
////                .name(name)
////                .role(role)
////                .build();
////    }
//}