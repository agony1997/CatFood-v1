# --- 阶段 1: 构建 ---
# 使用包含 Maven 和 JDK 的镜像来编译和打包应用
FROM maven:3.9-eclipse-temurin-21 AS build

# 设置工作目录
WORKDIR /workspace

# 复制 pom.xml 并下载依赖，利用 Docker 的层缓存机制
COPY pom.xml .
RUN mvn dependency:go-offline

# 复制源代码并执行打包
COPY src ./src
RUN mvn package -DskipTests

# --- 阶段 2: 运行 ---
# 使用更小的 JRE 镜像作为最终的运行环境
FROM eclipse-temurin:21-jre
WORKDIR /app

# 创建一个非 root 用户来运行应用，增强安全性
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# 从 'build' 阶段复制已打包的 JAR 文件
COPY --from=build /workspace/target/CatFood-v1-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口（可选，但推荐）
EXPOSE 8080

# 定义启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
