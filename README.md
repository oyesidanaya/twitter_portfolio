# Portfolio Java Web App
A simple portfolio Spring Java Web App that displays the profile image, name, some text with the experience and a 5 tweet list of the user’s Twitter timeline. 
## Requirements
* Java: JDK 11 or newer
* Maven: this is optional if you want to run the app through the maven wrapper mvnm

## Spring Modules
* Spring boot
* Spring data JPA
* Spring Web


## External libraries
* H2 database: To use a database in memory for the Integration Tests
* Mysql connector: To use a Mysql database for the persistence in the App
* Lombok: To generate code. https://projectlombok.org/
* Twitter4j: An unofficial Java library for the Twitter API. https://twitter4j.org/
* Springdoc: To generate the Api documentation. https://springdoc.org/
* Mustache: To use Logic-less templates to show in the frontend side. https://github.com/spullara/mustache.java

## Configuration
* Create a file called ``application-local.properties`` at the same level at ``application.properties``
* Add these properties with the appropriate values:\
  PORTFOLIO_DATABASE_URL=jdbc:mysql://{host}:{port}/{database} \
  PORTFOLIO_DATABASE_USER={database_user} \
  PORTFOLIO_DATABASE_PASSWORD={database_password} \
  TWITTER_API_KEY={a_valid_twitter_api_key} \
  TWITTER_API_SECRET_KEY={a_valid_twitter_api_secret_key} \
  TWITTER_ACCESS_TOKEN={a_valid_twitter_access_token} \
  TWITTER_ACCESS_TOKEN_SECRET={a_valid_twitter_access_token_secret} 

## Running Locally
Execute the command \
`./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=local"
` \
After the successful execution, you can check the application in http://localhost:8080/

## Checking the rest API
The Portfolio Java Web App is using the library springdoc at https://springdoc.org/ to generate the API documentation.

The Swagger ui will be available at http://localhost:8080/swagger-ui/index.html

## Duration
This implementation took me ~ 9 hours.