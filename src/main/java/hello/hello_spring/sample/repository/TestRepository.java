package hello.hello_spring.sample.repository;


import hello.hello_spring.sample.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    // 기본 CRUD 메서드 외에 사용자 정의 쿼리 메서드를 추가할 수 있습니다.
}
