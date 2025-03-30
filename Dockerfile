# 1. Base image (JDK 17)
FROM openjdk:21-slim

ENV TZ=Asia/Seoul

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 4. 컨테이너에서 실행할 명령어 설정
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
