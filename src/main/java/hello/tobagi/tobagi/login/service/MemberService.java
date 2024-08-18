package hello.tobagi.tobagi.login.service;

//import hello.tobagi.tobagi.login.dto.JoinRequest;
import hello.tobagi.tobagi.login.dto.LoginRequest;
import hello.tobagi.tobagi.login.entity.Member;
import hello.tobagi.tobagi.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }


//    public void join(JoinRequest joinRequest) {
//        memberRepository.save(joinRequest.toEntity());
//    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByLoginId(loginRequest.getLoginId());

        if(findMember == null){
            return null;
        }

        if (!findMember.getPassword().equals(loginRequest.getPassword())) {
            return null;
        }

        return findMember;
    }

    public Member getLoginMemberById(Long memberId){
        if(memberId == null) return null;

        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);

    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}