package hello.hello_spring.sample.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "test_table")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    // 기본 생성자
    protected TestEntity() {}

    // 생성자
    public TestEntity(String name) {
        this.name = name;
    }

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
