# 使用官方的 OpenJDK 8 镜像
FROM openjdk:8-jdk-alpine

# 安装字体和字体配置工具
RUN apk update && apk add --no-cache \
    fontconfig \
    ttf-dejavu

# 设置工作目录
WORKDIR /app

# 将构建好的 JAR 文件复制到容器中
COPY lx-admin/target/lx-admin.jar app.jar

ENV REDIS_PORT=6379

# 暴露应用运行的端口（根据你的项目设置）
EXPOSE 9300

# 设置运行时的命令
CMD ["java", "-jar", "app.jar"]