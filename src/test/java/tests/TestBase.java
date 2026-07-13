package tests;

import org.testng.util.Strings;

import data.utils.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;

public abstract class TestBase {

    protected PropertyReader propertyReader;
    protected String baseUrl = System.getProperty("baseUrl");
    protected String apiKey = System.getProperty("apiKey");

    public TestBase() {
        if (Strings.isNullOrEmpty(baseUrl)) {
            baseUrl = getFileProperty("baseUrl");
        }

        if (Strings.isNullOrEmpty(apiKey)) {
            apiKey = getFileProperty("apiKey");
        }

        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig()
                        .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
                        .enablePrettyPrinting(true));
    }

    private String getFileProperty(String key) {
        try {
            if (propertyReader == null) {
                propertyReader = new PropertyReader();
            }
            return propertyReader.getProperty(key);
        } catch (Exception e) {
            System.err.println("Could not read from properties file: " + e.getMessage());
            return null;
        }
    }
    
}
