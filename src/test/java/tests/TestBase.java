package tests;

import data.utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class TestBase {

    protected final String baseUrl;
    protected final String apiKey;

    public TestBase() {
        PropertyReader propertyReader = new PropertyReader();
        baseUrl = getProperty("baseUrl", propertyReader);
        apiKey = getProperty("apiKey", propertyReader);

        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
                        .enablePrettyPrinting(true));
    }

    protected RequestSpecification requestSpec(boolean authenticated) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(baseUrl);
        if (authenticated) {
            builder.addHeader("api_key", apiKey);
        }
        return builder.build();
    }

    private String getProperty(String key, PropertyReader propertyReader) {
        String value = System.getProperty(key);
        return value == null || value.isBlank() ? propertyReader.getProperty(key) : value;
    }
    
}
