FROM openjdk:8-jre-alpine

WORKDIR /app

ARG JAR_FILE=target/janusgraph-visualization-0.1.0.jar
ARG TIME_ZONE=Asia/Shanghai

ENV TZ=${TIME_ZONE}
ENV JAVA_OPTS="-Xms128m -Xmx512dockerm"

COPY ${JAR_FILE} app.jar
EXPOSE 8888
ENTRYPOINT java ${JAVA_OPTS} -jar app.jar