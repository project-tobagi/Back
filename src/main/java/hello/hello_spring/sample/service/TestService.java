package hello.hello_spring.sample.service;

import hello.hello_spring.sample.entity.TestEntity;
import hello.hello_spring.sample.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // 모든 TestEntity 데이터 조회
    public List<TestEntity> getAllTestEntities() {
        return testRepository.findAll();
    }

    // ID로 TestEntity 조회
    public TestEntity getTestEntityById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    // 새로운 TestEntity 저장
    public TestEntity saveTestEntity(TestEntity testEntity) {
        return testRepository.save(testEntity);
    }
}
