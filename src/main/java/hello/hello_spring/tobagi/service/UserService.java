package hello.hello_spring.tobagi.service;

import hello.hello_spring.tobagi.entity.UserEntity;
import hello.hello_spring.tobagi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * userId(Long)를 입력받아 User을 return 해주는 기능
     * 인증, 인가 시 사용
     * userId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * userId로 찾아온 User가 존재하면 User return
     */
    public UserDetails getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return new PrincipalDetails(optionalUser.get());
    }


}
