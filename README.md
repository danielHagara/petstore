# Project description
This is a simple test project that is using rest assured framework to test rest api for store feature at https://petstore.swagger.io/v2.

### Running the tests
Run tests either with a default environment https://petstore.swagger.io/v2 and default api_key
```console
mvn test
```
Or provide the environment url or api key as maven parameters
```console
mvn test -DbaseUrl=testEnvironmentUrl.com -DapiKey=testApiKey
```