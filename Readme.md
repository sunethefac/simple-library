# Restaurant Selection Application
This project was developed as a solution to **GovTech Technical Challenge**

## Requirements

For building and running the application you need:

- JDK 17
- Gradle
- PostgreSQL

## DataBase

You can either create PostgreSQL Database named `library` and connect to it by changing the datasource properties in `application.properties` file 
OR can use `application-dev.properties` file that uses H2 database by passing `spring.profiles.active=dev` property.

- You can set `spring.profiles.active=dev` property using IDE application run configuration
OR
- You can pass property to Jar file using following command

     `java -jar -Dspring.profiles.active=dev library-0.0.1-SNAPSHOT.jar`


## How to run the application

 - Clone the application from GitHub.
 - cd into the project folder
 - Run build command to build the project

    ``./gradlew build``


 - cd into `<project path>/build/libs` and run following command to run the spring boot app
   
    `java -jar library-0.0.1-SNAPSHOT.jar`

- To use "dev" settings

  `java -jar -Dspring.profiles.active=dev library-0.0.1-SNAPSHOT.jar`
- You can access APIs using following url

    `http://localhost:8080/`

## Postman
Postman collection for API requests can be found under `postman_collection` folder

## API Documentation

API documentation can be accessed using this URL `http://localhost:8080/swagger-ui/index.html`

