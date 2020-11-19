# Person Service

## Database

To create a postgre sql database configured to be used by the service execute from the project root directory

`docker-compose -f infrastructure/docker-compose.yml up -d`

## Service

To start the service execute following command in the project root directory

`mvnw spring-boot:run`

## Requests

Below you find the curl commands to execute the rest api. Note that I developed and tested under Windows (Powershell), therefor the double quotes
You also can find the rest-api.http which I used to execute the commands directly from IntelliJ 

### Get all persons

curl -X GET http://localhost:8080/person

### Get person by id

curl -X GET http://localhost:8080/person/1

### Add a new person

curl -X POST -H "Content-Type: application/json" -d "{ ""firstName"": ""Max"", ""lastName"": ""Mustermann"", ""birthday"": ""2002-02-28"", ""gender"": ""Male"", ""size"": 180, ""weight"": 80 }" http://localhost:8080/person

### Update the person having the id appended to url

curl -X PUT -H "Content-Type: application/json" -d "{ ""firstName"": ""Erika"", ""lastName"": ""Mustermann"", ""birthday"": ""2003-06-15"", ""gender"": ""Female"", ""size"": 160, ""weight"": 60 }" http://localhost:8080/person/1

### Delete person by id

curl -X DELETE http://localhost:8080/person/1