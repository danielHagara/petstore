__Project description__
This is a simple test project that is using rest assured framework to test rest api for store feature at https://petstore.swagger.io/v2.

__Running the tests__
Run tests either with a default environment https://petstore.swagger.io/v2 and default api_key
_mvn test_
Or provide the environment url as maven parameter baseUrl
_mvn test -DbaseUrl=testEnvironmentUrl.com -DapiKey=testApiKey_