package hello.hello_spring.tobagi.service;

import hello.hello_spring.tobagi.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//UserDetails 인터페이스를 구현하여 사용자 정보를 제공
public class PrincipalDetails implements UserDetails {

    // UserEntity 객체를 저장하는 필드
    private UserEntity userEntity;

    // 생성자에서 UserEntity 객체를 초기화
    public PrincipalDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    // 사용자의 권한을 반환하는 메서드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> userEntity.getRole().name());
        return collections;
    }

    // 사용자의 비밀번호를 반환하는 메서드
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    // 사용자의 로그인 ID를 반환하는 메서드
    @Override
    public String getUsername() {
        return userEntity.getLoginId();
    }

    // 계정이 만료되지 않았는지 여부를 반환하는 메서드 (true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠기지 않았는지 여부를 반환하는 메서드 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 여부를 반환하는 메서드 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용 가능) 상태인지 여부를 반환하는 메서드 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
}