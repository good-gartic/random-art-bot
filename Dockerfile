FROM openjdk:16-slim-buster

# Install node.js (used for building the vue app)
RUN apt-get update; apt-get install -y curl \
    && curl -sL https://deb.nodesource.com/setup_14.x | bash - \
    && apt-get install -y nodejs \
    && curl -L https://www.npmjs.com/install.sh | sh

# Create the /opt/app folder
RUN mkdir -p /opt/app
WORKDIR /opt/app

# Copy the whole application into /opt/app
COPY src src
COPY .env .
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle gradle
COPY gradlew .
COPY build.sh .

# Setup the web port
EXPOSE 8080

# And finally build and run the application
CMD ["./build.sh"]

