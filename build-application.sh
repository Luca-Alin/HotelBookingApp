#!/bin/bash
kill -9 $(lsof -i:8080)
kill -9 $(lsof -i:4173)

mvn clean install -DskipTests
mvn spring-boot:run &

cd ui
npm install
npm run build
npm run preview