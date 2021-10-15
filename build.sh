#!/usr/bin/env bash

set -e

# Compile the frontend application
cd src/main/resources/app/
npm install
npm run build

# Create the resource directories
mkdir -p ../static
mkdir -p ../templates

# Clear the previously built resources
rm -rf ../static/*
rm -f ../templates/index.html

# Copy the compiled resources to /static folder
cp -R ./dist/* ../static

# Copy the index.html file to templates, as it will be rendered by controller
mv ../static/index.html ../templates/index.html

# Build and run the JVM application
cd ../../../..

# TODO: Load the environmental variables?

./gradlew bootRun