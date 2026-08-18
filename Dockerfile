# 1단계: 빌드용 이미지 (Gradle 7/8, Java 17 기준)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /build

# 프로젝트 파일 전체 복사
COPY . .

# 윈도우/맥 환경 차이로 인한 gradlew 실행 권한 문제 방지 및 빌드 실행
RUN chmod +x gradlew
RUN ./gradlew clean bootJar

# 2단계: 실제 실행용 가벼운 이미지
FROM eclipse-temurin:17-jre
WORKDIR /app

# 빌드된 jar 파일 복사 (파일명에 맞게 자동 탐색)
COPY --from=builder /build/build/libs/*.jar app.jar

# 8080 포트 오픈
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]