FROM adoptopenjdk:11-jre-hotspot
COPY ./build/libs/Skratchpad-1.0.jar /usr/bin/Skratchpad.jar
WORKDIR /usr/bin
CMD ["java", "-cp", "Skratchpad.jar", "ca.alexleung.skratchpad.SkratchpadKt"]
