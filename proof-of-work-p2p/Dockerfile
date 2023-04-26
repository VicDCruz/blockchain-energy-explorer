FROM debian:stable-20230411-slim

ARG JAVA_VERSION="22.3.r19-grl"
ARG MAVEN_VERSION="3.9.1"

RUN apt-get update && \
	apt-get install -y zip unzip curl && \
	rm -rf /var/lib/apt/lists/* && \
	rm -rf /tmp/*

# Install SDK Man
RUN curl -s "https://get.sdkman.io" | bash
RUN bash -c "source /root/.sdkman/bin/sdkman-init.sh && \
    sdk install java $JAVA_VERSION && \
    sdk install maven $MAVEN_VERSION && \
    rm -rf /root/.sdkman/archives/* && \
    rm -rf /root/.sdkman/tmp/*"

ENV MAVEN_HOME="/root/.sdkman/candidates/maven/current"
ENV JAVA_HOME="/root/.sdkman/candidates/java/current"
ENV PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

WORKDIR /code

COPY src ./src
COPY lib ./lib
COPY pom.xml .

RUN mvn install:install-file -Dfile=./lib/FreePastry-2.1.jar -DgroupId=free-pastry -DartifactId=FreePastry -Dversion=2.1 -DgeneratePom=true -Dpackaging=jar
RUN mvn package

CMD [ "java", "-jar", "target/proof-of-work-p2p-1.0-SNAPSHOT-jar-with-dependencies.jar" ]
