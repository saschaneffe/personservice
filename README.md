# Requests (tested on Windows)

### Get all persons

curl -X GET http://localhost:8080/person

### Get person by id

curl -X GET http://localhost:8080/person/1

### Add a new person

curl -X POST -H "Content-Type: application/json" -d "{ ""firstName"": ""Max"", ""lastName"": ""Mustermann"", ""birthday"": ""2002-02-28"", ""gender"": ""Male"", ""size"": 180, ""weight"": 80 }" http://localhost:8080/person

### Change a person

curl -X PUT -H "Content-Type: application/json" -d "{ ""firstName"": ""Erika"", ""lastName"": ""Mustermann"", ""birthday"": ""2003-06-15"", ""gender"": ""Female"", ""size"": 160, ""weight"": 60 }" http://localhost:8080/person/1

### Delete person by id

curl -X DELETE http://localhost:8080/person/1