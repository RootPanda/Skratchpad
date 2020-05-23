FROM adoptopenjdk:11-jre-hotspot
COPY ./out/artifacts/Skratchpad_jar/Skratchpad.jar /usr/bin/Skratchpad.jar
WORKDIR /usr/bin
CMD ["java", "-jar", "Skratchpad.jar"]
