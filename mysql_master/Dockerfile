# 베이스 이미지
FROM openjdk:17-jdk

# 작업 디렉토리 생성
WORKDIR /app

# 애플리케이션 JAR 복사
COPY ./target/your-app.jar /app/app.jar

# 포트 설정
EXPOSE 8080

# 애플리케이션 실행
CMD ["java", "-jar", "/app/app.jar"]
