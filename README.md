# Camunda Spring Boot example
Simple Spring Boot application integrating [Camunda](https://camunda.com/) engine.
It has a REST endpoint that receives the process key to start. Additionally, it uses the [FEEL Scala Plugin](https://github.com/camunda/feel-scala) defining a custom function.

## Requirements
* Gradle

## Run on GNU/Linux
```
./gradlew bootRun
```
## Run on Windows
```
./gradlew.bat bootRun
```

## Example of a process start call
Given a simple process that does nothing, available in `/src/test/resources/test-processes-1` and it's already deployed on the engine,
executing the following command making the REST call
```bash
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST http://localhost:8080/start/synchronous_echo_test -d '{}'
```
returns something like:
```
HTTP/1.1 200
Set-Cookie: JSESSIONID=74B731A2F988736CADC09C5C06073695; Path=/; HttpOnly
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 28 Jan 2019 11:58:07 GMT

{"id":"f9e56b9c-22f3-11e9-8a6f-02426f783a63","businessKey":"0185311d-fad5-470d-9d96-5b8f61b60dd1","
processDefinitionId":"synchronous_echo_test:1:8455985b-22f3-11e9-8a6f-02426f783a63","isEnded":true,"variables":{}}
```