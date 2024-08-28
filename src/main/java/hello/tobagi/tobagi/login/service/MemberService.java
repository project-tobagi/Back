//package hello.tobagi.tobagi.login.service;
//
//import java.util.Optional;
//
//import org.springframework.stereotype.Service;
//
//import com.example.storage.MemberJpaRepository;
//
//import hello.tobagi.tobagi.login.entity.MemberRole.MemberRole;
//import hello.tobagi.tobagi.login.dto.LoginRequest;
//import hello.tobagi.tobagi.login.entity.Member;
//
//@Service
//
//public class MemberService {
//
//    private final MemberJpaRepository memberRepository;
//
//    public MemberService(MemberJpaRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//
//    public Member login(LoginRequest loginRequest) {
//        com.example.storage.Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());
//
//        if(findMember == null){
//            return null;
//        }
//
//        if (!findMember.getPassword().equals(loginRequest.getPassword())) {
//            return null;
//        }
//
//        MemberRole memberRole = MemberRole.valueOf(findMember.getRole().toString());
//
//        Member build = Member.builder()
//                .id(findMember.getId())
//                .loginId(findMember.getLoginId())
//                .name(findMember.getName())
//                .email(findMember.getEmail())
//                .role(memberRole)
//                .password(findMember.getPassword())
//                .build();
//        return build;
//    }
//
//    public Member getLoginMemberById(Long memberId){
//        if(memberId == null) return null;
//
//        Optional<com.example.storage.Member> findMember = memberRepository.findById(memberId);
//        com.example.storage.MemberRole.MemberRole role = findMember.get().getRole();
//        MemberRole memberRole = MemberRole.valueOf(role.toString());
//
//        Member build = Member.builder()
//                .id(findMember.get().getId())
//                .loginId(findMember.get().getLoginId())
//                .name(findMember.get().getName())
//                .email(findMember.get().getEmail())
//                .role(memberRole)
//                .password(findMember.get().getPassword())
//                .build();
//        return build;
//
//    }
//
//    public Member createMember(Member member) {
//        com.example.storage.Member memberEntity = com.example.storage.Member.builder()
//                .loginId(member.getLoginId())
//                .password(member.getPassword())
//                .name(member.getName())
//                .email(member.getEmail())
//                .role(com.example.storage.MemberRole.MemberRole.USER)
//                .build();
//        memberRepository.save(memberEntity);
//        return member;
//    }
//}