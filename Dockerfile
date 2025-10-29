# --- 阶段 1: 構建 ---
# 使用包含 Maven 和 JDK 的鏡像來編譯和打包應用
FROM maven:3.9-eclipse-temurin-21 AS build

# 更快的 Maven 預設參數
ENV MAVEN_CONFIG=/root/.m2 \
    MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# 設定工作目錄
WORKDIR /workspace

# 只複製 POM 先行下載依賴，最大化利用快取
COPY pom.xml ./
RUN mvn -B -e -DskipTests=true dependency:go-offline

# 先行複製前端相關檔（Vaadin/Vite），讓前端依賴可被單獨快取
COPY package*.json* ./
COPY vite.* ./
COPY tsconfig*.json ./
COPY types.d.ts ./
# 注意：不額外 COPY 可能不存在的 frontend 目錄，避免在無該目錄時導致建置失敗

# 嘗試預先準備 Vaadin 前端（可快取），若未配置則不阻塞建置
# 這一步會在 pom.xml 或前端檔案不變時命中快取，大幅縮短後續 package 時間
RUN mvn -B -DskipTests=true -Pproduction vaadin:prepare-frontend || echo "skip vaadin:prepare-frontend"

# 複製其餘原始碼並打包
COPY src ./src
RUN mvn -B -DskipTests=true -Pproduction package

# --- 階段 2: 運行 ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 建立非 root 使用者以增強安全性
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# 從 build 階段複製已打包的 JAR
COPY --from=build /workspace/target/CatFood-v1-0.0.1-SNAPSHOT.jar app.jar

# 曝露埠
EXPOSE 8080

# 啟動命令
ENTRYPOINT ["java", "-jar", "app.jar"]
