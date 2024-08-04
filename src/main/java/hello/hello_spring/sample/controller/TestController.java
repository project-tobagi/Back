package hello.hello_spring.sample.controller;


import hello.hello_spring.sample.entity.TestEntity;
import hello.hello_spring.sample.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    // 모든 TestEntity 데이터 조회
    @GetMapping
    public List<TestEntity> getAllTestEntities() {
        return testService.getAllTestEntities();
    }

    // ID로 TestEntity 조회
    @GetMapping("/{id}")
    public TestEntity getTestEntityById(@PathVariable Long id) {
        return testService.getTestEntityById(id);
    }

    // 새로운 TestEntity 저장
    @PostMapping
    public TestEntity createTestEntity(@RequestBody TestEntity testEntity) {
        return testService.saveTestEntity(testEntity);
    }
}
