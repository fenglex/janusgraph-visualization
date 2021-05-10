FROM openjdk:8-jre-alpine
MAINTAINER fenglex@126.com
WORKDIR /app

ARG JAR_FILE=target/janusgraph-visualization.jar
ARG TIME_ZONE=Asia/Shanghai

ENV TZ=${TIME_ZONE}
ENV JAVA_OPTS="-Xms128m -Xmx512m"

COPY ${JAR_FILE} janusgraph-visualization.jar
EXPOSE 8888
ENTRYPOINT java ${JAVA_OPTS} -jar janusgraph-visualization.jar
