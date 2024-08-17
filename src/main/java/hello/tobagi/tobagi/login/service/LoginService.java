package hello.tobagi.tobagi.login.service;

import hello.tobagi.tobagi.login.entity.CustomOauth2UserDetails;
import hello.tobagi.tobagi.login.entity.GoogleUserDetails;
import hello.tobagi.tobagi.login.entity.Member;
import hello.tobagi.tobagi.login.entity.MemberRole.MemberRole;
import hello.tobagi.tobagi.login.entity.OAuth2UserInfo;
import hello.tobagi.tobagi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService extends DefaultOAuth2UserService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        // todo : 네이버, 카카오 추가
        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());

        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();

        Member findMember = memberRepository.findByLoginId(loginId);
        Member member;

        if (findMember == null) {
            member = Member.builder()
                    .loginId(loginId)
                    .name(name)
                    .email(email)
                    .build();
            memberRepository.save(member);
        } else{
            member = findMember;
        }

        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}