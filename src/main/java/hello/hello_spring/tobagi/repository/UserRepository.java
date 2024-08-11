package hello.hello_spring.tobagi.repository;

import hello.hello_spring.tobagi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findByLoginId(String loginId);

}

