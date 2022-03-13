FROM openjdk:8

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./target/*pay.jar /app/mall-pay.jar

WORKDIR /app/

ENV PARAMS=""

ENTRYPOINT ["sh","-c","java $PARAMS -jar mall-pay.jar"]