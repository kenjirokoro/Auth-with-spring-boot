# Authentication with Spring Boot ![Build](https://img.shields.io/github/actions/workflow/status/frogindreams/Auth-with-spring-boot/gradle.yml?branch=develop)

Simple example of registration & login API. <b>Enjoy yourself!</b><br/>
But pay attention, there's no tests of this, for now and for this tiny project.

## What have I used 
I recommend you to see the build.gradle
```
- Java version "17.0.5" 2022-10-18 LTS
- Gradle 7.6
- PostgreSQL 14.3
- Spring v3.0.0
```

## How to run
You simply need clone it and then run this with you Gradle
```
$ gradle wrapper
$ ./gradlew b bootRun
```
Or you can run this via <b>-jar</b>
```
$ gradle wrapper && ./gradlew b
$ java -jar build/libs/<name-of-jar-compiled-file>.jar
```

## How to register a user
You simply need to send a <b>POST</b> request or <b>GET</b> request if you want to obtain some information. You can send any request via Postman or, in my case, I used common curl. Here's the one way to go:
```
$ curl -X POST http://localhost:8080/api/registration \
-H "Content-Type: application/json" \
-d '{"firstName": "Akane", "lastName": "Koro", "password": "user", "email": "frogharvard@gmail.com"}'
```
Remember, your goal link is <b>localhost:8080/api/registration</b>. After you have registered your user, you can login by following <b>that goal link</b> in your browser.

## Remember to change some database configuration
This guy
```
File: application.yml

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/<your-database>
    username: <your-db-username>
    password: <your-db-password-for-that-username>
```

## Documentation
Here's some awesome <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">documentation</a>.

Like <b>Auth-with-spring-boot</b>? Give a star on <a href="https://github.com/frogindreams/Auth-with-spring-boot/">Github</a>.
