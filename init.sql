-- init.sql

-- 데이터베이스 사용
USE my_database;

-- 테스트용 테이블 생성
CREATE TABLE test_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 초기 데이터 삽입
INSERT INTO test_table (name) VALUES ('Test Data 1'), ('Test Data 2'), ('Test Data 3');
