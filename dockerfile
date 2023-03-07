FROM openjdk:19
LABEL maintainer="blue hydrogen"
ADD target/kafkaToKafka-0.0.1-SNAPSHOT.jar kstream-to-kstream
ENTRYPOINT ["java","-jar","kstream-to-kstream"]