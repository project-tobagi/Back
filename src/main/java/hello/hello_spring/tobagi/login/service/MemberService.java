package hello.hello_spring.tobagi.login.service;

import hello.hello_spring.tobagi.login.dto.JoinRequest;
import hello.hello_spring.tobagi.login.dto.LoginRequest;
import hello.hello_spring.tobagi.login.entity.Member;
import hello.hello_spring.tobagi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean checkLoginIdDuplicate(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }


    public void join(JoinRequest joinRequest) {
        memberRepository.save(joinRequest.toEntity());
    }

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